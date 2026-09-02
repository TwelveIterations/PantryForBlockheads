package net.blay09.mods.pantryforblockheads.item;

import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;

public class PantryTreeGrowers {
    public static final TreeGrower LEMON = new TreeGrower("lemon", WeightedList.of(), WeightedList.of(), WeightedList.of(PantryTreeFeatures.LEMON), PantryTreeFeatures.LEMON);
    public static final TreeGrower PEACH = new TreeGrower("peach", WeightedList.of(), WeightedList.of(), WeightedList.of(PantryTreeFeatures.PEACH), PantryTreeFeatures.PEACH);
}
