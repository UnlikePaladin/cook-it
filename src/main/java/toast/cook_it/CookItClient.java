package toast.cook_it;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import toast.cook_it.block.containers.baking_sheet.BakingSheetItemRenderer;
import toast.cook_it.registries.*;

@Environment(EnvType.CLIENT)
public class CookItClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // Here we will put client-only registration code
        CookItBlockEntities.registerRenderers();
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.OVEN, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.MICROWAVE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.MUFFIN_TIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.FRYER, RenderLayer.getCutout());

        BuiltinItemRendererRegistry.INSTANCE.register(CookItBlocks.BAKING_SHEET.asItem(), new BakingSheetItemRenderer());
        ModelPredicateProviderRegistry.register(CookItItems.FIRE_EXTINGUISHER, new Identifier("extinguisher_fuel"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {

                return (float) (stack.getDamage() / 100);
            }
        });
    }
}