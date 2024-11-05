package com.toast.cookit.block.containers.mixing_bowl;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;

public class MixingBowl extends Block implements BlockEntityProvider {

    public MixingBowl(Settings settings) {
        super(settings);
    }
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity player, Hand hand, BlockHitResult blockHitResult) {
        world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_LISTENERS);
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return VoxelShapes.cuboid(0.1875, 0f, 0.0625f, 0.8125f, 0.125f, 0.9375f);
    }
    @Override
    public void appendTooltip(ItemStack stack, BlockView world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getSubNbt("BlockEntityTag");
        if (nbt == null || !nbt.contains("Items")) return;

        NbtList itemsTag = nbt.getList("Items", NbtElement.COMPOUND_TYPE);

        if (!itemsTag.isEmpty()) {
            tooltip.add((Text.literal("Items:").formatted(Formatting.GRAY)));
        }

        for (int i = 0; i < itemsTag.size(); i++) {
            NbtCompound itemTag = itemsTag.getCompound(i);
            ItemStack itemStack = ItemStack.fromNbt(itemTag);
            if (!itemStack.isEmpty()) {
                String itemName = itemStack.getName().getString();
                tooltip.add((Text.literal(itemName).formatted(Formatting.BLUE)));
            }
        }
    }
}