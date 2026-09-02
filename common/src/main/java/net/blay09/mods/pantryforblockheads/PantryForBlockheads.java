package net.blay09.mods.pantryforblockheads;

import net.blay09.mods.balm.Balm;
import net.blay09.mods.balm.core.BalmRegistrars;
import net.blay09.mods.balm.platform.event.callback.ServerLifecycleCallback;
import net.blay09.mods.balm.platform.event.callback.ServerTickCallback;
import net.blay09.mods.pantryforblockheads.block.ModBlocks;
import net.blay09.mods.pantryforblockheads.block.entity.ModBlockEntities;
import net.blay09.mods.pantryforblockheads.core.component.ModDataComponents;
import net.blay09.mods.pantryforblockheads.effect.ModMobEffects;
import net.blay09.mods.pantryforblockheads.item.ModItems;
import net.blay09.mods.pantryforblockheads.loot.ModLootModifiers;
import net.blay09.mods.pantryforblockheads.menu.ModMenus;
import net.blay09.mods.pantryforblockheads.network.ModNetworking;
import net.blay09.mods.pantryforblockheads.platform.attachment.ModDataAttachments;
import net.blay09.mods.pantryforblockheads.platform.attachment.PlayerFortune;
import net.blay09.mods.pantryforblockheads.recipe.ModRecipes;
import net.blay09.mods.pantryforblockheads.tag.ModItemTags;
import net.blay09.mods.pantryforblockheads.worldgen.ModPlacementModifierTypes;
import net.blay09.mods.pantryforblockheads.worldgen.ModWorldGen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PantryForBlockheads {

    public static final Logger logger = LoggerFactory.getLogger(PantryForBlockheads.class);

    public static final String MOD_ID = "pantryforblockheads";

    private static @Nullable ModBlocks blocks;
    private static @Nullable ModItems items;
    private static @Nullable ModDataComponents dataComponents;
    private static @Nullable ModDataAttachments dataAttachments;
    private static @Nullable ModPlacementModifierTypes placementModifierTypes;
    private static @Nullable ModMobEffects mobEffects;

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static PantryForBlockheadsConfig config() {
        return Objects.requireNonNull(Balm.config().getActiveConfig(PantryForBlockheadsConfig.class));
    }

    public static void initialize(BalmRegistrars registrars) {
        Balm.config().registerConfig(PantryForBlockheadsConfig.class);
        ModNetworking.initialize(Balm.networking());

        registrars.dataComponentTypes(registrar -> dataComponents = new ModDataComponents(registrar));
        registrars.blocks(registrar -> blocks = new ModBlocks(registrar));
        registrars.blockEntityTypes(ModBlockEntities::initialize);
        registrars.items(registrar -> items = new ModItems(registrar));
        registrars.menuTypes(ModMenus::initialize);
        registrars.recipeTypes(ModRecipes::initialize);
        registrars.creativeModeTabs((it) -> items().initializeCreativeModeTabs(it));
        registrars.dataAttachmentTypes(registrar -> dataAttachments = new ModDataAttachments(registrar));
        registrars.registrar(Registries.PLACEMENT_MODIFIER_TYPE, registrar -> placementModifierTypes = new ModPlacementModifierTypes(registrar));
        registrars.registrar(Registries.MOB_EFFECT, registrar -> mobEffects = new ModMobEffects(registrar));
        ModLootModifiers.initialize();
        ModWorldGen.initialize(Balm.biomeModifications());

        ServerLifecycleCallback.Starting.EVENT.register(_ -> items().postInitialize());

        ServerTickCallback.ServerPlayerTick.AFTER.register(player -> {
            final var fortune = PlayerFortune.lookup().get(player);
            if (fortune != null) {
                final int fortuneCooldownTicks = fortune.getFortuneCooldownTicks();
                if (fortuneCooldownTicks > 0) {
                    fortune.setFortuneCooldownTicks(fortuneCooldownTicks - 1);
                }
            }
        });
    }

    public static ModBlocks blocks() {
        return Objects.requireNonNull(blocks);
    }

    public static ModItems items() {
        return Objects.requireNonNull(items);
    }

    public static ModDataComponents dataComponents() {
        return Objects.requireNonNull(dataComponents);
    }

    public static ModDataAttachments dataAttachments() {
        return Objects.requireNonNull(dataAttachments);
    }

    public static ModPlacementModifierTypes placementModifierTypes() {
        return Objects.requireNonNull(placementModifierTypes);
    }

    public static ModMobEffects mobEffects() {
        return Objects.requireNonNull(mobEffects);
    }

    public static void applyAfterEatEffects(Player player, ItemStack stack, int excessNutrition) {
        if (config().foodEffects.excessNutritionGrantsAbsorption && stack.is(ModItemTags.EXCESS_NUTRITION_GRANTS_ABSORPTION) && excessNutrition > 0) {
            if (!player.hasEffect(mobEffects().wellFed) && !player.hasEffect(MobEffects.ABSORPTION)) {
                player.addEffect(new MobEffectInstance(mobEffects().wellFed, 10 * 60 * 20, excessNutrition, false, false, false));
            }
        }
    }
}
