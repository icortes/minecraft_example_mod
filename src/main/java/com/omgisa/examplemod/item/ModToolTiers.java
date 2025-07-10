package com.omgisa.examplemod.item;

import com.omgisa.examplemod.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTiers {
    public static final Tier BISMUTH = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_BISMUTH_TOOL, 1400, 4.0F, 3.0F, 28, () -> Ingredient.of(ModItems.BISMUTH));
}
