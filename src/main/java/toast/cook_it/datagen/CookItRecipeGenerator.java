package toast.cook_it.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import toast.cook_it.block.containers.Bowl;
import toast.cook_it.block.containers.plate.Plate;
import toast.cook_it.registries.CookItBlocks;

public class CookItRecipeGenerator extends FabricRecipeProvider {


    public CookItRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    public static String getColor(Plate plate) {
        return Registries.BLOCK.getId(plate).getPath().replace(Plate.isLargePlate(plate) ? "_large_plate" : "_plate", "");
    }

    public static String getColor(Bowl bowl) {
        return Registries.BLOCK.getId(bowl).getPath().replace("_bowl", "");
    }

    @Override
    public void generate(RecipeExporter exporter) {
        for (Plate plate : CookItBlocks.PLATES) {
            String color = getColor(plate);
            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, plate).pattern("ccc")
                    .input('c', Registries.BLOCK.get(new Identifier("minecraft", color + "_concrete")))
                    .criterion(FabricRecipeProvider.hasItem(plate),
                            FabricRecipeProvider.conditionsFromItem(plate))
                    .offerTo(exporter);
        }
        for (Bowl bowl : CookItBlocks.BOWLS) {
            String color = getColor(bowl);
            ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, bowl).pattern("c c").pattern("ccc")
                    .input('c', Registries.BLOCK.get(new Identifier("minecraft", color + "_concrete")))
                    .criterion(FabricRecipeProvider.hasItem(bowl),
                            FabricRecipeProvider.conditionsFromItem(bowl))
                    .offerTo(exporter);
        }
    }
}

