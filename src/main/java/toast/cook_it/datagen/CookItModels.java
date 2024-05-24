package toast.cook_it.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import toast.cook_it.CookIt;

import java.util.Optional;

public class CookItModels {
    static TextureKey PLATE = TextureKey.of("plate");
    static TextureKey BOWL = TextureKey.of("bowl");

    public static final Model TEMPLATE_PLATE_1 = newParent("plate_1", PLATE);
    public static final Model TEMPLATE_PLATE_2 = newParent("plate_2", PLATE);
    public static final Model TEMPLATE_PLATE_3 = newParent("plate_3", PLATE);
    public static final Model TEMPLATE_PLATE_4 = newParent("plate_4", PLATE);
    public static final Model TEMPLATE_LARGE_PLATE_1 = newParent("large_plate_1", PLATE);
    public static final Model TEMPLATE_LARGE_PLATE_2 = newParent("large_plate_2", PLATE);
    public static final Model TEMPLATE_LARGE_PLATE_3 = newParent("large_plate_3", PLATE);
    public static final Model TEMPLATE_LARGE_PLATE_4 = newParent("large_plate_4", PLATE);
    public static final Model TEMPLATE_BOWL = newParent("bowl", BOWL);

    private static Model newParent(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(CookIt.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    public static TextureMap coloredTextureMap(TextureKey type, Block block, String folder) {
        return TextureMap.of(type, setTextureOutput(block, "colored/" + folder + "/" + Registries.BLOCK.getId(block).getPath()));
    }

    public static Identifier setTextureOutput(Block block, String path) {
        Identifier identifier = Registries.BLOCK.getId(block);
        return identifier.withPath("block/" + path);
    }
    public static Identifier setModelOutput(String path, Block block) {
        return new Identifier(CookIt.MOD_ID, path + Registries.BLOCK.getId(block).getPath());}

    public static Identifier setModelOutput(String path, Block block, String suffix) {
        return new Identifier(CookIt.MOD_ID, path + Registries.BLOCK.getId(block).getPath() + suffix);
    }
}
