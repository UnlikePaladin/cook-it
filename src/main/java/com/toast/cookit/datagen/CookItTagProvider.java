package com.toast.cookit.datagen;

import com.toast.cookit.registries.CookItBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class CookItTagProvider extends FabricTagProvider.BlockTagProvider {
    public CookItTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(CookItBlocks.CUTTING_BOARDS.toArray(new Block[0]));
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(CookItBlocks.PLATES.toArray(new Block[0]))
                .add(CookItBlocks.APPLIANCES.toArray(new Block[0]))
                .add(CookItBlocks.CONTAINERS.toArray(new Block[0]));

    }
}
