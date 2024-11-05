package com.toast.cookit.block.containers.muffin_tin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import com.toast.cookit.block.CookingBlockEntity;
import com.toast.cookit.block.ImplementedInventory;
import com.toast.cookit.registries.CookItBlockEntities;

public class MuffinTinEntity extends CookingBlockEntity implements ImplementedInventory {
    public MuffinTinEntity(BlockPos pos, BlockState state) {
        super(CookItBlockEntities.MUFFIN_TIN_ENTITY, pos, state, 6);
    }


}