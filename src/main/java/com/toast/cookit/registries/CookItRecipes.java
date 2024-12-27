package com.toast.cookit.registries;

import com.toast.cookit.recipes.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.toast.cookit.CookIt;

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

       Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "cutting"),
               CuttingBoardRecipe.Serializer.INSTANCE);

       Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "cutting"),
               CuttingBoardRecipe.Type.INSTANCE);

       Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "frying"),
               FryerRecipe.Serializer.INSTANCE);

       Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "frying"),
               FryerRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(CookIt.MOD_ID, "mixing"),
                MixingBowlRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_TYPE, new Identifier(CookIt.MOD_ID, "mixing"),
                MixingBowlRecipe.Type.INSTANCE);

    }
}