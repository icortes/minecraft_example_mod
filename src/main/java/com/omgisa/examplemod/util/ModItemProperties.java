package com.omgisa.examplemod.util;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.component.ModDataComponents;
import com.omgisa.examplemod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(),
                                ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "used"),
                                (stack, level, entity, seed) -> stack.get(ModDataComponents.COORDINATES) != null ? 1F : 0F);
    }
}
