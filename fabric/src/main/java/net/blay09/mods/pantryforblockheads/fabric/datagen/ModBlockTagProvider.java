package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        final var blocks = PantryForBlockheads.blocks();

        final var relocationNotSupported = valueLookupBuilder(TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "relocation_not_supported")));
        relocationNotSupported.add(blocks.artisanPress.asBlock());

        valueLookupBuilder(ModBlockTags.BUSHES).addAll(blocks.bushes.sortedValues().map(DeferredBlock::asBlock).toList());
        valueLookupBuilder(ModBlockTags.DROPS_SEEDS).add(Blocks.SHORT_GRASS, Blocks.TALL_GRASS);
        final var maintainsFarmland = valueLookupBuilder(BlockTags.MAINTAINS_FARMLAND);
        blocks.crops.sortedValues().map(DeferredBlock::asBlock).forEach(maintainsFarmland::add);

        final var leaves = valueLookupBuilder(BlockTags.LEAVES);
        blocks.leaves.sortedValues().map(DeferredBlock::asBlock).forEach(leaves::add);

        final var saplings = valueLookupBuilder(BlockTags.SAPLINGS);
        blocks.saplings.sortedValues().map(DeferredBlock::asBlock).forEach(saplings::add);

        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(blocks.artisanPress.asBlock());
    }
}
