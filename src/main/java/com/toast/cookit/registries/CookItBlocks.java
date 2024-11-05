package com.toast.cookit.registries;

import com.toast.cookit.block.containers.mixing_bowl.MixingBowl;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import com.toast.cookit.CookIt;
import com.toast.cookit.block.Bench;
import com.toast.cookit.block.appliances.Toaster;
import com.toast.cookit.block.appliances.fryer.Fryer;
import com.toast.cookit.block.appliances.microwave.Microwave;
import com.toast.cookit.block.appliances.oven.Oven;
import com.toast.cookit.block.containers.Bowl;
import com.toast.cookit.block.containers.cutting_board.CuttingBoard;
import com.toast.cookit.block.containers.PizzaPan;
import com.toast.cookit.block.containers.baking_sheet.BakingSheet;
import com.toast.cookit.block.containers.muffin_tin.MuffinTin;
import com.toast.cookit.block.containers.plate.Plate;
import com.toast.cookit.block.food_blocks.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

public class CookItBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Plate> PLATES = new ArrayList<>();
    public static final List<Bowl> BOWLS = new ArrayList<>();

    // -- Appliances --
    public static final Block FRYER = registerBlock("fryer", new Fryer(FabricBlockSettings.create().nonOpaque()));
    public static final Block TOASTER = registerBlock("toaster", new Toaster(FabricBlockSettings.copyOf(Blocks.WHITE_CONCRETE)));
    public static final Block OVEN = registerBlock("oven", new Oven(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));
    public static final Block MICROWAVE = registerBlock("microwave", new Microwave(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));

    // -- Food Blocks --
    public static final Block PIZZA = registerBlock("pizza", new Pizza(FabricBlockSettings.create()));
    public static final Block UNCOOKED_PIZZA = registerBlock("uncooked_pizza", new Pizza(FabricBlockSettings.create()));

    // -- Containers --
    public static final Block CUTTING_BOARD = registerBlock("cutting_board", new CuttingBoard(FabricBlockSettings.copyOf(Blocks.SPRUCE_PLANKS)));
    public static final Block MUFFIN_TIN = registerBlock("muffin_tin", new MuffinTin(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).nonOpaque()));;
    public static final Block BAKING_SHEET = registerBlock("baking_sheet", new BakingSheet(FabricBlockSettings.create()));
    public static final Block PIZZA_PAN = registerBlock("pizza_pan", new PizzaPan(FabricBlockSettings.create()));
    public static final Block MIXING_BOWL = registerBlock("mixing_bowl", new MixingBowl(FabricBlockSettings.create()));
    // -- Miscellaneous --
    public static final Block BENCH = registerBlock("bench", new Bench(FabricBlockSettings.create()));


    public static void registerColoredBlocks() {
        for (DyeColor color : DyeColor.values()) {
            Block PLATE = registerBlock(color + "_plate", new Plate(FabricBlockSettings.create().sounds(BlockSoundGroup.DECORATED_POT)));
            Block LARGE_PLATE = registerBlock(color + "_large_plate", new Plate(FabricBlockSettings.create().sounds(BlockSoundGroup.DECORATED_POT)));
            Block BOWL = registerBlock(color + "_bowl", new Bowl(FabricBlockSettings.create().sounds(BlockSoundGroup.DECORATED_POT)));
            CookItBlocks.PLATES.add((Plate) PLATE);
            CookItBlocks.PLATES.add((Plate) LARGE_PLATE);
            CookItBlocks.BOWLS.add((Bowl) BOWL);
        }
    }

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        CookItBlocks.BLOCKS.add(block);
        return Registry.register(Registries.BLOCK, new Identifier(CookIt.MOD_ID, name), block);
    }

    public static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(CookIt.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerBlocks() {
        registerColoredBlocks();
    }


}