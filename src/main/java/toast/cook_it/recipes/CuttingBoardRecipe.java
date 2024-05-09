package toast.cook_it.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;
import toast.cook_it.block.containers.cutting_board.CuttingBoard;

import java.util.List;

public class CuttingBoardRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final Ingredient ingredient;
    private final String recipeType;

    public CuttingBoardRecipe(Ingredient ingredient, ItemStack itemStack, String recipeType) {
        this.output = itemStack;
        this.ingredient = ingredient;
        this.recipeType = recipeType;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) {
            return false;
        }
        return ingredient.test(inventory.getStack(0));
    }

    @Override
    public boolean isIgnoredInRecipeBook() { return true; }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    public String getRecipeType() {
        return recipeType;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CuttingBoardRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<CuttingBoardRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Codec<CuttingBoardRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("input").forGetter(r -> r.ingredient),
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output),
                Codec.STRING.fieldOf("recipeType").forGetter(CuttingBoardRecipe::getRecipeType)
        ).apply(in, CuttingBoardRecipe::new));

        @Override
        public Codec<CuttingBoardRecipe> codec() {
            return CODEC;
        }

        @Override
        public CuttingBoardRecipe read(PacketByteBuf buf) {

            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            String recipeType = buf.readString();
            return new CuttingBoardRecipe(ingredient, output, recipeType);
        }

        @Override
        public void write(PacketByteBuf buf, CuttingBoardRecipe recipe) {
            recipe.ingredient.write(buf);
            buf.writeItemStack(recipe.getResult(null));
            buf.writeString(recipe.recipeType);
        }
    }
}
