package com.toast.cookit.block.food_blocks.pizza;

import com.toast.cookit.registries.CookItBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

public class PizzaEntity extends BlockEntity{

    private NbtList toppings = new NbtList();

    public PizzaEntity(BlockPos pos, BlockState state) { super(CookItBlockEntities.PIZZA_ENTITY, pos, state); }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.toppings = nbt.getList("toppings", 8);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.put("toppings", toppings);
        super.writeNbt(nbt);
    }

    public NbtList getToppings() {
        return toppings;
    }
}

