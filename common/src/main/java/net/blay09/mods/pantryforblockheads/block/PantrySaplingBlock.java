package net.blay09.mods.pantryforblockheads.block;

import net.blay09.mods.pantryforblockheads.item.TreeType;
import net.minecraft.world.level.block.SaplingBlock;

public class PantrySaplingBlock extends SaplingBlock {
    private final TreeType treeType;

    protected PantrySaplingBlock(TreeType treeType, Properties properties) {
        super(treeType.treeGrower(), properties);
        this.treeType = treeType;
    }

    public TreeType getTreeType() {
        return treeType;
    }
}
