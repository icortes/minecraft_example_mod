package com.omgisa.examplemod.block;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.block.custom.BismuthLampBlock;
import com.omgisa.examplemod.block.custom.GojiBerryBushBlock;
import com.omgisa.examplemod.block.custom.MagicBlock;
import com.omgisa.examplemod.block.custom.RadishCropBlock;
import com.omgisa.examplemod.item.ModItems;
import com.omgisa.examplemod.sound.ModSounds;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExampleMod.MOD_ID);

    public static final DeferredBlock<Block> BISMUTH_BLOCK = registerBlock("bismuth_block",
                                                                           () -> new Block(BlockBehaviour.Properties.of().strength(4F).requiresCorrectToolForDrops().sound(SoundType.AMETHYST)));
    public static final DeferredBlock<StairBlock> BISMUTH_STAIRS = registerBlock("bismuth_stairs",
                                                                                 () -> new StairBlock(ModBlocks.BISMUTH_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<TrapDoorBlock> BISMUTH_TRAPDOOR = registerBlock("bismuth_trapdoor",
                                                                                      () -> new TrapDoorBlock(BlockSetType.IRON,
                                                                                                              BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<DoorBlock> BISMUTH_DOOR = registerBlock("bismuth_door",
                                                                              () -> new DoorBlock(BlockSetType.IRON,
                                                                                                  BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<WallBlock> BISMUTH_WALL = registerBlock("bismuth_wall",
                                                                              () -> new WallBlock(BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<FenceGateBlock> BISMUTH_FENCE_GATE = registerBlock("bismuth_fence_gate",
                                                                                         () -> new FenceGateBlock(WoodType.ACACIA,
                                                                                                                  BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<FenceBlock> BISMUTH_FENCE = registerBlock("bismuth_fence",
                                                                                () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> BISMUTH_ORE = registerBlock("bismuth_ore",
                                                                         () -> new DropExperienceBlock(UniformInt.of(2, 4),
                                                                                                       BlockBehaviour.Properties.of().strength(3F).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final DeferredBlock<Block> BISMUTH_DEEPSLATE_ORE = registerBlock("bismuth_deepslate_ore",
                                                                                   () -> new DropExperienceBlock(UniformInt.of(3, 6),
                                                                                                                 BlockBehaviour.Properties.of().strength(4F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<Block> MAGIC_BLOCK = registerBlock("magic_block",
                                                                         () -> new MagicBlock(BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops().sound(ModSounds.MAGIC_BLOCK_SOUNDS)));
    public static final DeferredBlock<SlabBlock> BISMUTH_SLAB = registerBlock("bismuth_slab",
                                                                              () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<PressurePlateBlock> BISMUTH_PRESSURE_PLATE = registerBlock("bismuth_pressure_plate",
                                                                                                 () -> new PressurePlateBlock(BlockSetType.IRON, BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<ButtonBlock> BISMUTH_BUTTON = registerBlock("bismuth_button",
                                                                                  () -> new ButtonBlock(BlockSetType.IRON, 20, BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops().noCollission()));
    public static final DeferredBlock<Block> BISMUTH_LAMP = registerBlock("bismuth_lamp",
                                                                          () -> new BismuthLampBlock(BlockBehaviour.Properties.of().strength(2F).requiresCorrectToolForDrops().lightLevel(state -> state.getValue(BismuthLampBlock.CLICKED) ? 15 : 0)));

    public static final DeferredBlock<Block> RADISH_CROP = BLOCKS.register("radish_crop",
                                                                           () -> new RadishCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEETROOTS)));

    public static final DeferredBlock<Block> GOJI_BERRY_BUSH = BLOCKS.register("goji_berry_bush",
                                                                               () -> new GojiBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
