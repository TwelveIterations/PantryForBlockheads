package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.item.DeferredItem;
import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.MealType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.blay09.mods.pantryforblockheads.tag.ModItemTags;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;

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
    }
}
