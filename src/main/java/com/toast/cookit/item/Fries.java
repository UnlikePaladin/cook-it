package com.toast.cookit.item;

import com.toast.cookit.registries.CookItFoodTypes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import com.toast.cookit.registries.CookItItems;

import java.util.List;

public class Fries extends CookItFood {

    public Fries(Settings settings, CookItFoodTypes foodType) {
        super(settings, foodType);
    }



    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getItem() == CookItItems.UNCOOKED_FRENCH_FRIES) {
            tooltip.add(1, Text.literal("Uncooked").formatted(Formatting.ITALIC, Formatting.GRAY));
        }
    }
}
