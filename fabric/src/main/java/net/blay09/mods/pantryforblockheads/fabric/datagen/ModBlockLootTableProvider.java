package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.level.block.BlockLike;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.block.PantryBushBlock;
import net.blay09.mods.pantryforblockheads.block.PantryLeavesBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    private static final TagKey<Item> TOOLS_SHEAR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shear"));
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f}; // see BlockLootSubProvider

    protected ModBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(dataOutput, provider);
    }

    @Override
    public void generate() {
        final var blocks = PantryForBlockheads.blocks();
        final var items = PantryForBlockheads.items();
        dropSelf(blocks.artisanPress.asBlock());
        blocks.saplings.values().stream().map(BlockLike::asBlock).forEach(this::dropSelf);
        blocks.pottedSaplings.values().stream().map(BlockLike::asBlock).forEach(this::dropPottedContents);
        blocks.crops.forEach((type, block) -> {
            final var isMaxAge = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.asBlock()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(type.ageProperty(), type.maxAge()));
            add(block.asBlock(), createCropDrops(block.asBlock(), items.crops.get(type).asItem(), block.asItem(), isMaxAge));
        });
        final var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        blocks.bushes.values().stream().map(BlockLike::asBlock).forEach(block -> {
            add(block, applyExplosionDecay(block, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PantryBushBlock.AGE, PantryBushBlock.MAX_AGE)))
                            .add(LootItem.lootTableItem(block.asItem()))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2f, 3f)))
                            .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))
                    .withPool(LootPool.lootPool()
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PantryBushBlock.AGE, PantryBushBlock.MAX_AGE - 1)))
                            .add(LootItem.lootTableItem(block.asItem()))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f)))
                            .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        });
        blocks.leaves.forEach((type, block) -> {
            final var leavesBlock = (PantryLeavesBlock) block.asBlock();
            add(leavesBlock, createModdedLeavesDrops(leavesBlock, blocks.saplings.get(type).asBlock(), NORMAL_LEAVES_SAPLING_CHANCES)
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1f))
                            .when(doesNotHaveAnyShearsToolOrSilkTouch())
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(leavesBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(leavesBlock.getAgeProperty(), leavesBlock.getMaxAge())))
                            .add(applyExplosionCondition(blocks.saplings.get(type), LootItem.lootTableItem(items.fruits.get(type)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1f))
                            .when(doesNotHaveAnyShearsToolOrSilkTouch())
                            .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(leavesBlock).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(leavesBlock.getAgeProperty(), leavesBlock.getMaxAge())))
                            .add(applyExplosionCondition(blocks.saplings.get(type), LootItem.lootTableItem(items.fruits.get(type))))
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.005f, 0.0055f, 0.00625f, 0.0083f, 0.025f)))
            );
        });
    }

    private LootTable.Builder createModdedLeavesDrops(Block leavesBlock, Block saplingBlock, float... chances) {
        final var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .add(LootItem.lootTableItem(leavesBlock)
                                .when(hasAnyShearsToolOrSilkTouch())
                                .otherwise(applyExplosionCondition(leavesBlock, LootItem.lootTableItem(saplingBlock))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), chances)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1f))
                        .when(doesNotHaveAnyShearsToolOrSilkTouch())
                        .add(applyExplosionDecay(leavesBlock, LootItem.lootTableItem(Items.STICK)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1f, 2f))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))));
    }

    private LootItemCondition.Builder hasAnyShearsTool() {
        final var items = registries.lookupOrThrow(Registries.ITEM);
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, TOOLS_SHEAR));
    }

    private LootItemCondition.Builder hasAnyShearsToolOrSilkTouch() {
        return hasAnyShearsTool().or(hasSilkTouch());
    }

    private LootItemCondition.Builder doesNotHaveAnyShearsToolOrSilkTouch() {
        return hasAnyShearsToolOrSilkTouch().invert();
    }
}
