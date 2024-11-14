package com.toast.cookit.block.containers.cutting_board;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.math.BlockPos;
import com.toast.cookit.block.CookingBlockEntity;
import com.toast.cookit.block.ImplementedInventory;
import com.toast.cookit.recipes.CuttingBoardRecipe;
import com.toast.cookit.registries.CookItBlockEntities;

import java.util.List;
import java.util.Objects;

public class CuttingBoardEntity extends CookingBlockEntity implements ImplementedInventory {

    public CuttingBoardEntity(BlockPos pos, BlockState state) {
        super(CookItBlockEntities.CUTTING_BOARD_ENTITY, pos, state, 1);
    }


    public boolean processRecipe(ItemStack tool) {
        List<RecipeEntry<CuttingBoardRecipe>> recipes = getCurrentRecipe();

        if (!recipes.isEmpty()) {
            for(RecipeEntry<CuttingBoardRecipe> recipeEntry : recipes) {
                for (ItemStack otherTool : recipeEntry.value().getTool()) {
                    if (tool.getItem().asItem().equals(otherTool.getItem())) {
                        Item item = recipeEntry.value().getResult(null).getItem();
                        ItemStack output = new ItemStack(item, recipeEntry.value().getOutputCount());
                        this.setStack(0, output);
                        if (recipeEntry.value().usesItem()) {
                            tool.decrement(1);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<RecipeEntry<CuttingBoardRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return Objects.requireNonNull(getWorld()).getRecipeManager().getAllMatches(CuttingBoardRecipe.Type.INSTANCE, inv, getWorld());
    }
}
