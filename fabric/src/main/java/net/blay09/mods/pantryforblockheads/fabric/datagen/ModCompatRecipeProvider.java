package net.blay09.mods.pantryforblockheads.fabric.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.TreeType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import static net.blay09.mods.pantryforblockheads.PantryForBlockheads.id;

public class ModCompatRecipeProvider implements DataProvider {
    private final PackOutput.PathProvider recipePathProvider;

    public ModCompatRecipeProvider(FabricPackOutput output) {
        recipePathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        final var entries = new ArrayList<Entry>();

        for (final var cropType : CropType.values()) {
            entries.add(new Entry(
                    id("market/" + cropType + "_seeds"),
                    marketRecipe("farmingforblockheads:seeds", "pantryforblockheads:" + cropType + "_seeds", "selling.seeds.pantryforblockheads." + cropType + "_seeds")));
            entries.add(new Entry(
                    id("shipping_bin/pantryforblockheads/" + cropType),
                    shippingBinRecipe("pantryforblockheads:" + cropType, 5)));
            entries.add(new Entry(
                    id("drop_rush/" + cropType.plural()),
                    dropRushRecipe("pantryforblockheads:" + cropType.plural(), cropType.maxAge(), "pantryforblockheads:blocks/" + cropType.plural())));
        }

        for (final var treeType : TreeType.values()) {
            final var name = treeType.getSerializedName();
            entries.add(new Entry(
                    id( "market/" + name + "_sapling"),
                    marketRecipe("farmingforblockheads:saplings", "pantryforblockheads:" + name + "_sapling", "selling.saplings.pantryforblockheads." + name + "_sapling")));
            entries.add(new Entry(
                    id("shipping_bin/pantryforblockheads/" + name),
                    shippingBinRecipe("pantryforblockheads:" + name, 5)));
        }

        for (final var bushType : BushType.values()) {
            entries.add(new Entry(
                    id("shipping_bin/pantryforblockheads/" + bushType.getSerializedName()),
                    shippingBinRecipe("pantryforblockheads:" + bushType.getSerializedName(), 1)));
        }

        return CompletableFuture.allOf(entries.stream()
                .map(it -> DataProvider.saveStable(output, it.json(), recipePathProvider.json(it.id())))
                .toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return PantryForBlockheads.MOD_ID + " Compat Recipes";
    }

    private static JsonObject marketRecipe(String category, String item, String defaults) {
        final var json = new JsonObject();
        json.addProperty("type", "farmingforblockheads:market");
        json.addProperty("category", category);

        final var result = new JsonObject();
        result.addProperty("item", item);
        json.add("result", result);

        json.addProperty("defaults", defaults);
        addModLoadedConditions(json, "farmingforblockheads");
        return json;
    }

    private static JsonObject shippingBinRecipe(String input, int value) {
        final var json = new JsonObject();
        json.addProperty("type", "farmingforblockheads:shipping_bin");
        addModLoadedConditions(json, "farmingforblockheads");
        json.addProperty("input", input);
        json.addProperty("value", value);
        return json;
    }

    private static JsonObject dropRushRecipe(String stateName, int age, String lootTable) {
        final var json = new JsonObject();
        json.addProperty("type", "littlejoys:drop_rush");
        addModLoadedConditions(json, "littlejoys");

        final var eventCondition = new JsonObject();
        eventCondition.addProperty("type", "is_state");
        final var state = new JsonObject();
        state.addProperty("Name", stateName);
        final var properties = new JsonObject();
        properties.addProperty("age", Integer.toString(age));
        state.add("Properties", properties);
        eventCondition.add("state", state);
        json.add("eventCondition", eventCondition);

        json.addProperty("lootTable", lootTable);
        return json;
    }

    private static void addModLoadedConditions(JsonObject json, String modId) {
        final var fabricConditions = new JsonArray();
        final var fabricCondition = new JsonObject();
        fabricCondition.addProperty("condition", "fabric:all_mods_loaded");
        final var values = new JsonArray();
        values.add(modId);
        fabricCondition.add("values", values);
        fabricConditions.add(fabricCondition);
        json.add("fabric:load_conditions", fabricConditions);

        final var neoForgeConditions = new JsonArray();
        final var neoForgeCondition = new JsonObject();
        neoForgeCondition.addProperty("type", "neoforge:mod_loaded");
        neoForgeCondition.addProperty("modid", modId);
        neoForgeConditions.add(neoForgeCondition);
        json.add("neoforge:conditions", neoForgeConditions);
    }

    private record Entry(Identifier id, JsonObject json) {
    }
}
