package com.toast.cookit.block.containers.plate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import org.jetbrains.annotations.Nullable;
import com.toast.cookit.registries.CookItItems;

public class Plate extends Block implements BlockEntityProvider {

    public static final IntProperty PLATES_AMOUNT = IntProperty.of("plate_amount", 1, 4);

    public Plate(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PLATES_AMOUNT, 1));
    }



    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PLATES_AMOUNT);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        int plateAmount = state.get(PLATES_AMOUNT);
        if (isLargePlate((Plate) state.getBlock())) {
            return VoxelShapes.cuboid(0.125f, 0f, 0.125f, 0.875f, 0.0625f * plateAmount, 0.875f);
        } else {
            return VoxelShapes.cuboid(0.25f, 0f, 0.25f, 0.75f, 0.0625f * plateAmount, 0.75f);
        }


    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        PlateEntity blockEntity = (PlateEntity) world.getBlockEntity(pos);
        int plateAmount = state.get(PLATES_AMOUNT);
        ItemStack heldItem = player.getStackInHand(hand);

        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            // If there is no item in the player's hand and there is more than one plate, give one plate
            // Otherwise give back whatever is on the plate (because there's only one sooo)
            if (heldItem.isEmpty()) {
                if (plateAmount > 1) {
                    player.getInventory().offerOrDrop(new ItemStack(world.getBlockState(pos).getBlock()));
                    world.setBlockState(pos, state.with(PLATES_AMOUNT, plateAmount - 1));
                    world.playSound(null, pos, SoundEvents.BLOCK_LANTERN_BREAK, SoundCategory.BLOCKS, 1, 3.0f);
                } else if (!blockEntity.getStack(0).isEmpty()) {
                    player.getInventory().offerOrDrop(blockEntity.getStack(0));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 3.0f);
                    blockEntity.setStack(0, ItemStack.EMPTY);
                }
            } else {
                // If there is a plate in the player's hand, and there are less than 5 plates in the stack, add one to the stack
                // If there is anything else in the player's hand, put that item on the plate.
                if (heldItem.getItem() instanceof BlockItem blockItem) {
                    if (blockItem.getBlock() == world.getBlockState(pos).getBlock() && plateAmount < 4 && blockEntity.getStack(0) == ItemStack.EMPTY) {
                        heldItem.decrement(1);
                        world.playSound(null, pos, SoundEvents.BLOCK_COPPER_PLACE, SoundCategory.BLOCKS, 1, 1.75f);
                        world.setBlockState(pos, state.with(PLATES_AMOUNT, plateAmount + 1));
                    }
                    return ActionResult.SUCCESS;
                } else if (heldItem.getItem().equals(CookItItems.FRYER_BASKET)) {
                    return ActionResult.FAIL;
                } else if (blockEntity.getStack(0).isEmpty() && plateAmount == 1) {
                    blockEntity.setStack(0, new ItemStack(heldItem.getItem()));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 1.0f);
                    heldItem.decrement(1);
                }
            }
        }
        return ActionResult.PASS;
    }

    public static boolean isLargePlate(Plate plate) {
        return Registries.BLOCK.getId(plate).getPath().contains("large_plate");
    }

    @Nullable

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new PlateEntity(pos, state); }
}