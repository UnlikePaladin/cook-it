package com.toast.cookit.block.appliances.fryer;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import com.toast.cookit.registries.CookItBlockEntities;
import com.toast.cookit.registries.CookItItems;

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
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        FryerEntity blockEntity = (FryerEntity) world.getBlockEntity(pos);
        if (blockEntity == null) { return ActionResult.FAIL; }

        ItemStack heldItem = player.getStackInHand(hand);
        if (blockEntity.isEmpty()) {
            if (heldItem.getItem().equals(CookItItems.FRYER_BASKET)) {
                blockEntity.setStack(0, heldItem.copyAndEmpty());
            } else {
                return ActionResult.FAIL;
            }
        } else {
            player.getInventory().insertStack(blockEntity.getStack(0));
        }
        return ActionResult.SUCCESS;
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

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, CookItBlockEntities.FRYER_ENTITY,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world, pos, state));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new FryerEntity(pos, state); }
}

