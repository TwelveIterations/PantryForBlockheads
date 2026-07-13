package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockItemTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        final var blocks = PantryForBlockheads.blocks();

        final var relocationNotSupported = builder(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "relocation_not_supported")));
        relocationNotSupported.add(blocks.artisanPress.asResourceKey());

        builder(ModBlockTags.BUSHES).addAll(blocks.bushes.sortedValues().map(DeferredBlock::asResourceKey).toList());
        builder(ModBlockTags.DROPS_SEEDS).add(BlockItemIds.SHORT_GRASS, BlockItemIds.TALL_GRASS);

        final var maintainsFarmland = builder(BlockTags.MAINTAINS_FARMLAND);
        blocks.crops.sortedValues().map(DeferredBlock::asResourceKey).forEach(maintainsFarmland::add);

        final var crops = builder(BlockTags.CROPS);
        blocks.crops.sortedValues().map(DeferredBlock::asResourceKey).forEach(crops::add);

        final var leaves = builder(BlockTags.LEAVES);
        blocks.leaves.sortedValues().map(DeferredBlock::asResourceKey).forEach(leaves::add);

        final var saplings = builder(BlockItemTags.SAPLINGS.block());
        blocks.saplings.sortedValues().map(DeferredBlock::asResourceKey).forEach(saplings::add);

        builder(BlockTags.MINEABLE_WITH_PICKAXE).add(blocks.artisanPress.asResourceKey());
    }
}
