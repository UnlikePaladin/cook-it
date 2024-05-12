package toast.cook_it.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import toast.cook_it.registries.CookItItems;

import java.util.List;

public class Fries extends Item {

    public Fries(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getItem() == CookItItems.UNCOOKED_FRENCH_FRIES) {
            tooltip.add(1, Text.literal("Uncooked").formatted(Formatting.ITALIC, Formatting.GRAY));
        }
    }
}
