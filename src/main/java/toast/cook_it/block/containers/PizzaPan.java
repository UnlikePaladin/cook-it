package toast.cook_it.block.containers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import toast.cook_it.block.food_blocks.pizza.Pizza;
import toast.cook_it.registries.CookItBlocks;

public class PizzaPan extends Block {
    public static final BooleanProperty COOKED = BooleanProperty.of("cooked");
    public static final BooleanProperty HAS_PIZZA = BooleanProperty.of("has_pizza");

    public PizzaPan(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(COOKED, false).with(HAS_PIZZA, false));
    }
    private final VoxelShape OUTLINE = VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return OUTLINE;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COOKED, HAS_PIZZA);
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        boolean hasPizza = state.get(HAS_PIZZA);
        boolean cooked = state.get(COOKED);

        ItemStack heldItem = player.getStackInHand(hand);

        if (heldItem.getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof Pizza && !hasPizza) {
                heldItem.decrement(1);
                world.setBlockState(pos, state.with(COOKED, blockItem.getBlock().equals(CookItBlocks.PIZZA)).with(HAS_PIZZA, true));
            }
        } else if (heldItem.isEmpty() && hasPizza) {
            player.getInventory().offerOrDrop(cooked ? new ItemStack(CookItBlocks.PIZZA, 1) : new ItemStack(CookItBlocks.UNCOOKED_PIZZA, 1));
            world.setBlockState(pos, state.with(COOKED, false).with(HAS_PIZZA, false));
        } else {
            return ActionResult.PASS;
        }
        return ActionResult.SUCCESS;
    }
}
