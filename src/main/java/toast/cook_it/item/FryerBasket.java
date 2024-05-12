package toast.cook_it.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FryerBasket extends Item {
    public FryerBasket(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        ItemStack item = getItem(slot.getStack());
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();

            if (!otherStack.isEmpty() && item.isEmpty()) {
                setItem(itemStack, otherStack.copyWithCount(1));
                otherStack.decrement(1);
            } else if (!item.isEmpty()) {
                player.getInventory().offerOrDrop(item);
                setItem(itemStack, ItemStack.EMPTY);
            }
        }
        return true;
    }

    public void setItem(ItemStack input, ItemStack item) {
        NbtList nbtList = new NbtList();
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putByte("Slot", (byte) 0);

        item.writeNbt(nbtCompound);
        nbtList.add(nbtCompound);
        input.getOrCreateNbt().put("Items", nbtList);
        if (item == ItemStack.EMPTY) {
            input.removeSubNbt("Items");
        }
    }

    public ItemStack getItem(ItemStack input) {
        NbtCompound itemsTag = input.getNbt();
        if (itemsTag != null) {
            NbtList list = itemsTag.getList("Items", NbtElement.COMPOUND_TYPE);
            return ItemStack.fromNbt(list.getCompound(0));
        } else { return ItemStack.EMPTY; }
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Text text = Text.literal("Item: ").formatted(Formatting.GRAY).append(Text.literal(this.getItem(stack).getName().getString()).formatted(Formatting.BLUE));
        if (this.getItem(stack) != ItemStack.EMPTY) {
            tooltip.add(text);
        } else {
            tooltip.remove(text);
        }
    }
}
