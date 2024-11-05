package com.toast.cookit.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import com.toast.cookit.registries.CookItBlocks;

import java.util.List;

public class CookItLootTables extends FabricBlockLootTableProvider {
    List<Block> blocks = CookItBlocks.BLOCKS;

    public CookItLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        blocks.remove(CookItBlocks.BAKING_SHEET);
        for(Block block : blocks) {
            addDrop(block, drops(block));
        }
    }

}
