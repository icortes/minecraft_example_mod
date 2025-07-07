package com.omgisa.examplemod.item;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExampleMod.MOD_ID);

    public static final Supplier<CreativeModeTab> BISMUTH_ITEMS_TAB = CREATIVE_MODE_TAB.register("bismuth_items_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                                 .title(Component.translatable("creativetab.examplemod.bismuth_items"))
                                 .displayItems((itemDisplayParameters, output) -> {
                                     // Add items to the creative tab
                                    output.accept(ModItems.BISMUTH);
                                    output.accept(ModItems.RAW_BISMUTH);
                                    output.accept(ModItems.CHISEL);
                                    output.accept(ModItems.RADISH);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> BISMUTH_BLOCK_TAB = CREATIVE_MODE_TAB.register("bismuth_blocks_tab",
           () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.BISMUTH_BLOCK.get()))
                                .withTabsBefore(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "bismuth_items_tab"))
                                .title(Component.translatable("creativetab.examplemod.bismuth_blocks"))
                                .displayItems((itemDisplayParameters, output) -> {
                                    // Add blocks to the creative tab
                                    output.accept(ModBlocks.BISMUTH_BLOCK);
                                    output.accept(ModBlocks.BISMUTH_ORE);
                                    output.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE);
                                    output.accept(ModBlocks.MAGIC_BLOCK);
                                })
                                .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
