package net.blay09.mods.pantryforblockheads.loot;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.world.level.storage.loot.BalmLootModifier;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.tag.ModBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jspecify.annotations.Nullable;

import java.util.List;

public final class ModLootModifiers {
    private static final ThreadLocal<Boolean> isApplyingSeedChestLoot = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<Boolean> isApplyingGrassSeeds = ThreadLocal.withInitial(() -> false);

    private ModLootModifiers() {
    }

    public static void initialize() {
        Balm.lootModifiers().registerLootModifier(PantryForBlockheads.id("seed_chest_loot"), new BalmLootModifier() {
            @Override
            public void apply(LootContext context, List<ItemStack> loot, @Nullable ResourceKey<LootTable> lootTableId) {
                if (isApplyingSeedChestLoot.get() || lootTableId == null) {
                    return;
                }

                if (PantryForBlockheads.config().vanillaModifications.chestLootHoldsBlockheadSeeds) {
                    final var level = context.getLevel();
                    final var additionsLootTable = Identifier.fromNamespaceAndPath(PantryForBlockheads.MOD_ID, lootTableId.identifier().getPath()).withPrefix("loot_additions/");
                    final var lootTable = level.getServer().reloadableRegistries().getLootTable(ResourceKey.create(Registries.LOOT_TABLE, additionsLootTable));
                    isApplyingSeedChestLoot.set(true);
                    try {
                        lootTable.getRandomItems(context, loot::add);
                    } finally {
                        isApplyingSeedChestLoot.set(false);
                    }
                }
            }
        });

        Balm.lootModifiers().registerLootModifier(PantryForBlockheads.id("grass_seeds"), new BalmLootModifier() {
            @Override
            public void apply(LootContext context, List<ItemStack> loot, @Nullable ResourceKey<LootTable> lootTableId) {
                if (isApplyingGrassSeeds.get()) {
                    return;
                }

                if (!PantryForBlockheads.config().vanillaModifications.grassDropsBlockheadSeeds) {
                    return;
                }

                final BlockState blockState = context.getOptional(LootContextParams.BLOCK_STATE);
                if (blockState == null || !blockState.is(ModBlockTags.DROPS_SEEDS)) {
                    return;
                }

                final var level = context.getLevel();
                final var grassSeedLootTable = level.getServer().reloadableRegistries().getLootTable(ModLootTables.GRASS_SEEDS);
                isApplyingGrassSeeds.set(true);
                try {
                    grassSeedLootTable.getRandomItems(context, loot::add);
                } finally {
                    isApplyingGrassSeeds.set(false);
                }
            }
        });
    }
}
