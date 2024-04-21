package toast.cook_it.block.appliances.oven;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Oven extends TranslucentBlock implements BlockEntityProvider {
    public static final BooleanProperty OPEN = BooleanProperty.of("open");
    public static final BooleanProperty ON = BooleanProperty.of("on");
    public static final Property<Direction> FACING = Properties.HORIZONTAL_FACING;

    public Oven(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(OPEN, false).with(ON, false).with(FACING, Direction.NORTH));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN).add(ON).add(FACING);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        boolean open = state.get(OPEN);
        OvenEntity blockEntity = (OvenEntity) world.getBlockEntity(pos);

        ItemStack heldItem = player.getStackInHand(hand).copyWithCount(1);

        if (world.isClient || blockEntity == null) {
            return ActionResult.SUCCESS;
        } else {
            if (!open && heldItem.isEmpty()) {
                world.setBlockState(pos, state.with(OPEN, true));
                world.playSound(null, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS);
                return ActionResult.PASS;
            }
            if (!player.getStackInHand(hand).isEmpty()) {
                // Check what is the first open slot and put an item from the player's hand there
                for (int i = 0; i < blockEntity.getItems().size(); i++) {
                    // Put the stack the player is holding into the inventory
                    if (blockEntity.getStack(i).isEmpty()) {
                        blockEntity.setStack(i, heldItem);
                        player.getStackInHand(hand).decrement(1);
                        break;
                    }
                }
            } else {
                // If the player is not holding anything, give them the items in the block entity one by one
                for (int i = blockEntity.getItems().size() - 1; i >= 0; i--) {
                    // Find the first slot that has an item and give it to the player
                    if (!blockEntity.getStack(i).isEmpty()) {
                        // Give the player the stack in the inventory
                        player.getInventory().offerOrDrop(blockEntity.getStack(i));
                        // Remove the stack from the inventory
                        blockEntity.setStack(i, ItemStack.EMPTY);
                        break;
                    }
                }
            }
        }

        return ActionResult.SUCCESS;
    }
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Nullable
    @Override
    public OvenEntity createBlockEntity(BlockPos pos, BlockState state) { return new OvenEntity(pos, state); }


}