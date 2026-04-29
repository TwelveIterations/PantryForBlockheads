package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.pantryforblockheads.trade.ModVillagerTrades;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTagProvider extends TagsProvider<VillagerTrade> {
    public ModVillagerTradeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.VILLAGER_TRADE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        final var farmerLevel1Trades = tag(VillagerTradeTags.FARMER_LEVEL_1);
        for (final var trade : ModVillagerTrades.farmerLevel1Trades()) {
            farmerLevel1Trades.add(trade);
        }

        final var butcherLevel5Trades = tag(VillagerTradeTags.BUTCHER_LEVEL_5);
        for (final var trade : ModVillagerTrades.butcherLevel5Trades()) {
            butcherLevel5Trades.add(trade);
        }

        final var commonTrades = tag(VillagerTradeTags.WANDERING_TRADER_COMMON);
        for (final var trade : ModVillagerTrades.wanderingTraderCommonTrades()) {
            commonTrades.add(trade);
        }

        final var buyingTrades = tag(VillagerTradeTags.WANDERING_TRADER_BUYING);
        for (final var trade : ModVillagerTrades.wanderingTraderBuyingTrades()) {
            buyingTrades.add(trade);
        }
    }
}
