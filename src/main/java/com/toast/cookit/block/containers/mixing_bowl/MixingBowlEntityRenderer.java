package com.toast.cookit.block.containers.mixing_bowl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;


@Environment(EnvType.CLIENT)
public class MixingBowlEntityRenderer implements BlockEntityRenderer<MixingBowlEntity> {

    public MixingBowlEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(MixingBowlEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();

        ItemStack stack = blockEntity.getStack(0);


        if (!stack.isEmpty()) {

        }
    }
}