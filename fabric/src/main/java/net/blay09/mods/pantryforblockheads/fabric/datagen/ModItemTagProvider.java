package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.item.DeferredItem;
import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.MealType;
import net.blay09.mods.pantryforblockheads.item.TreeType;
import net.blay09.mods.pantryforblockheads.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        final var blocks = PantryForBlockheads.blocks();
        final var items = PantryForBlockheads.items();
        valueLookupBuilder(ModItemTags.ANY_EDIBLE_RAW_FISH)
                .add(Items.COD)
                .add(Items.SALMON)
                .add(Items.TROPICAL_FISH);
        valueLookupBuilder(ModItemTags.ANY_SUSHI_FILLING)
                .add(items.meals.get(MealType.TOFU).asItem())
                .addTag(ModItemTags.ANY_EDIBLE_RAW_FISH)
                .add(Items.COOKED_CHICKEN);
        valueLookupBuilder(ModItemTags.ANY_DUMPLING_FILLING)
                .add(items.meals.get(MealType.TOFU).asItem())
                .add(blocks.bushes.get(BushType.BLUEBERRIES).asItem())
                .addOptionalTag(ItemTags.EGGS)
                .add(Items.BEETROOT)
                .add(Items.PORKCHOP)
                .add(Items.CHICKEN)
                .add(Items.BROWN_MUSHROOM)
                .add(Items.RED_MUSHROOM)
                .add(Items.BEEF)
                .addTag(ModItemTags.ANY_EDIBLE_RAW_FISH)
                .add(Items.COOKED_CHICKEN);
        valueLookupBuilder(ModItemTags.ANY_PIZZA_VEGETABLE)
                .add(items.crops.get(CropType.BELL_PEPPER).asItem())
                .add(items.crops.get(CropType.BROCCOLI).asItem())
                .add(items.crops.get(CropType.CHILI_PEPPER).asItem())
                .add(items.crops.get(CropType.ONION).asItem())
                .add(items.crops.get(CropType.TOMATO).asItem())
                .add(items.crops.get(CropType.EGGPLANT).asItem());
        valueLookupBuilder(ModItemTags.ANY_BERRY)
                .add(blocks.bushes.get(BushType.BLUEBERRIES).asItem())
                .add(Items.SWEET_BERRIES);
        valueLookupBuilder(ModItemTags.EXCESS_NUTRITION_GRANTS_ABSORPTION)
                .add(items.meals.get(MealType.BURGER).asItem())
                .add(items.meals.get(MealType.BURRITO).asItem())
                .add(items.meals.get(MealType.CHEESE_PIZZA).asItem())
                .add(items.meals.get(MealType.PEPPERONI_PIZZA).asItem())
                .add(items.meals.get(MealType.VEGETABLE_PIZZA).asItem())
                .add(items.meals.get(MealType.SANDWICH).asItem())
                .add(items.meals.get(MealType.TACO).asItem());

        final var crops = valueLookupBuilder(ConventionalItemTags.CROPS);
        items.crops.sortedValues().map(DeferredItem::asItem).forEach(crops::add);
        for (final var cropType : CropType.values()) {
            valueLookupBuilder(conventionalItemTag("crops/" + cropType.getSerializedName()))
                    .add(items.crops.get(cropType).asItem());
        }

        final var seeds = valueLookupBuilder(ConventionalItemTags.SEEDS);
        blocks.crops.sortedValues().map(DeferredBlock::asItem).forEach(seeds::add);
        for (final var cropType : CropType.values()) {
            valueLookupBuilder(conventionalItemTag("seeds/" + cropType.getSerializedName()))
                    .add(blocks.crops.get(cropType).asItem());
        }

        valueLookupBuilder(ConventionalItemTags.VEGETABLE_FOODS)
                .add(items.crops.get(CropType.BELL_PEPPER).asItem())
                .add(items.crops.get(CropType.BROCCOLI).asItem())
                .add(items.crops.get(CropType.CAULIFLOWER).asItem())
                .add(items.crops.get(CropType.CHILI_PEPPER).asItem())
                .add(items.crops.get(CropType.CORN).asItem())
                .add(items.crops.get(CropType.EGGPLANT).asItem())
                .add(items.crops.get(CropType.LETTUCE).asItem())
                .add(items.crops.get(CropType.ONION).asItem())
                .add(items.crops.get(CropType.TOMATO).asItem())
                .add(items.crops.get(CropType.TURNIP).asItem());
        valueLookupBuilder(ConventionalItemTags.FRUIT_FOODS)
                .add(blocks.bushes.get(BushType.GRAPES).asItem())
                .add(items.fruits.get(TreeType.LEMON).asItem())
                .add(items.fruits.get(TreeType.PEACH).asItem());
        valueLookupBuilder(ConventionalItemTags.BERRY_FOODS)
                .add(blocks.bushes.get(BushType.BLUEBERRIES).asItem())
                .add(items.crops.get(CropType.STRAWBERRY).asItem());

        final var foods = valueLookupBuilder(ConventionalItemTags.FOODS);
        items.meals.sortedValues().map(DeferredItem::asItem).forEach(foods::add);
        foods.add(blocks.bushes.get(BushType.BLUEBERRIES).asItem())
                .add(blocks.bushes.get(BushType.GRAPES).asItem())
                .add(items.fruits.get(TreeType.LEMON).asItem())
                .add(items.fruits.get(TreeType.PEACH).asItem())
                .add(items.crops.get(CropType.BELL_PEPPER).asItem())
                .add(items.crops.get(CropType.BROCCOLI).asItem())
                .add(items.crops.get(CropType.CAULIFLOWER).asItem())
                .add(items.crops.get(CropType.CHILI_PEPPER).asItem())
                .add(items.crops.get(CropType.CORN).asItem())
                .add(items.crops.get(CropType.EGGPLANT).asItem())
                .add(items.crops.get(CropType.LETTUCE).asItem())
                .add(items.crops.get(CropType.ONION).asItem())
                .add(items.crops.get(CropType.PEANUT).asItem())
                .add(items.crops.get(CropType.STRAWBERRY).asItem())
                .add(items.crops.get(CropType.TOMATO).asItem())
                .add(items.crops.get(CropType.TURNIP).asItem());
        valueLookupBuilder(ConventionalItemTags.BREAD_FOODS)
                .add(items.meals.get(MealType.FLATBREAD).asItem());
        valueLookupBuilder(ConventionalItemTags.COOKED_FISH_FOODS)
                .add(items.meals.get(MealType.FISH_FILLET).asItem())
                .add(items.meals.get(MealType.FISH_STICKS).asItem());
        valueLookupBuilder(ConventionalItemTags.COOKED_MEAT_FOODS)
                .add(items.meals.get(MealType.BACON).asItem())
                .add(items.meals.get(MealType.CHICKEN_NUGGETS).asItem())
                .add(items.meals.get(MealType.SAUSAGE).asItem());
        valueLookupBuilder(ConventionalItemTags.CANDY_FOODS)
                .add(items.meals.get(MealType.CHOCOLATE).asItem());

        final var chickenFood = valueLookupBuilder(ItemTags.CHICKEN_FOOD);
        chickenFood.add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        blocks.crops.sortedValues().map(DeferredBlock::asItem).forEach(chickenFood::add);
        final var parrotFood = valueLookupBuilder(ItemTags.PARROT_FOOD);
        parrotFood.add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        blocks.crops.sortedValues().map(DeferredBlock::asItem).forEach(parrotFood::add);
        valueLookupBuilder(ItemTags.PIG_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.SHEEP_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.COW_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.HORSE_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.GOAT_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.RABBIT_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.CAMEL_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.ARMADILLO_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.TURTLE_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());
        valueLookupBuilder(ItemTags.LLAMA_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asItem());

        final var villagerPlantableSeeds = valueLookupBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS);
        blocks.crops.sortedValues().map(DeferredBlock::asItem).forEach(villagerPlantableSeeds::add);
        final var villagerPicksUp = valueLookupBuilder(ItemTags.VILLAGER_PICKS_UP);
        items.crops.sortedValues().map(DeferredItem::asItem).forEach(villagerPicksUp::add);

        final var saplings = valueLookupBuilder(ItemTags.SAPLINGS);
        blocks.saplings.sortedValues().map(DeferredBlock::asItem).forEach(saplings::add);

        final var leaves = valueLookupBuilder(ItemTags.LEAVES);
        blocks.leaves.sortedValues().map(DeferredBlock::asItem).forEach(leaves::add);
    }

    private static TagKey<Item> conventionalItemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }
}
