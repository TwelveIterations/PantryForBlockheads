package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.level.block.BlockLike;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.block.PantryBushBlock;
import net.blay09.mods.pantryforblockheads.block.PantryLeavesBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
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
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    private static final TagKey<Item> TOOLS_SHEAR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "tools/shear"));
    private static final float[] NORMAL_LEAVES_STICK_CHANCES = new float[]{0.02f, 0.022222223f, 0.025f, 0.033333335f, 0.1f}; // see BlockLootSubProvider

    protected ModBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> provider) {
        super(dataOutput, provider);
    }

    @Override
    public void generate() {
        final var modBlocks = PantryForBlockheads.blocks();
        final var modItems = PantryForBlockheads.items();
        dropSelf(modBlocks.artisanPress.asBlock());
        modBlocks.saplings.values().stream().map(BlockLike::asBlock).forEach(this::dropSelf);
        modBlocks.pottedSaplings.values().stream().map(BlockLike::asBlock).forEach(this::dropPottedContents);
        modBlocks.crops.forEach((type, block) -> {
            final var isMaxAge = MatchBlock.blockMatches(blocks, block.asBlock(), StatePropertiesPredicate.Builder.properties().hasProperty(type.ageProperty(), type.maxAge()));
            add(block.asBlock(), createCropDrops(block.asBlock(), modItems.crops.get(type).asItem(), block.asItem(), isMaxAge));
        });
        modBlocks.bushes.values().stream().map(BlockLike::asBlock).forEach(block -> {
            add(block, applyExplosionDecay(block, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .when(MatchBlock.blockMatches(blocks, block,
                                    StatePropertiesPredicate.Builder.properties().hasProperty(PantryBushBlock.AGE, PantryBushBlock.MAX_AGE)))
                            .add(LootItem.lootTableItem(block.asItem()))
                            .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3)))
                            .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))
                    .withPool(LootPool.lootPool()
                            .when(MatchBlock.blockMatches(blocks, block,
                                    StatePropertiesPredicate.Builder.properties().hasProperty(PantryBushBlock.AGE, PantryBushBlock.MAX_AGE - 1)))
                            .add(LootItem.lootTableItem(block.asItem()))
                            .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2)))
                            .apply(ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE))))));
        });
        modBlocks.leaves.forEach((type, block) -> {
            final var leavesBlock = (PantryLeavesBlock) block.asBlock();
            add(leavesBlock, createModdedLeavesDrops(leavesBlock, modBlocks.saplings.get(type).asBlock(), NORMAL_LEAVES_SAPLING_CHANCES)
                    .withPool(LootPool.lootPool()
                            .setRolls(ContextIntProviders.exactly(1))
                            .when(doesNotHaveAnyShearsToolOrSilkTouch())
                            .when(MatchBlock.blockMatches(blocks, leavesBlock, StatePropertiesPredicate.Builder.properties().hasProperty(leavesBlock.getAgeProperty(), leavesBlock.getMaxAge())))
                            .add(applyExplosionCondition(modBlocks.saplings.get(type), LootItem.lootTableItem(modItems.fruits.get(type)))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ContextIntProviders.exactly(1))
                            .when(doesNotHaveAnyShearsToolOrSilkTouch())
                            .when(MatchBlock.blockMatches(blocks, leavesBlock, StatePropertiesPredicate.Builder.properties().hasProperty(leavesBlock.getAgeProperty(), leavesBlock.getMaxAge())))
                            .add(applyExplosionCondition(modBlocks.saplings.get(type), LootItem.lootTableItem(modItems.fruits.get(type))))
                            .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), 0.005f, 0.0055f, 0.00625f, 0.0083f, 0.025f)))
            );
        });
    }

    private LootTable.Builder createModdedLeavesDrops(Block leavesBlock, Block saplingBlock, float... chances) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .add(LootItem.lootTableItem(leavesBlock)
                                .when(hasAnyShearsToolOrSilkTouch())
                                .otherwise(applyExplosionCondition(leavesBlock, LootItem.lootTableItem(saplingBlock))
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), chances)))))
                .withPool(LootPool.lootPool()
                        .setRolls(ContextIntProviders.exactly(1))
                        .when(doesNotHaveAnyShearsToolOrSilkTouch())
                        .add(applyExplosionDecay(leavesBlock, LootItem.lootTableItem(Items.STICK)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(enchantments.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_STICK_CHANCES))));
    }

    private LootItemCondition.Builder hasAnyShearsTool() {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().of(items, TOOLS_SHEAR));
    }

    private LootItemCondition.Builder hasAnyShearsToolOrSilkTouch() {
        return hasAnyShearsTool().or(() -> hasSilkTouch().value());
    }

    private LootItemCondition.Builder doesNotHaveAnyShearsToolOrSilkTouch() {
        return hasAnyShearsToolOrSilkTouch().invert();
    }
}
