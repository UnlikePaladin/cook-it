package com.toast.cookit.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import com.toast.cookit.block.containers.Bowl;
import com.toast.cookit.block.containers.plate.Plate;
import com.toast.cookit.registries.CookItBlocks;

import static com.toast.cookit.datagen.CookItModels.*;

public class CookItModelProvider extends FabricModelProvider {

    public CookItModelProvider(FabricDataOutput output) {
        super(output);
    }


    private static void generateColoredBlocks(BlockStateModelGenerator blockStateModelGenerator) {
        for (Plate plate : CookItBlocks.PLATES) {
            TextureMap textureMap = coloredTextureMap(PLATE, plate, "plate");
            Identifier identifier, identifier2, identifier3, identifier4;
            if (Plate.isLargePlate(plate)) {
                identifier = TEMPLATE_LARGE_PLATE_1.upload(setModelOutput("block/plate/", plate, "_1"), textureMap, blockStateModelGenerator.modelCollector);
                identifier2 = TEMPLATE_LARGE_PLATE_2.upload(setModelOutput("block/plate/", plate, "_2"), textureMap, blockStateModelGenerator.modelCollector);
                identifier3 = TEMPLATE_LARGE_PLATE_3.upload(setModelOutput("block/plate/", plate, "_3"), textureMap, blockStateModelGenerator.modelCollector);
                identifier4 = TEMPLATE_LARGE_PLATE_4.upload(setModelOutput("block/plate/", plate, "_4"), textureMap, blockStateModelGenerator.modelCollector);
            } else {
                identifier = TEMPLATE_PLATE_1.upload(setModelOutput("block/plate/", plate, "_1"), textureMap, blockStateModelGenerator.modelCollector);
                identifier2 = TEMPLATE_PLATE_2.upload(setModelOutput("block/plate/", plate, "_2"), textureMap, blockStateModelGenerator.modelCollector);
                identifier3 = TEMPLATE_PLATE_3.upload(setModelOutput("block/plate/", plate, "_3"), textureMap, blockStateModelGenerator.modelCollector);
                identifier4 = TEMPLATE_PLATE_4.upload(setModelOutput("block/plate/", plate, "_4"), textureMap, blockStateModelGenerator.modelCollector);
            }
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(plate).coordinate(BlockStateVariantMap.create(Plate.PLATES_AMOUNT).register(1, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).register(2, BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).register(3, BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).register(4, BlockStateVariant.create().put(VariantSettings.MODEL, identifier4))));
            blockStateModelGenerator.registerParentedItemModel(plate, identifier);
        }
        for (Bowl bowl : CookItBlocks.BOWLS) {
            TextureMap textureMap = coloredTextureMap(BOWL, bowl, "bowl");
            Identifier identifier = TEMPLATE_BOWL.upload(setModelOutput("block/bowl/", bowl), textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(bowl, BlockStateVariant.create().put(VariantSettings.MODEL, identifier)));
            blockStateModelGenerator.registerParentedItemModel(bowl, identifier);
        }
    }


    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        generateColoredBlocks(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }
}
