package com.toast.cookit.block.containers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import com.toast.cookit.block.food_blocks.pizza.Pizza;
import com.toast.cookit.registries.CookItBlocks;

public class PizzaPan extends Block {
    /* States
    * 0 - Nothing in pizza pan
    * 1 - uncooked pizza
    * 2 - cooked pizza
    */

    public static final IntProperty PIZZA_STATE = IntProperty.of("state", 0, 2);
    public PizzaPan(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PIZZA_STATE, 0));
    }
    private final VoxelShape OUTLINE = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return OUTLINE;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PIZZA_STATE);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int pizzaState = state.get(PIZZA_STATE);
        boolean hasPizza = pizzaState > 0;

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof Pizza && !hasPizza) {
                heldItem.decrement(1);
                world.setBlockState(pos, state.with(PIZZA_STATE, blockItem.getBlock().equals(CookItBlocks.UNCOOKED_PIZZA) ? 1 : 2));
            }
        } else if (heldItem.isEmpty() && hasPizza) {
            player.getInventory().offerOrDrop(pizzaState == 2 ? new ItemStack(CookItBlocks.PIZZA, 1) : new ItemStack(CookItBlocks.UNCOOKED_PIZZA, 1));
            world.setBlockState(pos, state.with(PIZZA_STATE, 0));
        } else {
            return ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }
}
