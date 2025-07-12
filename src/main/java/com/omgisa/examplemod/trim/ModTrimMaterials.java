package com.omgisa.examplemod.trim;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class ModTrimMaterials {
    public static final ResourceKey<TrimMaterial> BISMUTH =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "bismuth"));

    public static void bootstrap(BootstrapContext<TrimMaterial> context) {
        register(context, BISMUTH, ModItems.BISMUTH.get(), Style.EMPTY.withColor(TextColor.parseColor("#031cfc").getOrThrow()), 0.5F);
    }

    /**
     * Registers a trim material with the given parameters.
     *
     * @param context        The bootstrap context for registration.
     * @param trimKey        The resource key for the trim material.
     * @param item           The item associated with the trim material.
     * @param style          The style for the trim material's description.
     * @param itemModelIndex The index for the item model.
     */
    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item, Style style, float itemModelIndex) {
        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
                                                        Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimmaterial);
    }
}
