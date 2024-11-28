package com.toast.cookit.datagen;

import com.toast.cookit.block.containers.plate.Plate;
import com.toast.cookit.block.food_blocks.pizza.CookedPizza;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import com.toast.cookit.registries.CookItBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

import java.util.List;

public class CookItLootTables extends FabricBlockLootTableProvider {
    List<Block> blocks = CookItBlocks.BLOCKS;

    public CookItLootTables(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        blocks.remove(CookItBlocks.BAKING_SHEET);
        blocks.remove(CookItBlocks.PIZZA_PAN);
        blocks.remove(CookItBlocks.PIZZA);
        blocks.remove(CookItBlocks.PLATES.toArray(new Block[0]));
        for(Block block : blocks) {
            addDrop(block, drops(block));
        }
        for(Block plate : CookItBlocks.PLATES ) {
            addDrop(plate, (Block block) ->
                    LootTable.builder().pool(
                            LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(plate.asItem())
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).conditionally(
                                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Plate.PLATES_AMOUNT, 1))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)).conditionally(
                                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Plate.PLATES_AMOUNT, 2))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3.0f)).conditionally(
                                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Plate.PLATES_AMOUNT, 3))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f)).conditionally(
                                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Plate.PLATES_AMOUNT, 4))))
                            )
                    )
            );
        }
        addDrop(CookItBlocks.PIZZA, ((Block block) ->
                LootTable.builder().pool(
                        LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f)).with(ItemEntry.builder(CookItBlocks.PIZZA.asItem())
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).conditionally(
                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(CookedPizza.PIZZA_AMOUNT, 1))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0f)).conditionally(
                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(CookedPizza.PIZZA_AMOUNT, 2))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3.0f)).conditionally(
                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(CookedPizza.PIZZA_AMOUNT, 3))))
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4.0f)).conditionally(
                                        BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(CookedPizza.PIZZA_AMOUNT, 4))))
                        )
                ))
        );
    }

}
