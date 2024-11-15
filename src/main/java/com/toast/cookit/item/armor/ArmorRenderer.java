package com.toast.cookit.item.armor;

import com.toast.cookit.CookItClient;
import com.toast.cookit.compat.FiguraCompat;
import com.toast.cookit.item.armor.ChefOutfit.ChefOutfitItem;
import com.toast.cookit.mixin.PlayerEntityModelAccessor;
import com.toast.cookit.registries.CookItItems;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.List;

public class ArmorRenderer {

    public static final List<Item> HAZMAT_SUIT =
            List.of(
                    CookItItems.CHEF_UNIFORM,
                    CookItItems.CHEF_PANTS
            );

    static void renderPart(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack, Model model, Identifier texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(texture), false, stack.hasGlint());
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
    }

    public static void register() {
        net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer renderer = (matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            ChefOutfitItem armor = (ChefOutfitItem) stack.getItem();

            boolean shouldRender = (!CookItClient.isFiguraLoaded || FiguraCompat.renderArmorPart((PlayerEntity) entity, slot));
            boolean hasSlimArms = false;

            if (contextModel instanceof PlayerEntityModel<?>) { hasSlimArms = ((PlayerEntityModelAccessor)contextModel).getThinArms(); }

            var texture = armor.getArmorTexture(hasSlimArms);
            var model = armor.getArmorModel(hasSlimArms);
            if (shouldRender) {
                contextModel.copyBipedStateTo(model);
                renderPart(matrices, vertexConsumers, light, stack, model, texture);
            }
        };
        net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer.register(renderer, HAZMAT_SUIT.toArray(new Item[0]));
    }

}
