package com.toast.cookit.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.World;

public class CuttingBoardRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final Ingredient ingredient;
    private final int count;
    private final Ingredient tool;
    private final boolean usesItem;

    public CuttingBoardRecipe(Ingredient ingredient, ItemStack itemStack, int count, Ingredient tool, boolean usesItem) {
        this.output = itemStack;
        this.ingredient = ingredient;
        this.count = count;
        this.tool = tool;
        this.usesItem = usesItem;
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

    public ItemStack[] getTool() {
        if (tool.isEmpty()) {return new ItemStack[]{ ItemStack.EMPTY}; }
        return tool.getMatchingStacks();
    }

    public boolean usesItem() { return usesItem; }

    public int getOutputCount() {
        return count;
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
                Codec.INT.optionalFieldOf("count", 1).forGetter(r -> r.count),
                Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("tool", Ingredient.EMPTY).forGetter(r -> r.tool),
                Codec.BOOL.optionalFieldOf("usesItem", false).forGetter(r -> r.usesItem)
        ).apply(in, CuttingBoardRecipe::new));

        @Override
        public Codec<CuttingBoardRecipe> codec() {
            return CODEC;
        }

        @Override
        public CuttingBoardRecipe read(PacketByteBuf buf) {

            Ingredient ingredient = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            Ingredient tool = Ingredient.fromPacket(buf);
            int count = buf.readInt();
            boolean usesItem = buf.readBoolean();
            return new CuttingBoardRecipe(ingredient, output, count, tool, usesItem);
        }

        @Override
        public void write(PacketByteBuf buf, CuttingBoardRecipe recipe) {
            recipe.ingredient.write(buf);
            buf.writeItemStack(recipe.getResult(null));
            buf.readInt();
            recipe.tool.write(buf);
            buf.writeBoolean(recipe.usesItem());
        }
    }
}
