package toast.cook_it.block.appliances.fryer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class FryerEntityRenderer implements BlockEntityRenderer<FryerEntity> {

    public FryerEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }


    @Override
    public void render(FryerEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

    }
}
