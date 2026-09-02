package net.blay09.mods.pantryforblockheads.worldgen;

import com.mojang.serialization.MapCodec;
import net.blay09.mods.balm.core.BalmRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class ModPlacementModifierTypes {

    public final Holder<MapCodec<? extends PlacementModifier>> CONFIG_ENABLED;

    public ModPlacementModifierTypes(BalmRegistrar.Scoped<MapCodec<? extends PlacementModifier>> registrar) {
        CONFIG_ENABLED = registrar.register("config_enabled", _ -> ConfigEnabledPlacementModifier.CODEC);
    }

}
