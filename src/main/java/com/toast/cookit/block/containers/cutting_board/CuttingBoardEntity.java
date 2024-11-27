package com.toast.cookit.block.containers.cutting_board;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
        List<RecipeEntry<CuttingBoardRecipe>> recipes = getCurrentRecipe();
        if (!recipes.isEmpty()) {
            this.clicks++;
            for(RecipeEntry<CuttingBoardRecipe> recipeEntry : recipes) {

                if (recipeEntry.value().isResetable() && tryReset && tool.isEmpty()) {
                    Item item = recipeEntry.value().getResult(null).getItem();
                    ItemStack output = new ItemStack(item, recipeEntry.value().getOutputCount());
                    this.setStack(0, output);
                    return;
                }

                for (ItemStack otherTool : recipeEntry.value().getTool()) {
                    if (tool.getItem().asItem().equals(otherTool.getItem()) && !recipeEntry.value().isResetable()) {
                        Objects.requireNonNull(this.getWorld()).playSound(null, pos.getX(), pos.getY(), pos.getZ(),SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 0.5f, 0.25f);

                        ((ServerWorld) Objects.requireNonNull(this.getWorld())).spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, recipeEntry.value().getResult(null)), pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f, 10, 0f,0.025f,0.0f,0.125f);
                        if (this.clicks < recipeEntry.value().getClicks()) { return; }

                        Item item = recipeEntry.value().getResult(null).getItem();
                        ItemStack output = new ItemStack(item, recipeEntry.value().getOutputCount());
                        this.setStack(0, output);
                        this.clicks = 0;
                        if (recipeEntry.value().usesItem()) {
                            tool.decrement(1);
                        }

                        return;
                    }
                }
            }
        }
    }

    private List<RecipeEntry<CuttingBoardRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return Objects.requireNonNull(getWorld()).getRecipeManager().getAllMatches(CuttingBoardRecipe.Type.INSTANCE, inv, getWorld());
    }
}
