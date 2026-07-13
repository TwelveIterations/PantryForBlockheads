package net.blay09.mods.pantryforblockheads.fabric.datagen;

import net.blay09.mods.balm.world.item.DeferredItem;
import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.MealType;
import net.blay09.mods.pantryforblockheads.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
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
}
