package com.omgisa.examplemod.worldgen.tree;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower BLOODWOOD = new TreeGrower(ExampleMod.MOD_ID + ":bloodwood", Optional.empty(), Optional.of(ModConfiguredFeatures.BLOODWOOD_KEY), Optional.empty());
}
