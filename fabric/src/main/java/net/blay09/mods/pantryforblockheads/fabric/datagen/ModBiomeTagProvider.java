package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.pantryforblockheads.tag.ModBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagsProvider<Biome> {
    public ModBiomeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tag(ModBiomeTags.HAS_LEMON_TREE)
                .add(Biomes.BAMBOO_JUNGLE)
                .add(Biomes.JUNGLE)
                .add(Biomes.SAVANNA)
                .add(Biomes.SAVANNA_PLATEAU)
                .add(Biomes.SPARSE_JUNGLE)
                .add(Biomes.WINDSWEPT_SAVANNA);
        tag(ModBiomeTags.HAS_PEACH_TREE)
                .add(Biomes.BIRCH_FOREST)
                .add(Biomes.CHERRY_GROVE)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.FOREST)
                .add(Biomes.MEADOW)
                .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
                .add(Biomes.PLAINS)
                .add(Biomes.SUNFLOWER_PLAINS);
        tag(ModBiomeTags.HAS_BLUEBERRY_BUSH)
                .add(Biomes.FOREST)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.BIRCH_FOREST)
                .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
                .add(Biomes.TAIGA)
                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                .add(Biomes.MEADOW)
                .add(Biomes.GROVE);
        tag(ModBiomeTags.HAS_BLUEBERRY_BUSH_RARE)
                .add(Biomes.SNOWY_TAIGA)
                .add(Biomes.SNOWY_PLAINS);
        tag(ModBiomeTags.HAS_GRAPEVINE)
                .add(Biomes.PLAINS)
                .add(Biomes.SUNFLOWER_PLAINS)
                .add(Biomes.FOREST)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.BIRCH_FOREST)
                .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
                .add(Biomes.WINDSWEPT_FOREST)
                .add(Biomes.MEADOW)
                .add(Biomes.CHERRY_GROVE)
                .add(Biomes.GROVE)
                .add(Biomes.SPARSE_JUNGLE);
        tag(ModBiomeTags.HAS_GRAPEVINE_RARE)
                .add(Biomes.WINDSWEPT_HILLS)
                .add(Biomes.STONY_PEAKS)
                .add(Biomes.WOODED_BADLANDS);
    }
}
