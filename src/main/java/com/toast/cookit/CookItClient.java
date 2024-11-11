package com.toast.cookit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import com.toast.cookit.block.containers.baking_sheet.BakingSheetItemRenderer;
import com.toast.cookit.registries.*;

@Environment(EnvType.CLIENT)
public class CookItClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // Here we will put client-only registration code
        CookItBlockEntities.registerRenderers();
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.OVEN, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.MICROWAVE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.MUFFIN_TIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.FRYER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.PIZZA, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.PIZZA_PAN, RenderLayer.getTranslucent());


        BuiltinItemRendererRegistry.INSTANCE.register(CookItBlocks.BAKING_SHEET.asItem(), new BakingSheetItemRenderer());
        ModelPredicateProviderRegistry.register(CookItItems.FIRE_EXTINGUISHER, new Identifier("extinguisher_fuel"), (stack, world, entity, seed) -> (float) Math.round(((float) stack.getMaxDamage() - stack.getDamage()) / 100) / 10);
        ParticleFactoryRegistry.getInstance().register(CookIt.OIL_PARTICLE, OilParticle.Factory::new);
    }
}