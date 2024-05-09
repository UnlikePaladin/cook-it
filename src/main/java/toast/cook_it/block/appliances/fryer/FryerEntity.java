package toast.cook_it.block.appliances.fryer;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import toast.cook_it.block.CookingBlockEntity;
import toast.cook_it.block.ImplementedInventory;
import toast.cook_it.registries.CookItBlockEntities;

public class FryerEntity extends CookingBlockEntity implements ImplementedInventory {

    public FryerEntity(BlockPos pos, BlockState state) {
        super(CookItBlockEntities.FRYER_ENTITY, pos, state, 1);
    }
}
