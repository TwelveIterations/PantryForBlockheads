package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.loot.ModLootTables;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModGameplayLootTableProvider extends SimpleFabricLootTableSubProvider {
    private static final CropType[] CUSTOM_SEED_CROPS = {
            CropType.BELL_PEPPER,
            CropType.BROCCOLI,
            CropType.CAULIFLOWER,
            CropType.CHILI_PEPPER,
            CropType.CORN,
            CropType.EGGPLANT,
            CropType.LETTUCE,
            CropType.ONION,
            CropType.PEANUT,
            CropType.STRAWBERRY,
            CropType.TOMATO,
            CropType.TURNIP,
            CropType.RICE,
            CropType.SOYBEAN
    };

    public ModGameplayLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture, LootContextParamSets.EMPTY);
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(ModLootTables.GRASS_SEEDS, LootTable.lootTable().withPool(seedPool()
                .when(LootItemRandomChanceCondition.randomChance(0.125f))));
        output.accept(ModLootTables.CHEST_ABANDONED_MINESHAFT, LootTable.lootTable().withPool(seedPool()));
        output.accept(ModLootTables.CHEST_SIMPLE_DUNGEON, LootTable.lootTable().withPool(seedPool()));
        output.accept(ModLootTables.CHESTS_VILLAGE_TAIGA_HOUSE, LootTable.lootTable().withPool(seedPool()));
        output.accept(ModLootTables.CHEST_WOODLAND_MANSION, LootTable.lootTable().withPool(seedPool()));
    }

    private static LootPool.Builder seedPool() {
        final var blocks = PantryForBlockheads.blocks();
        final var builder = LootPool.lootPool();
        for (final var cropType : CUSTOM_SEED_CROPS) {
            builder.add(LootItem.lootTableItem(blocks.crops.get(cropType).asItem()));
        }
        return builder;
    }

    @Override
    public void run() {
    }
}
