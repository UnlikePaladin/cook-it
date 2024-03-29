package toast.cook_it.block.appliances.oven;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import static toast.cook_it.block.appliances.oven.Oven.FACING;

@Environment(EnvType.CLIENT)
public class OvenEntityRenderer implements BlockEntityRenderer<OvenEntity> {

    public OvenEntityRenderer(BlockEntityRendererFactory.Context ctx) { }
    @Override
    public void render(OvenEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        final MinecraftClient client = MinecraftClient.getInstance();
        for (int i = 0; i < 2; i++) {
            ItemStack stack = blockEntity.getStack(i);

            if (!stack.isEmpty()) {
                matrices.push();
                matrices.scale(0.875f,0.875f,0.875f);
                Direction facing = blockEntity.getCachedState().get(FACING);

                matrices.translate(0.5625f, 0.3f * i + 0.9125f, 0.5625f);
                if (facing == Direction.NORTH || facing == Direction.SOUTH) {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
                }
                client.getItemRenderer().renderItem(stack, ModelTransformationMode.NONE, light, overlay, matrices, vertexConsumers, blockEntity.getWorld(), 0);
                matrices.pop();
            }
        }
        }
}
