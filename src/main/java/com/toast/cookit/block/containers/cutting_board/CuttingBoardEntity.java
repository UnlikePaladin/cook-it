package com.toast.cookit.block.containers.cutting_board;

import com.toast.cookit.CookIt;
import com.toast.cookit.block.food_blocks.pizza.PizzaToppings;
import com.toast.cookit.registries.CookItBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import com.toast.cookit.block.CookingBlockEntity;
import com.toast.cookit.block.ImplementedInventory;
import com.toast.cookit.recipes.CuttingBoardRecipe;
import com.toast.cookit.registries.CookItBlockEntities;

import java.util.List;
import java.util.Objects;

public class CuttingBoardEntity extends CookingBlockEntity implements ImplementedInventory {
    private int clicks = 0;

    public CuttingBoardEntity(BlockPos pos, BlockState state) {
        super(CookItBlockEntities.CUTTING_BOARD_ENTITY, pos, state, 1);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.clicks = nbt.getInt("cutting_board.clicks");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putInt("cutting_board.clicks", clicks);
        super.writeNbt(nbt);
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }
    public int getClicks() { return this.clicks; }


    public void processRecipe(ItemStack tool, boolean tryReset) {
        if (this.getStack(0).isOf(Item.fromBlock(CookItBlocks.UNCOOKED_PIZZA)) || this.getStack(0).isOf(Item.fromBlock(CookItBlocks.PIZZA_CRUST))) {processPizza(tool); return;}
        List<RecipeEntry<CuttingBoardRecipe>> recipes = getCurrentRecipe();
        if (!recipes.isEmpty()) {
            this.clicks++;
            for(RecipeEntry<CuttingBoardRecipe> recipeEntry : recipes) {

                if (recipeEntry.value().isResetable() && tryReset && tool.isEmpty()) {
                    complete(recipeEntry);
                    return;
                }
                for (ItemStack otherTool : recipeEntry.value().getTool()) {
                    if (tool.getItem().asItem().equals(otherTool.getItem()) && !recipeEntry.value().isResetable()) {
                        Objects.requireNonNull(this.getWorld()).playSound(null, pos.getX(), pos.getY(), pos.getZ(),SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 0.5f, 0.25f);
                        ((ServerWorld) Objects.requireNonNull(this.getWorld())).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, recipeEntry.value().getResult(null)), pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, 10, 0f,0.025f,0.0f,0.125f);
                        if (this.clicks < recipeEntry.value().getClicks()) { return; }
                        complete(recipeEntry);
                        if (recipeEntry.value().usesItem()) {
                            tool.decrement(1);
                        }
                        return;
                    }
                }
            }
        }
    }

    public void processPizza(ItemStack tool) {

        NbtList toppings = this.getStack(0).getOrCreateSubNbt("BlockEntityTag").getList("toppings", 8);
        CookIt.LOGGER.warn(String.valueOf(this.getStack(0).getNbt()));
        if (!toppings.getString(2).isEmpty()) { return; }

        String name = PizzaToppings.fromItem(tool.getItem());

        if (name != null) {
            for (int i = 0; i < 3; i++) {
                if (toppings.getString(i).isEmpty()) {
                    toppings.add(NbtString.of(name));
                    tool.decrement(1);
                    break;
                }
            }
            this.getStack(0).getOrCreateSubNbt("BlockEntityTag").put("toppings", toppings);
        }
    }

    private void complete(RecipeEntry<CuttingBoardRecipe> recipeEntry) {
        ItemStack output = recipeEntry.value().getResult(null);
        output.setCount(recipeEntry.value().getOutputCount());
        this.setStack(0, output);
        this.clicks = 0;
    }

    private List<RecipeEntry<CuttingBoardRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return Objects.requireNonNull(getWorld()).getRecipeManager().getAllMatches(CuttingBoardRecipe.Type.INSTANCE, inv, getWorld());
    }
}
