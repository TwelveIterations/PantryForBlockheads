package net.blay09.mods.pantryforblockheads.item;

import net.blay09.mods.balm.world.item.BalmCompostableRegistrar;
import net.blay09.mods.balm.world.item.DeferredItem;
import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;

public class ModCompostables {
    private static final float SEED_CHANCE = 0.3f;
    private static final float FRUITY_LEAVES_CHANCE = 0.5f;
    private static final float SAPLING_CHANCE = 0.3f;
    private static final float CROP_CHANCE = 0.65f;

    public static void initialize(BalmCompostableRegistrar compostables) {
        final var blocks = PantryForBlockheads.blocks();
        final var items = PantryForBlockheads.items();

        items.crops.values().stream().map(DeferredItem::asItem).forEach(item -> compostables.register(item, CROP_CHANCE));
        blocks.crops.values().stream().map(DeferredBlock::asItem).forEach(item -> compostables.register(item, SEED_CHANCE));
        blocks.saplings.values().stream().map(DeferredBlock::asItem).forEach(item -> compostables.register(item, SAPLING_CHANCE));
        blocks.leaves.values().stream().map(DeferredBlock::asItem).forEach(item -> compostables.register(item, FRUITY_LEAVES_CHANCE));
    }
}
