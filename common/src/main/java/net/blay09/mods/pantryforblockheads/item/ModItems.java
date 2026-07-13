package net.blay09.mods.pantryforblockheads.item;

import net.blay09.mods.balm.world.item.BalmCreativeModeTabRegistrar;
import net.blay09.mods.balm.world.item.BalmItemRegistrar;
import net.blay09.mods.balm.world.item.DeferredItem;
import net.blay09.mods.balm.world.item.DiscriminatedItems;
import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.pantryforblockheads.PantryForBlockheads;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import static net.blay09.mods.pantryforblockheads.PantryForBlockheads.id;

public class ModItems {
    public final DeferredItem fryingPan;
    public final DeferredItem pot;
    public final DeferredItem mixingBowl;
    public final DeferredItem bakingSheet;
    public final DeferredItem knife;
    public final DeferredItem magicSprinkles;
    public final DiscriminatedItems<CropType> crops;
    public final DiscriminatedItems<TreeType> fruits;
    public final DiscriminatedItems<MealType> meals;

    private boolean postInitialized;

    public ModItems(BalmItemRegistrar items) {
        fryingPan = items.register("frying_pan", PantryUtensilItem::new).asDeferredItem();
        pot = items.register("pot", PantryUtensilItem::new).asDeferredItem();
        mixingBowl = items.register("mixing_bowl", PantryUtensilItem::new).asDeferredItem();
        bakingSheet = items.register("baking_sheet", PantryUtensilItem::new).asDeferredItem();
        knife = items.register("knife", PantryUtensilItem::new).asDeferredItem();
        magicSprinkles = items.register("magic_sprinkles", Item::new).asDeferredItem();

        crops = items.registerDiscriminated(CropType.values(), CropType::getSerializedName, (_, properties) -> new Item(properties), CropType::applyProperties).asDiscriminatedItems();
        fruits = items.registerDiscriminated(TreeType.values(), Enum::toString, (_, properties) -> new Item(properties), (type, it) -> it.food(type.foodProperties())).asDiscriminatedItems();
        meals = items.registerDiscriminated(MealType.values(), MealType::getSerializedName, (_, properties) -> new Item(properties), MealType::applyProperties).asDiscriminatedItems();
    }

    public void initializeCreativeModeTabs(BalmCreativeModeTabRegistrar creativeModeTabs) {
        creativeModeTabs.register(PantryForBlockheads.MOD_ID, builder ->
                builder.title(Component.translatable(id(PantryForBlockheads.MOD_ID).toLanguageKey("itemGroup")))
                        .icon(() -> crops.get(CropType.CHILI_PEPPER).createStack())
                        .displayItems((_, output) -> {
                            final var blocks = PantryForBlockheads.blocks();
                            output.accept(blocks.artisanPress);
                            output.accept(fryingPan);
                            output.accept(pot);
                            output.accept(mixingBowl);
                            output.accept(bakingSheet);
                            output.accept(knife);

                            blocks.bushes.sortedValues().map(DeferredBlock::asItem).forEach(output::accept);
                            crops.sortedValues().map(DeferredItem::asItem).forEach(output::accept);
                            fruits.sortedValues().map(DeferredItem::asItem).forEach(output::accept);
                            blocks.crops.sortedValues().map(DeferredBlock::asItem).forEach(output::accept);
                            blocks.saplings.sortedValues().map(DeferredBlock::asItem).forEach(output::accept);
                            blocks.leaves.sortedValues().map(DeferredBlock::asItem).forEach(output::accept);
                            meals.sortedValues().map(DeferredItem::asItem).forEach(output::accept);

                            output.accept(magicSprinkles);
                        })
        );
    }

    public void postInitialize() {
        // I hate this
        if (!postInitialized) {
            ((PantryUtensilItem) fryingPan.asItem()).postInitialize();
            ((PantryUtensilItem) bakingSheet.asItem()).postInitialize();
            ((PantryUtensilItem) mixingBowl.asItem()).postInitialize();
            ((PantryUtensilItem) pot.asItem()).postInitialize();
            ((PantryUtensilItem) knife.asItem()).postInitialize();
            postInitialized = true;
        }
    }
}
