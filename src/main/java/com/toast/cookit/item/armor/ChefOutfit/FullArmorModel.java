package com.toast.cookit.item.armor.ChefOutfit;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class FullArmorModel extends BipedEntityModel<LivingEntity> {
    final EquipmentSlot slot;
    public FullArmorModel(ModelPart root, EquipmentSlot slot) {
        super(root);
        this.slot = slot;
    }

    @Override
    public void render(MatrixStack ms, VertexConsumer buffer, int light, int overlay, float r, float g, float b, float a) {
        renderArmorSlot(slot);
        super.render(ms, buffer, light, overlay, r, g, b, a);
    }

    private void renderArmorSlot(EquipmentSlot slot) {
        setVisible(false);
        switch (slot) {
            case CHEST -> {
                body.visible = true;
                rightArm.visible = true;
                leftArm.visible = true;
            }
            case LEGS -> {
                rightLeg.visible = true;
                leftLeg.visible = true;
            }
            case MAINHAND, OFFHAND -> { }
        }
    }
}
