package net.blay09.mods.pantryforblockheads.trade;

import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.TreeType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static net.blay09.mods.pantryforblockheads.PantryForBlockheads.id;

public final class ModVillagerTrades {
    private ModVillagerTrades() {
    }

    public static void bootstrap(BootstrapContext<VillagerTrade> context) {
        final var blocks = PantryForBlockheads.blocks();
        final var items = PantryForBlockheads.items();
        for (final var cropType : CropType.values()) {
            register(context, wanderingTraderTrade(cropType), blocks.crops.get(cropType).asItem(), 1, 12);
            registerBuying(context, farmerTrade(cropType), items.crops.get(cropType).asItem(), 15, 16, 2);
        }
        for (final var treeType : TreeType.values()) {
            register(context, wanderingTraderTrade(treeType), blocks.saplings.get(treeType).asItem(), 5, 8);
            registerBuying(context, wanderingTraderBuyingTrade(treeType), items.fruits.get(treeType).asItem(), 8, 2, 0);
        }
        for (final var bushType : BushType.values()) {
            registerBuying(context, wanderingTraderBuyingTrade(bushType), blocks.bushes.get(bushType).asItem(), 8, 2, 0);
            registerBuying(context, butcherLevel5Trade(bushType), blocks.bushes.get(bushType).asItem(), 10, 12, 30);
        }
    }

    public static List<ResourceKey<VillagerTrade>> wanderingTraderCommonTrades() {
        final var cropTrades = CropType.values().stream().map(ModVillagerTrades::wanderingTraderTrade);
        final var saplingTrades = Arrays.stream(TreeType.values()).map(ModVillagerTrades::wanderingTraderTrade);
        return Stream.concat(cropTrades, saplingTrades).toList();
    }

    public static List<ResourceKey<VillagerTrade>> farmerLevel1Trades() {
        return CropType.values().stream().map(ModVillagerTrades::farmerTrade).toList();
    }

    public static List<ResourceKey<VillagerTrade>> wanderingTraderBuyingTrades() {
        final var fruitTrades = Arrays.stream(TreeType.values()).map(ModVillagerTrades::wanderingTraderBuyingTrade);
        final var berryTrades = Arrays.stream(BushType.values()).map(ModVillagerTrades::wanderingTraderBuyingTrade);
        return Stream.concat(fruitTrades, berryTrades).toList();
    }

    public static List<ResourceKey<VillagerTrade>> butcherLevel5Trades() {
        return Arrays.stream(BushType.values()).map(ModVillagerTrades::butcherLevel5Trade).toList();
    }

    public static ResourceKey<VillagerTrade> wanderingTraderTrade(CropType cropType) {
        return wanderingTraderTrade(cropType + "_seeds");
    }

    public static ResourceKey<VillagerTrade> wanderingTraderTrade(TreeType treeType) {
        return wanderingTraderTrade(treeType + "_sapling");
    }

    public static ResourceKey<VillagerTrade> wanderingTraderTrade(String itemName) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, id("wandering_trader_emerald_" + itemName));
    }

    public static ResourceKey<VillagerTrade> farmerTrade(CropType cropType) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, id("farmer/1/" + cropType + "_emerald"));
    }

    public static ResourceKey<VillagerTrade> wanderingTraderBuyingTrade(TreeType treeType) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, id("wandering_trader/" + treeType + "_emerald"));
    }

    public static ResourceKey<VillagerTrade> wanderingTraderBuyingTrade(BushType bushType) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, id("wandering_trader/" + bushType + "_emerald"));
    }

    public static ResourceKey<VillagerTrade> butcherLevel5Trade(BushType bushType) {
        return ResourceKey.create(Registries.VILLAGER_TRADE, id("butcher/5/" + bushType + "_emerald"));
    }

    private static void register(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> key, ItemLike item, int emeraldPrice, int maxUses) {
        context.register(
                key,
                VillagerTrade.builder(
                        new TradeCost(Items.EMERALD, emeraldPrice),
                        new ItemStackTemplate(item.asItem()),
                        maxUses,
                        1,
                        0.05f
                ).build()
        );
    }

    private static void registerBuying(BootstrapContext<VillagerTrade> context, ResourceKey<VillagerTrade> key, ItemLike item, int itemCount, int maxUses, int xp) {
        context.register(
                key,
                VillagerTrade.builder(
                        new TradeCost(item, itemCount),
                        new ItemStackTemplate(Items.EMERALD),
                        maxUses,
                        xp,
                        0.05f
                ).build()
        );
    }
}
