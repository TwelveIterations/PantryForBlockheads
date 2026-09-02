package net.blay09.mods.pantryforblockheads.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.sounds.AmbientLeavesBlockSoundPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PantryLeavesBlock extends UntintedParticleLeavesBlock {
    private static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public PantryLeavesBlock(float leafParticleChance, Properties properties) {
        super(leafParticleChance, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 0xff48b518), AmbientLeavesBlockSoundPlayer.noAmbientSound(), properties);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) || !isMaxAge(state);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        final var age = getAge(state);
        if (!isMaxAge(state)) {
            float growthSpeed = getGrowthSpeed(this, state, level, pos);
            if (growthSpeed <= 0f) {
                return;
            }
            if (random.nextInt((int)(25f / growthSpeed) + 1) == 0) {
                level.setBlock(pos, state.setValue(getAgeProperty(), age + 1), Block.UPDATE_CLIENTS);
            }
        }
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    public int getAge(BlockState state) {
        return state.getValue(getAgeProperty());
    }

    public BlockState getStateForAge(int age) {
        return defaultBlockState().setValue(getAgeProperty(), age);
    }

    public final boolean isMaxAge(BlockState state) {
        return getAge(state) >= getMaxAge();
    }

    private float getGrowthSpeed(PantryLeavesBlock type, BlockState state, ServerLevel level, BlockPos pos) {
        if (state.getValue(PERSISTENT)) {
            return 0f;
        }

        for (final var direction : Direction.values()) {
            final var adjacentState = level.getBlockState(pos.relative(direction));
            if (adjacentState.getBlock() instanceof PantryLeavesBlock adjacentLeaves &&
                    adjacentLeaves == type &&
                    adjacentLeaves.isMaxAge(adjacentState)) {
                return 0f;
            }
        }

        return 1f;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(getAgeProperty());
    }
}
