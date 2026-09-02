package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.tags.ConventionalItemTags;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.MealType;
import net.blay09.mods.pantryforblockheads.recipe.ArtisanPressRecipe;
import net.blay09.mods.pantryforblockheads.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.blay09.mods.pantryforblockheads.PantryForBlockheads.id;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new RecipeProvider(recipes, advancements) {
            @Override
            public void buildRecipes() {
                final var blocks = PantryForBlockheads.blocks();
                shaped(RecipeCategory.TOOLS, blocks.artisanPress)
                        .pattern("WCW")
                        .pattern("WAW")
                        .pattern("BPB")
                        .define('W', ItemTags.PLANKS)
                        .define('C', Items.IRON_CHAIN)
                        .define('A', Items.ANVIL)
                        .define('B', Items.MUD_BRICKS)
                        .define('P', Items.HEAVY_WEIGHTED_PRESSURE_PLATE)
                        .unlockedBy("has_anvil", has(Items.ANVIL))
                        .save(output);

                final var items = PantryForBlockheads.items();
                shaped(RecipeCategory.TOOLS, items.fryingPan)
                        .pattern("  C")
                        .pattern("II ")
                        .define('I', Items.IRON_INGOT)
                        .define('C', Items.COPPER_INGOT)
                        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                        .save(output);
                shaped(RecipeCategory.TOOLS, items.pot)
                        .pattern("IBI")
                        .pattern(" I ")
                        .define('I', Items.IRON_INGOT)
                        .define('B', Items.BUCKET)
                        .unlockedBy("has_bucket", has(Items.BUCKET))
                        .save(output);
                shapeless(RecipeCategory.TOOLS, items.mixingBowl)
                        .requires(Items.BOWL)
                        .requires(Items.STICK)
                        .unlockedBy("has_bowl", has(Items.BOWL))
                        .save(output);
                shaped(RecipeCategory.TOOLS, items.bakingSheet)
                        .pattern("PPP")
                        .pattern("III")
                        .define('P', Items.PAPER)
                        .define('I', Items.IRON_INGOT)
                        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                        .save(output);
                shaped(RecipeCategory.TOOLS, items.knife)
                        .pattern(" I ")
                        .pattern("II ")
                        .pattern(" S ")
                        .define('I', Items.IRON_INGOT)
                        .define('S', Items.STICK)
                        .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.FORTUNE_COOKIE))
                        .requires(items.magicSprinkles)
                        .requires(Items.COOKIE)
                        .requires(Items.PAPER)
                        .unlockedBy("has_cookie", has(Items.COOKIE))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHOCOLATE))
                        .requires(items.mixingBowl)
                        .requires(Items.COCOA_BEANS)
                        .requires(Items.SUGAR)
                        .unlockedBy("has_cocoa_beans", has(Items.COCOA_BEANS))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.magicSprinkles)
                        .requires(Items.GOLD_NUGGET)
                        .requires(Items.LAPIS_LAZULI)
                        .requires(Items.SUGAR)
                        .requires(Items.HONEYCOMB)
                        .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.BACON))
                        .requires(items.fryingPan)
                        .requires(items.knife)
                        .requires(Items.PORKCHOP)
                        .unlockedBy("has_porkchop", has(Items.PORKCHOP))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.BREADSTICK))
                        .requires(Items.WHEAT)
                        .requires(Items.STICK)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.MAKI_SUSHI), 6)
                        .requires(items.crops.get(CropType.RICE))
                        .requires(ModItemTags.ANY_SUSHI_FILLING)
                        .requires(Items.KELP)
                        .requires(items.knife)
                        .unlockedBy("has_rice", has(items.crops.get(CropType.RICE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.NIGIRI_SUSHI), 2)
                        .requires(items.crops.get(CropType.RICE))
                        .requires(ModItemTags.ANY_SUSHI_FILLING)
                        .requires(items.knife)
                        .unlockedBy("has_rice", has(items.crops.get(CropType.RICE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.INARI_SUSHI), 2)
                        .requires(items.crops.get(CropType.RICE))
                        .requires(items.meals.get(MealType.TOFU))
                        .requires(items.fryingPan)
                        .unlockedBy("has_tofu", has(items.meals.get(MealType.TOFU)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.DUMPLING))
                        .requires(Items.WHEAT)
                        .requires(ModItemTags.ANY_DUMPLING_FILLING)
                        .requires(items.pot)
                        .unlockedBy("has_any_dumpling_filling", has(ModItemTags.ANY_DUMPLING_FILLING))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.RICE_BALL), 3)
                        .requires(items.crops.get(CropType.RICE))
                        .requires(Items.KELP)
                        .unlockedBy("has_rice", has(items.crops.get(CropType.RICE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.RICE_CRACKER), 3)
                        .requires(items.crops.get(CropType.RICE))
                        .requires(items.bakingSheet)
                        .unlockedBy("has_rice", has(items.crops.get(CropType.RICE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.SANDWICH))
                        .requires(items.crops.get(CropType.LETTUCE))
                        .requires(items.crops.get(CropType.TOMATO))
                        .requires(items.meals.get(MealType.CHEESE))
                        .requires(Items.BREAD)
                        .unlockedBy("has_bread", has(Items.BREAD))
                        .save(output);

                shaped(RecipeCategory.FOOD, items.meals.get(MealType.CROISSANT))
                        .pattern(" W ")
                        .pattern("W  ")
                        .pattern(" W ")
                        .define('W', Items.WHEAT)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shaped(RecipeCategory.FOOD, items.meals.get(MealType.PRETZEL))
                        .pattern("WSW")
                        .pattern(" W ")
                        .pattern("W W")
                        .define('W', Items.WHEAT)
                        .define('S', Items.PUMPKIN_SEEDS)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.SAUSAGE), 2)
                        .requires(Items.PORKCHOP)
                        .requires(items.knife)
                        .requires(items.pot)
                        .unlockedBy("has_porkchop", has(Items.PORKCHOP))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.HOTDOG))
                        .requires(items.meals.get(MealType.SAUSAGE))
                        .requires(Items.BREAD)
                        .requires(items.knife)
                        .unlockedBy("has_sausage", has(items.meals.get(MealType.SAUSAGE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.FALAFEL))
                        .requires(items.crops.get(CropType.SOYBEAN))
                        .requires(items.crops.get(CropType.CAULIFLOWER))
                        .requires(items.crops.get(CropType.BELL_PEPPER))
                        .requires(items.mixingBowl)
                        .unlockedBy("has_soybean", has(items.crops.get(CropType.SOYBEAN)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.TACO))
                        .requires(items.meals.get(MealType.FLATBREAD))
                        .requires(items.crops.get(CropType.SOYBEAN))
                        .requires(items.crops.get(CropType.CORN))
                        .requires(items.crops.get(CropType.LETTUCE))
                        .unlockedBy("has_corn", has(items.crops.get(CropType.CORN)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.BURRITO))
                        .requires(items.meals.get(MealType.FLATBREAD))
                        .requires(items.crops.get(CropType.SOYBEAN))
                        .requires(items.crops.get(CropType.CORN))
                        .requires(items.crops.get(CropType.LETTUCE))
                        .requires(items.crops.get(CropType.RICE))
                        .requires(items.crops.get(CropType.TOMATO))
                        .unlockedBy("has_corn", has(items.crops.get(CropType.CORN)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.BURGER))
                        .requires(Items.WHEAT)
                        .requires(Items.COOKED_BEEF)
                        .requires(items.crops.get(CropType.LETTUCE))
                        .requires(items.crops.get(CropType.TOMATO))
                        .requires(items.crops.get(CropType.ONION))
                        .requires(items.meals.get(MealType.CHEESE))
                        .unlockedBy("has_cheese", has(items.meals.get(MealType.CHEESE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.PANCAKES))
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.fryingPan)
                        .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.WAFFLE))
                        .requires(ItemTags.EGGS)
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.bakingSheet)
                        .requires(items.mixingBowl)
                        .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.SOY_MILK))
                        .requires(items.crops.get(CropType.SOYBEAN))
                        .requires(Items.GLASS_BOTTLE)
                        .requires(Items.WATER_BUCKET)
                        .unlockedBy("has_soybean", has(items.crops.get(CropType.SOYBEAN)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.BAGEL))
                        .requires(Items.WHEAT)
                        .requires(Items.SUGAR)
                        .requires(items.bakingSheet)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.MUFFIN), 2)
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.bakingSheet)
                        .requires(items.mixingBowl)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.BERRY_MUFFIN), 2)
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.bakingSheet)
                        .requires(items.mixingBowl)
                        .requires(ModItemTags.ANY_BERRY)
                        .unlockedBy("has_any_berry", has(ModItemTags.ANY_BERRY))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.PINK_DOUGHNUT))
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.fryingPan)
                        .requires(items.mixingBowl)
                        .requires(ConventionalItemTags.PINK_DYES)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.PINK_DOUGHNUT_SPRINKLES))
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.fryingPan)
                        .requires(items.mixingBowl)
                        .requires(items.magicSprinkles)
                        .requires(ConventionalItemTags.PINK_DYES)
                        .unlockedBy("has_magic_sprinkles", has(items.magicSprinkles))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.PINK_DOUGHNUT_SPRINKLES))
                        .requires(items.meals.get(MealType.PINK_DOUGHNUT))
                        .requires(items.magicSprinkles)
                        .unlockedBy("has_magic_sprinkles", has(items.magicSprinkles))
                        .save(output, "sprinkle_pink_doughnut");

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHOCOLATE_DOUGHNUT))
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.fryingPan)
                        .requires(items.mixingBowl)
                        .requires(items.meals.get(MealType.CHOCOLATE))
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CUPCAKE), 2)
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.bakingSheet)
                        .requires(items.mixingBowl)
                        .unlockedBy("has_wheat", has(Items.WHEAT))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHOCOLATE_CUPCAKE), 2)
                        .requires(ItemTags.EGGS)
                        .requires(Items.MILK_BUCKET)
                        .requires(Items.SUGAR)
                        .requires(Items.WHEAT)
                        .requires(items.bakingSheet)
                        .requires(items.mixingBowl)
                        .requires(items.meals.get(MealType.CHOCOLATE))
                        .unlockedBy("has_chocolate", has(items.meals.get(MealType.CHOCOLATE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.ICECREAM))
                        .requires(Items.SNOWBALL)
                        .requires(Items.WHEAT)
                        .requires(Items.SUGAR)
                        .unlockedBy("has_snowball", has(Items.SNOWBALL))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHOCOLATE_ICECREAM))
                        .requires(Items.SNOWBALL)
                        .requires(Items.WHEAT)
                        .requires(items.meals.get(MealType.CHOCOLATE))
                        .unlockedBy("has_snowball", has(Items.SNOWBALL))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.FISH_FILLET))
                        .requires(ModItemTags.ANY_EDIBLE_RAW_FISH)
                        .requires(items.knife)
                        .requires(items.fryingPan)
                        .unlockedBy("has_edible_fishes", has(ModItemTags.ANY_EDIBLE_RAW_FISH))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.FISH_STICKS))
                        .requires(ModItemTags.ANY_EDIBLE_RAW_FISH)
                        .requires(Items.STICK)
                        .requires(items.fryingPan)
                        .unlockedBy("has_edible_fishes", has(ModItemTags.ANY_EDIBLE_RAW_FISH))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHEESE_PIZZA))
                        .requires(items.meals.get(MealType.FLATBREAD))
                        .requires(items.crops.get(CropType.TOMATO))
                        .requires(items.meals.get(MealType.CHEESE))
                        .unlockedBy("has_cheese", has(items.meals.get(MealType.CHEESE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHEESE_PIZZA_SLICE), 8)
                        .requires(items.meals.get(MealType.CHEESE_PIZZA))
                        .requires(items.knife)
                        .unlockedBy("has_cheese_pizza", has(items.meals.get(MealType.CHEESE_PIZZA)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.PEPPERONI_PIZZA))
                        .requires(items.meals.get(MealType.FLATBREAD))
                        .requires(items.crops.get(CropType.TOMATO))
                        .requires(items.meals.get(MealType.CHEESE))
                        .requires(items.meals.get(MealType.SAUSAGE))
                        .unlockedBy("has_cheese", has(items.meals.get(MealType.CHEESE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.PEPPERONI_PIZZA_SLICE), 8)
                        .requires(items.meals.get(MealType.PEPPERONI_PIZZA))
                        .requires(items.knife)
                        .unlockedBy("has_pepperoni_pizza", has(items.meals.get(MealType.PEPPERONI_PIZZA)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.VEGETABLE_PIZZA))
                        .requires(items.meals.get(MealType.FLATBREAD))
                        .requires(items.crops.get(CropType.TOMATO))
                        .requires(items.meals.get(MealType.CHEESE))
                        .requires(ModItemTags.ANY_PIZZA_VEGETABLE)
                        .unlockedBy("has_cheese", has(items.meals.get(MealType.CHEESE)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.VEGETABLE_PIZZA_SLICE), 8)
                        .requires(items.meals.get(MealType.VEGETABLE_PIZZA))
                        .requires(items.knife)
                        .unlockedBy("has_vegetable_pizza", has(items.meals.get(MealType.VEGETABLE_PIZZA)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.FRIED_EGG))
                        .requires(ItemTags.EGGS)
                        .requires(items.fryingPan)
                        .unlockedBy("has_eggs", has(ItemTags.EGGS))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.CHICKEN_NUGGETS), 6)
                        .requires(Items.COOKED_CHICKEN)
                        .requires(Items.WHEAT)
                        .requires(items.knife)
                        .requires(items.fryingPan)
                        .unlockedBy("has_cooked_chicken", has(Items.COOKED_CHICKEN))
                        .save(output);

                shapeless(RecipeCategory.FOOD, items.meals.get(MealType.ONION_RINGS), 2)
                        .requires(items.crops.get(CropType.ONION))
                        .requires(Items.WHEAT)
                        .requires(items.fryingPan)
                        .unlockedBy("has_onion", has(items.crops.get(CropType.ONION)))
                        .save(output);

                shapeless(RecipeCategory.FOOD, Items.MELON_SLICE, 9)
                        .requires(Items.MELON)
                        .requires(items.knife)
                        .unlockedBy("has_melon", has(Items.MELON))
                        .save(output);

                SimpleCookingRecipeBuilder.smelting(
                                Ingredient.of(items.crops.get(CropType.CORN)),
                                RecipeCategory.FOOD,
                                CookingBookCategory.FOOD,
                                items.meals.get(MealType.POPCORN),
                                0.1f,
                                200)
                        .unlockedBy("has_corn", has(items.crops.get(CropType.CORN)))
                        .save(output);

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("flatbread")),
                        Ingredient.of(Items.BREAD),
                        new ItemStackTemplate(items.meals.get(MealType.FLATBREAD).asItem()));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("cheese")),
                        Ingredient.of(Items.MILK_BUCKET),
                        new ItemStackTemplate(items.meals.get(MealType.CHEESE).asItem()));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("tofu")),
                        Ingredient.of(items.meals.get(MealType.SOY_MILK)),
                        new ItemStackTemplate(items.meals.get(MealType.TOFU).asItem()),
                        new ItemStackTemplate(Items.GLASS_BOTTLE));

                for (final var cropType : CropType.values()) {
                    artisanPress(output,
                            ResourceKey.create(Registries.RECIPE, id(cropType + "_seeds")),
                            Ingredient.of(items.crops.get(cropType)),
                            new ItemStackTemplate(blocks.crops.get(cropType).asItem()),
                            new ItemStackTemplate(items.meals.get(MealType.MASHED_PRODUCE).asItem()));
                }

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("sugar")),
                        Ingredient.of(Items.SUGAR_CANE),
                        new ItemStackTemplate(Items.SUGAR));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("magic_sparkles")),
                        Ingredient.of(Items.EXPERIENCE_BOTTLE),
                        new ItemStackTemplate(Items.GLASS_BOTTLE),
                        new ItemStackTemplate(items.magicSprinkles.asItem()));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("leather")),
                        Ingredient.of(Items.ROTTEN_FLESH),
                        new ItemStackTemplate(Items.LEATHER));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("melon")),
                        Ingredient.of(Items.GLISTERING_MELON_SLICE),
                        new ItemStackTemplate(Items.MELON_SLICE),
                        new ItemStackTemplate(items.magicSprinkles.asItem()));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("apple")),
                        Ingredient.of(Items.GOLDEN_APPLE),
                        new ItemStackTemplate(Items.APPLE),
                        new ItemStackTemplate(items.magicSprinkles.asItem()));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("golden_apple")),
                        Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
                        new ItemStackTemplate(Items.GOLDEN_APPLE),
                        new ItemStackTemplate(items.magicSprinkles.asItem()));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("slime")),
                        Ingredient.of(Items.POISONOUS_POTATO),
                        new ItemStackTemplate(Items.POTATO),
                        new ItemStackTemplate(Items.SLIME_BALL));

                artisanPress(output,
                        ResourceKey.create(Registries.RECIPE, id("you_monster")),
                        Ingredient.of(Items.DRIED_GHAST),
                        new ItemStackTemplate(Items.GHAST_TEAR),
                        new ItemStackTemplate(Items.BONE_MEAL));
            }
        };
    }

    @Override
    public String getName() {
        return PantryForBlockheads.MOD_ID;
    }

    private static void artisanPress(RecipeOutput output,
                                     ResourceKey<Recipe<?>> recipeKey,
                                     Ingredient ingredient,
                                     ItemStackTemplate... results) {
        output.accept(recipeKey, new ArtisanPressRecipe(ingredient, List.of(results)), null);
    }
}
