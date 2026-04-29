package net.blay09.mods.pantryforblockheads.block;

import net.blay09.mods.balm.world.item.DiscriminatedItems;
import net.blay09.mods.balm.world.level.block.BalmBlockRegistrar;
import net.blay09.mods.balm.world.level.block.DeferredBlock;
import net.blay09.mods.balm.world.level.block.DiscriminatedBlocks;
import net.blay09.mods.pantryforblockheads.item.BushType;
import net.blay09.mods.pantryforblockheads.item.CropType;
import net.blay09.mods.pantryforblockheads.item.TreeType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {

    public final DeferredBlock artisanPress;
    public final DiscriminatedBlocks<BushType> bushes;
    public final DiscriminatedBlocks<CropType> crops;
    public final DiscriminatedBlocks<TreeType> saplings;
    public final DiscriminatedBlocks<TreeType> pottedSaplings;
    public final DiscriminatedBlocks<TreeType> leaves;

    public ModBlocks(BalmBlockRegistrar blocks) {
        artisanPress = blocks.register("artisan_press", ArtisanPressBlock::new, it -> it
                        .strength(2.5f)
                        .requiresCorrectToolForDrops())
                .withDefaultItem()
                .asDeferredBlock();
        bushes = blocks.registerDiscriminated(BushType.values(), BushType::bushName, PantryBushBlock::new, it -> it
                        .mapColor(MapColor.PLANT).randomTicks().noCollision().sound(SoundType.SWEET_BERRY_BUSH).pushReaction(PushReaction.DESTROY))
                .withItems(Enum::toString, BlockItem::new, (type, properties) -> properties.useItemDescriptionPrefix().food(type.foodProperties()))
                .asDiscriminatedBlocks();
        crops = blocks.registerDiscriminated(CropType.values(), CropType::plural, PantryCropBlock::new, it -> it
                        .mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY))
                .withItems(DiscriminatedItems.suffixWith("seeds"), BlockItem::new, (_, properties) -> properties.useItemDescriptionPrefix())
                .asDiscriminatedBlocks();
        saplings = blocks.registerDiscriminated(TreeType.values(), DiscriminatedBlocks.suffixWith("sapling"), PantrySaplingBlock::new, it -> it
                        .mapColor(MapColor.PLANT).noCollision().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY))
                .withDefaultItems()
                .asDiscriminatedBlocks();
        pottedSaplings = blocks.registerDiscriminated(TreeType.values(), it -> "potted_" + it + "_sapling", (type, properties) -> new PantryFlowerPotBlock(saplings.get(type).asBlock(), properties), it -> it
                        .instabreak().noOcclusion().pushReaction(PushReaction.DESTROY))
                .withDefaultItems()
                .asDiscriminatedBlocks();
        leaves = blocks.registerDiscriminated(TreeType.values(), DiscriminatedBlocks.suffixWith("leaves"), (_, properties) -> new PantryLeavesBlock(0.01f, properties), it -> it
                        .mapColor(MapColor.PLANT)
                        .strength(0.2f)
                        .randomTicks()
                        .sound(SoundType.GRASS)
                        .noOcclusion()
                        .isValidSpawn(this::ocelotOrParrot)
                        .isSuffocating(this::never)
                        .isViewBlocking(this::never)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
                        .isRedstoneConductor(this::never))
                .withDefaultItems()
                .asDiscriminatedBlocks();
    }

    private boolean ocelotOrParrot(BlockState state, BlockGetter blockGetter, BlockPos blockPos, EntityType<?> entityType) {
        return entityType == EntityTypes.OCELOT || entityType == EntityTypes.PARROT;
    }

    private boolean never(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }

}
