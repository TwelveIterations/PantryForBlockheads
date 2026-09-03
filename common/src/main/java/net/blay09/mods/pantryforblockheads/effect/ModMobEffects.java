package net.blay09.mods.pantryforblockheads.effect;

import net.blay09.mods.balm.core.BalmRegistrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModMobEffects {
    public final Holder<MobEffect> wellFed;

    public ModMobEffects(BalmRegistrar.Scoped<MobEffect> registrar) {
        wellFed = registrar.register("well_fed", _ -> new WellFedMobEffect(MobEffectCategory.BENEFICIAL, 2445989)
                .addAttributeModifier(Attributes.MAX_ABSORPTION, Identifier.fromNamespaceAndPath("pantryforblockheads", "effect.well_fed"), 1.0, AttributeModifier.Operation.ADD_VALUE)).asHolder();
    }
}
