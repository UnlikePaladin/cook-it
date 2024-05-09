package toast.cook_it.block.containers.cutting_board;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.math.BlockPos;
import toast.cook_it.block.CookingBlockEntity;
import toast.cook_it.block.ImplementedInventory;
import toast.cook_it.recipes.CuttingBoardRecipe;
import toast.cook_it.registries.CookItBlockEntities;

import java.util.Objects;
import java.util.Optional;

public class CuttingBoardEntity extends CookingBlockEntity implements ImplementedInventory {

    public CuttingBoardEntity(BlockPos pos, BlockState state) {
        super(CookItBlockEntities.CUTTING_BOARD_ENTITY, pos, state, 1);
    }

    public void rollItem() {
        Optional<RecipeEntry<CuttingBoardRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent() && recipe.get().value().getRecipeType().equals("rolling")) {
            ItemStack output = recipe.get().value().getResult(null).getItem().getDefaultStack();
            this.setStack(0, output);
        }
    }


    public void cutItem(PlayerEntity player, ItemStack item) {
        Optional<RecipeEntry<CuttingBoardRecipe>> recipe = getCurrentRecipe();
        if (recipe.isPresent() && recipe.get().value().getRecipeType().equals("cutting")) {
            ItemStack output = recipe.get().value().getResult(null).getItem().getDefaultStack();
            item.damage(1, player, playerEntity -> player.sendToolBreakStatus(player.getActiveHand()));
            this.setStack(0, output);
        }
    }


    private Optional<RecipeEntry<CuttingBoardRecipe>> getCurrentRecipe() {
        SimpleInventory inv = new SimpleInventory(this.size());
        for (int i = 0; i < this.size(); i++) {
            inv.setStack(i, this.getStack(i));
        }
        return Objects.requireNonNull(getWorld()).getRecipeManager().getFirstMatch(CuttingBoardRecipe.Type.INSTANCE, inv, getWorld());
    }
}
