package net.blay09.mods.pantryforblockheads.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.blay09.mods.pantryforblockheads.block.ArtisanPressBlock;
import net.blay09.mods.pantryforblockheads.block.entity.ArtisanPressAnimation;
import net.blay09.mods.pantryforblockheads.block.entity.ArtisanPressBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ArtisanPressBlockEntityRenderer implements BlockEntityRenderer<ArtisanPressBlockEntity, ArtisanPressBlockEntityRenderer.RenderState> {
    private static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
    private static final BlockState CHAIN_STATE = Blocks.IRON_CHAIN.defaultBlockState();
    private static final float ANVIL_BASE_Y = 0.775f;
    private static final float CHAIN_BASE_Y = 1.05f;
    private static final float ITEM_Y = 0.65f;

    private final BlockModelResolver blockModelResolver;
    private final ItemModelResolver itemModelResolver;

    public ArtisanPressBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.blockModelResolver = context.blockModelResolver();
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(ArtisanPressBlockEntity blockEntity, RenderState renderState, float partialTick, net.minecraft.world.phys.Vec3 cameraPos, net.minecraft.client.renderer.feature.ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, breakProgress);
        renderState.facing = blockEntity.getBlockState().getValue(ArtisanPressBlock.FACING);

        blockModelResolver.update(renderState.upperChain, CHAIN_STATE, BLOCK_DISPLAY_CONTEXT);
        blockModelResolver.update(renderState.anvil, Blocks.ANVIL.defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
        final var animationState = ArtisanPressAnimation.sample(blockEntity.isProcessing(), blockEntity.getAnimationTick(), partialTick);
        renderState.anvilOffset = animationState.anvilOffset();
        renderState.chainRotation = animationState.chainRotation();

        renderState.inputItem.clear();
        final ItemStack inputItem = blockEntity.getInputItem();
        if (!inputItem.isEmpty() && blockEntity.getLevel() != null) {
            itemModelResolver.updateForTopItem(
                    renderState.inputItem,
                    inputItem,
                    ItemDisplayContext.FIXED,
                    blockEntity.getLevel(),
                    null,
                    (int) blockEntity.getBlockPos().asLong());
        }
    }

    @Override
    public void submit(RenderState renderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 0f, 0.5f);
        poseStack.rotateDegrees(Axis.YP, -renderState.facing.toYRot());
        poseStack.translate(-0.5f, 0f, -0.5f);

        poseStack.pushPose();
        poseStack.translate(0f, -0.2f, 0f);
        poseStack.translate(0.5f, 0f, 0.5f);
        poseStack.rotateDegrees(Axis.YP, renderState.chainRotation);
        poseStack.translate(-0.5f, 0f, -0.5f);
        submitBlockModel(renderState.upperChain, poseStack, submitNodeCollector, renderState.lightCoords, 0f, CHAIN_BASE_Y, 0f, 0.5f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5f, 0f, 0.5f);
        poseStack.rotateDegrees(Axis.YP, 90);
        poseStack.translate(-0.5f, 0f, -0.5f);
        poseStack.translate(0f, ANVIL_BASE_Y - renderState.anvilOffset, 0f);
        submitBlockModel(renderState.anvil, poseStack, submitNodeCollector, renderState.lightCoords, 0f, 0f, 0f, 0.4f);
        poseStack.popPose();

        if (!renderState.inputItem.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.5f, ITEM_Y, 0.5f);
            final float ITEM_SCALE = 0.3f;
            poseStack.scale(ITEM_SCALE, ITEM_SCALE, ITEM_SCALE);
            renderState.inputItem.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }

        poseStack.popPose();
    }

    private static void submitBlockModel(BlockModelRenderState blockModelRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, float x, float y, float z, float scale) {
        if (blockModelRenderState.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        if (scale != 1f) {
            poseStack.translate(0.5f, 0.5f, 0.5f);
            poseStack.scale(scale, scale, scale);
            poseStack.translate(-0.5f, -0.5f, -0.5f);
        }
        blockModelRenderState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    public static class RenderState extends BlockEntityRenderState {
        public final BlockModelRenderState upperChain = new BlockModelRenderState();
        public final BlockModelRenderState lowerChain = new BlockModelRenderState();
        public final BlockModelRenderState anvil = new BlockModelRenderState();
        public final ItemStackRenderState inputItem = new ItemStackRenderState();
        public Direction facing = Direction.NORTH;
        public float anvilOffset;
        public float chainRotation;
    }
}
