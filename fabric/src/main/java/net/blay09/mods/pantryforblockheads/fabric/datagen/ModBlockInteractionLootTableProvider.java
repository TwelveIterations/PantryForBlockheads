package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.block.PantryBushBlock;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModBlockInteractionLootTableProvider extends SimpleFabricLootTableSubProvider {
    public ModBlockInteractionLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, LootContextParamSets.BLOCK_INTERACT);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        final var blocks = PantryForBlockheads.blocks();
        final var blueberryBush = blocks.bushes.get(BushType.BLUEBERRIES);
        output.accept(BushType.BLUEBERRIES.harvestLootTable(), LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(blueberryBush)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                .when(MatchBlock.blockMatches(BuiltInRegistries.BLOCK, blueberryBush.asBlock(),
                                        StatePropertiesPredicate.Builder.properties().hasProperty(PantryBushBlock.AGE, PantryBushBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(blueberryBush)
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))));

        final var grapevine = blocks.bushes.get(BushType.GRAPES);
        output.accept(BushType.GRAPES.harvestLootTable(), LootTable.lootTable().withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(grapevine)
                                .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(1)))
                                .when(MatchBlock.blockMatches(BuiltInRegistries.BLOCK, grapevine.asBlock(),
                                        StatePropertiesPredicate.Builder.properties().hasProperty(PantryBushBlock.AGE, PantryBushBlock.MAX_AGE)))))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(grapevine)
                        .apply(SetItemCountFunction.setCount(ContextIntProviders.between(1, 2))))));
    }

    @Override
    public void run() {
    }
}
