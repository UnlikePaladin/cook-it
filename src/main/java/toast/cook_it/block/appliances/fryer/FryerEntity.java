package toast.cook_it.block.appliances.fryer;


import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import toast.cook_it.block.CookingBlockEntity;
import toast.cook_it.block.ImplementedInventory;
import toast.cook_it.item.FryerBasket;
import toast.cook_it.recipes.FryerRecipe;
import toast.cook_it.registries.CookItBlockEntities;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static toast.cook_it.block.appliances.fryer.Fryer.ON;


public class FryerEntity extends CookingBlockEntity implements ImplementedInventory {
    private static final int Fryer_SOUND_INTERVAL = 111;
    int progress = 0;
    private int maxProgress = 0;

    public FryerEntity(BlockPos pos, BlockState state) {
        super(CookItBlockEntities.FRYER_ENTITY, pos, state, 2);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        items.clear();
        super.readNbt(nbt);

        Inventories.readNbt(nbt, items);
        progress = nbt.getInt("fryer.progress");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        Inventories.writeNbt(nbt, this.items);
        nbt.putInt("fryer.progress", progress);
        super.writeNbt(nbt);
    }

    public void tick(World world, BlockPos pos, BlockState state) {

        if (world.isClient()) {
            return;
        }

        if (this.hasRecipe()) {
            world.setBlockState(pos, state.with(ON, true));
//            if (progress == 0 || world.getTime() % Fryer_SOUND_INTERVAL == 0) {
//                //playFryerSound(world, pos, state, true);
//            }

            this.updateMaxProgress();
            this.addProgress();
            markDirty(world, pos, state);

            if (craftingFinished()) {
                // Stop the continuous Fryer sound and play the world's most annoying beep sound
                //playFryerSound(world, pos, state, false);
                world.setBlockState(pos, state.with(ON, false));
                this.craftRecipe();
                this.resetProgress();

            }
        } else {
            this.resetProgress();
        }
    }

    private void updateMaxProgress() {
        Optional<RecipeEntry<FryerRecipe>> recipe = getCurrentRecipe();

        maxProgress = recipe.get().value().getMaxProgress();
    }

    private void craftRecipe() {
        ItemStack container = this.getStack(0);
        Optional<RecipeEntry<FryerRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent()) {
            if (!container.isEmpty() && recipe.get().value().getMaxProgress() <= this.progress) {
                ((FryerBasket) container.getItem()).setItem(container, recipe.get().value().getResult(null).getItem().getDefaultStack());
            }
        }

    }


    private void resetProgress() {
        this.progress = 0;
    }

    private boolean craftingFinished() {
        return this.maxProgress == this.progress;
    }

    private void addProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeEntry<FryerRecipe>> recipe = getCurrentRecipe();

        return recipe.isPresent();
    }

    private Optional<RecipeEntry<FryerRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        ArrayList<ItemStack> item = getContainerItems(this.getStack(0));
        if (item.isEmpty()) {
            return Optional.empty();
        }
        inv.setStack(0, item.get(0));

        return Objects.requireNonNull(getWorld()).getRecipeManager().getFirstMatch(FryerRecipe.Type.INSTANCE, inv, getWorld());
    }

    @Override
    public ArrayList<ItemStack> getContainerItems(ItemStack container) {

        ArrayList<ItemStack> itemStackList = new ArrayList<>();
        NbtCompound nbt = container.getNbt();
        if (nbt != null && nbt.contains("Items")) {
            NbtList itemsTag = nbt.getList("Items", NbtElement.COMPOUND_TYPE);
            for (int j = 0; j < itemsTag.size(); j++) {
                NbtCompound itemTag = itemsTag.getCompound(j);
                ItemStack itemStack = ItemStack.fromNbt(itemTag);

                itemStackList.add(itemStack);
            }
        }
        return itemStackList;
    }

//    private void playFryerSound(World world, BlockPos pos, BlockState state, boolean on) {
//        if (on && !state.get(OPEN)) {
//            world.playSound(null, pos, CookItSounds.Fryer_SOUND_EVENT, SoundCategory.BLOCKS, 0.3f, 1.0f);
//            world.setBlockState(pos, state.with(Fryer.ON, true));
//        } else {
//            world.setBlockState(pos, state.with(ON, false));
//            if (state.get(OPEN)) return;
//            world.playSound(null, pos, CookItSounds.Fryer_BEEP_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
//        }
//    }
}

