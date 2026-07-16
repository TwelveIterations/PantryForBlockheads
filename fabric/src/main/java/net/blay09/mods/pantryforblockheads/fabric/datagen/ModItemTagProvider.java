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
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        final var blocks = PantryForBlockheads.blocks();
        final var items = PantryForBlockheads.items();
        builder(ModItemTags.ANY_EDIBLE_RAW_FISH)
                .add(ItemIds.COD)
                .add(ItemIds.SALMON)
                .add(ItemIds.TROPICAL_FISH);
        builder(ModItemTags.ANY_SUSHI_FILLING)
                .add(items.meals.get(MealType.TOFU).asResourceKey())
                .addTag(ModItemTags.ANY_EDIBLE_RAW_FISH)
                .add(ItemIds.COOKED_CHICKEN);
        builder(ModItemTags.ANY_DUMPLING_FILLING)
                .add(items.meals.get(MealType.TOFU).asResourceKey())
                .add(blocks.bushes.get(BushType.BLUEBERRIES).asBlockItemId())
                .addOptionalTag(ItemTags.EGGS)
                .add(ItemIds.BEETROOT)
                .add(ItemIds.PORKCHOP)
                .add(ItemIds.CHICKEN)
                .add(BlockItemIds.BROWN_MUSHROOM)
                .add(BlockItemIds.RED_MUSHROOM)
                .add(ItemIds.BEEF)
                .addTag(ModItemTags.ANY_EDIBLE_RAW_FISH)
                .add(ItemIds.COOKED_CHICKEN);
        builder(ModItemTags.ANY_PIZZA_VEGETABLE)
                .add(items.crops.get(CropType.BELL_PEPPER).asResourceKey())
                .add(items.crops.get(CropType.BROCCOLI).asResourceKey())
                .add(items.crops.get(CropType.CHILI_PEPPER).asResourceKey())
                .add(items.crops.get(CropType.ONION).asResourceKey())
                .add(items.crops.get(CropType.TOMATO).asResourceKey())
                .add(items.crops.get(CropType.EGGPLANT).asResourceKey());
        builder(ModItemTags.ANY_BERRY)
                .add(blocks.bushes.get(BushType.BLUEBERRIES).asBlockItemId())
                .add(BlockItemIds.SWEET_BERRY_CROP);
        builder(ModItemTags.EXCESS_NUTRITION_GRANTS_ABSORPTION)
                .add(items.meals.get(MealType.BURGER).asResourceKey())
                .add(items.meals.get(MealType.BURRITO).asResourceKey())
                .add(items.meals.get(MealType.CHEESE_PIZZA).asResourceKey())
                .add(items.meals.get(MealType.PEPPERONI_PIZZA).asResourceKey())
                .add(items.meals.get(MealType.VEGETABLE_PIZZA).asResourceKey())
                .add(items.meals.get(MealType.SANDWICH).asResourceKey())
                .add(items.meals.get(MealType.TACO).asResourceKey());

        final var crops = builder(ConventionalItemTags.CROPS);
        items.crops.sortedValues().map(DeferredItem::asResourceKey).forEach(crops::add);
        for (final var cropType : CropType.values()) {
            builder(conventionalItemTag("crops/" + cropType.getSerializedName()))
                    .add(items.crops.get(cropType).asResourceKey());
        }

        final var seeds = builder(ConventionalItemTags.SEEDS);
        blocks.crops.sortedValues().map(DeferredBlock::asBlockItemId).forEach(seeds::add);
        for (final var cropType : CropType.values()) {
            builder(conventionalItemTag("seeds/" + cropType.getSerializedName()))
                    .add(blocks.crops.get(cropType).asBlockItemId());
        }

        builder(ConventionalItemTags.VEGETABLE_FOODS)
                .add(items.crops.get(CropType.BELL_PEPPER).asResourceKey())
                .add(items.crops.get(CropType.BROCCOLI).asResourceKey())
                .add(items.crops.get(CropType.CAULIFLOWER).asResourceKey())
                .add(items.crops.get(CropType.CHILI_PEPPER).asResourceKey())
                .add(items.crops.get(CropType.CORN).asResourceKey())
                .add(items.crops.get(CropType.EGGPLANT).asResourceKey())
                .add(items.crops.get(CropType.LETTUCE).asResourceKey())
                .add(items.crops.get(CropType.ONION).asResourceKey())
                .add(items.crops.get(CropType.TOMATO).asResourceKey())
                .add(items.crops.get(CropType.TURNIP).asResourceKey());
        builder(ConventionalItemTags.FRUIT_FOODS)
                .add(blocks.bushes.get(BushType.GRAPES).asBlockItemId())
                .add(items.fruits.get(TreeType.LEMON).asResourceKey())
                .add(items.fruits.get(TreeType.PEACH).asResourceKey());
        builder(ConventionalItemTags.BERRY_FOODS)
                .add(blocks.bushes.get(BushType.BLUEBERRIES).asBlockItemId())
                .add(items.crops.get(CropType.STRAWBERRY).asResourceKey());

        final var foods = builder(ConventionalItemTags.FOODS);
        items.meals.sortedValues().map(DeferredItem::asResourceKey).forEach(foods::add);
        foods.add(blocks.bushes.get(BushType.BLUEBERRIES).asBlockItemId())
                .add(blocks.bushes.get(BushType.GRAPES).asBlockItemId())
                .add(items.fruits.get(TreeType.LEMON).asResourceKey())
                .add(items.fruits.get(TreeType.PEACH).asResourceKey())
                .add(items.crops.get(CropType.BELL_PEPPER).asResourceKey())
                .add(items.crops.get(CropType.BROCCOLI).asResourceKey())
                .add(items.crops.get(CropType.CAULIFLOWER).asResourceKey())
                .add(items.crops.get(CropType.CHILI_PEPPER).asResourceKey())
                .add(items.crops.get(CropType.CORN).asResourceKey())
                .add(items.crops.get(CropType.EGGPLANT).asResourceKey())
                .add(items.crops.get(CropType.LETTUCE).asResourceKey())
                .add(items.crops.get(CropType.ONION).asResourceKey())
                .add(items.crops.get(CropType.PEANUT).asResourceKey())
                .add(items.crops.get(CropType.STRAWBERRY).asResourceKey())
                .add(items.crops.get(CropType.TOMATO).asResourceKey())
                .add(items.crops.get(CropType.TURNIP).asResourceKey());
        builder(ConventionalItemTags.BREAD_FOODS)
                .add(items.meals.get(MealType.FLATBREAD).asResourceKey());
        builder(ConventionalItemTags.COOKED_FISH_FOODS)
                .add(items.meals.get(MealType.FISH_FILLET).asResourceKey())
                .add(items.meals.get(MealType.FISH_STICKS).asResourceKey());
        builder(ConventionalItemTags.COOKED_MEAT_FOODS)
                .add(items.meals.get(MealType.BACON).asResourceKey())
                .add(items.meals.get(MealType.CHICKEN_NUGGETS).asResourceKey())
                .add(items.meals.get(MealType.SAUSAGE).asResourceKey());
        builder(ConventionalItemTags.CANDY_FOODS)
                .add(items.meals.get(MealType.CHOCOLATE).asResourceKey());

        final var chickenFood = builder(ItemTags.CHICKEN_FOOD);
        chickenFood.add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        blocks.crops.sortedValues().map(DeferredBlock::asBlockItemId).forEach(chickenFood::add);
        final var parrotFood = builder(ItemTags.PARROT_FOOD);
        parrotFood.add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        blocks.crops.sortedValues().map(DeferredBlock::asBlockItemId).forEach(parrotFood::add);
        builder(ItemTags.PIG_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.SHEEP_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.COW_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.HORSE_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.GOAT_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.RABBIT_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.CAMEL_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.ARMADILLO_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.TURTLE_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());
        builder(ItemTags.LLAMA_FOOD).add(items.meals.get(MealType.MASHED_PRODUCE).asResourceKey());

        final var villagerPlantableSeeds = builder(ItemTags.VILLAGER_PLANTABLE_SEEDS);
        blocks.crops.sortedValues().map(DeferredBlock::asBlockItemId).forEach(villagerPlantableSeeds::add);
        final var villagerPicksUp = builder(ItemTags.VILLAGER_PICKS_UP);
        items.crops.sortedValues().map(DeferredItem::asResourceKey).forEach(villagerPicksUp::add);

        final var saplings = builder(ItemTags.SAPLINGS);
        blocks.saplings.sortedValues().map(DeferredBlock::asBlockItemId).forEach(saplings::add);

        final var leaves = builder(ItemTags.LEAVES);
        blocks.leaves.sortedValues().map(DeferredBlock::asBlockItemId).forEach(leaves::add);
    }

    private static TagKey<Item> conventionalItemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", path));
    }
}
