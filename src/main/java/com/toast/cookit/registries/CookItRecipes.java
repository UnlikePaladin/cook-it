package com.toast.cookit.registries;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.toast.cookit.CookIt;
import com.toast.cookit.recipes.CuttingBoardRecipe;
import com.toast.cookit.recipes.FryerRecipe;
import com.toast.cookit.recipes.MicrowaveRecipe;
import com.toast.cookit.recipes.OvenRecipe;

public class CookItRecipes {

    public static void registerRecipes() {
       Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "microwaving"),
               MicrowaveRecipe.Serializer.INSTANCE);

       Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "microwaving"),
               MicrowaveRecipe.Type.INSTANCE);

       Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "baking"),
               OvenRecipe.Serializer.INSTANCE);

       Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "baking"),
               OvenRecipe.Type.INSTANCE);

       Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "cutting_board"),
               CuttingBoardRecipe.Serializer.INSTANCE);

       Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "cutting_board"),
               CuttingBoardRecipe.Type.INSTANCE);

       Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "frying"),
               FryerRecipe.Serializer.INSTANCE);

       Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "frying"),
               FryerRecipe.Type.INSTANCE);

    }
}