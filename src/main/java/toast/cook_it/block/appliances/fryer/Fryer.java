package toast.cook_it.block.appliances.fryer;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Fryer extends BlockWithEntity implements BlockEntityProvider {
    public static final BooleanProperty ON = BooleanProperty.of("on");
    public static final Property<Direction> FACING = Properties.HORIZONTAL_FACING;
    public Fryer(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ON, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(FACING)) {
            case NORTH, SOUTH -> {
                return VoxelShapes.cuboid(0.1875f, 0f, 0.0625f, 0.8125f, 0.5f, 0.9375f);
            }
            default -> {
                return VoxelShapes.cuboid(0.0625f, 0f, 0.1875f, 0.9375f, 0.5f, 0.8125f);
            }

        }
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }



    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ON, FACING);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new FryerEntity(pos, state); }
}

