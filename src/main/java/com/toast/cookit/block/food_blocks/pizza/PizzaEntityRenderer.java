package com.toast.cookit.block.food_blocks.pizza;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtList;

@Environment(EnvType.CLIENT)
public class PizzaEntityRenderer implements BlockEntityRenderer<PizzaEntity> {
    public PizzaEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(PizzaEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        NbtList toppings = entity.getToppings();
        if (!toppings.isEmpty()) {
        }
    }
}
