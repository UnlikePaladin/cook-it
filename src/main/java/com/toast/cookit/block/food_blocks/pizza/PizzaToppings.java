package com.toast.cookit.block.food_blocks.pizza;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public enum PizzaToppings {
    PORKCHOP(Items.PORKCHOP, "porkchop"),
    BEEF(Items.COOKED_BEEF, "beef"),
    KELP(Items.KELP, "kelp");

    private final Item item;
    private final String name;
    PizzaToppings(Item item, String name) {
        this.item = item;
        this.name = name;
    }

    public Item getItem() {
        return this.item;
    }

    public String getName() {
        return this.name;
    }
}
