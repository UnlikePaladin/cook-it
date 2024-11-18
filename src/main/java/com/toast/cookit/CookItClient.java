package com.toast.cookit;

import com.toast.cookit.item.armor.ChefOutfit.render.ChefOutfitModel;
import com.toast.cookit.item.armor.ChefOutfitRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import com.toast.cookit.block.containers.baking_sheet.BakingSheetItemRenderer;
import com.toast.cookit.registries.*;


import static com.toast.cookit.CookIt.MOD_ID;

@Environment(EnvType.CLIENT)
public class CookItClient implements ClientModInitializer {
    public static final EntityModelLayer CHEF_OUTFIT = new EntityModelLayer(new Identifier(MOD_ID,"chef_outfit"), "main");

    public static boolean isFiguraLoaded;


    public void onInitializeClient() {
        isFiguraLoaded = (FabricLoader.getInstance().isModLoaded("figura"));

        CookItBlockEntities.registerRenderers();
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.OVEN, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.MICROWAVE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.MUFFIN_TIN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.FRYER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.PIZZA, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.UNCOOKED_PIZZA, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(CookItBlocks.PIZZA_PAN, RenderLayer.getTranslucent());

        ChefOutfitRenderer.register();
        EntityModelLayerRegistry.registerModelLayer(CHEF_OUTFIT, () -> TexturedModelData.of(ChefOutfitModel.getModelData(), 64, 64));

        BuiltinItemRendererRegistry.INSTANCE.register(CookItBlocks.BAKING_SHEET.asItem(), new BakingSheetItemRenderer());
        ModelPredicateProviderRegistry.register(CookItItems.FIRE_EXTINGUISHER, new Identifier("extinguisher_fuel"), (stack, world, entity, seed) -> (float) Math.round(((float) stack.getMaxDamage() - stack.getDamage()) / 100) / 10);
        ParticleFactoryRegistry.getInstance().register(CookIt.OIL_PARTICLE, OilParticle.Factory::new);

    }
}