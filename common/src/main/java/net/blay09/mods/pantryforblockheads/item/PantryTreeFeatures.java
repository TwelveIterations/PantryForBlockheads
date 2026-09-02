package net.blay09.mods.pantryforblockheads.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;

import static net.blay09.mods.pantryforblockheads.PantryForBlockheads.id;

public class PantryTreeFeatures {
    public static final ResourceKey<Feature> LEMON = ResourceKey.create(Registries.FEATURE, id("lemon"));
    public static final ResourceKey<Feature> PEACH = ResourceKey.create(Registries.FEATURE, id("peach"));
}
