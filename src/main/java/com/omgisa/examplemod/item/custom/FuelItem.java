package com.omgisa.examplemod.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public class FuelItem extends Item {
    private int burnTime = 0;

    public FuelItem(Properties properties, int burnTime) {
        super(properties);
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime(@NotNull ItemStack itemStack, RecipeType<?> recipeType) {
        return this.burnTime;
    }
}
