package com.omgisa.examplemod.datagen;

import com.omgisa.examplemod.block.ModBlocks;
import com.omgisa.examplemod.block.custom.RadishCropBlock;
import com.omgisa.examplemod.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.BISMUTH_BLOCK.get());
        dropSelf(ModBlocks.MAGIC_BLOCK.get());

        add(ModBlocks.BISMUTH_ORE.get(),
            block -> createOreDrop(ModBlocks.BISMUTH_ORE.get(), ModItems.RAW_BISMUTH.get()));
        add(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(),
            block -> createMultipleOreDrops(ModBlocks.BISMUTH_DEEPSLATE_ORE.get(), ModItems.RAW_BISMUTH.get(), 2.0F, 5.0F));

        add(ModBlocks.BISMUTH_END_ORE.get(),
            block -> createMultipleOreDrops(ModBlocks.BISMUTH_END_ORE.get(), ModItems.RAW_BISMUTH.get(), 3.0F, 6.0F));
        add(ModBlocks.BISMUTH_NETHER_ORE.get(),
            block -> createMultipleOreDrops(ModBlocks.BISMUTH_NETHER_ORE.get(), ModItems.RAW_BISMUTH.get(), 4.0F, 8.0F));


        dropSelf(ModBlocks.BISMUTH_STAIRS.get());
        add(ModBlocks.BISMUTH_SLAB.get(),
            block -> createSlabItemTable(ModBlocks.BISMUTH_SLAB.get()));

        dropSelf(ModBlocks.BISMUTH_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.BISMUTH_BUTTON.get());

        dropSelf(ModBlocks.BISMUTH_FENCE.get());
        dropSelf(ModBlocks.BISMUTH_FENCE_GATE.get());
        dropSelf(ModBlocks.BISMUTH_WALL.get());
        dropSelf(ModBlocks.BISMUTH_DOOR.get());
        dropSelf(ModBlocks.BISMUTH_TRAPDOOR.get());

        add(ModBlocks.BISMUTH_DOOR.get(),
            block -> createDoorTable(ModBlocks.BISMUTH_DOOR.get()));

        dropSelf(ModBlocks.BISMUTH_LAMP.get());

        LootItemCondition.Builder lootItemConditionBuilder =
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.RADISH_CROP.get())
                                                   .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(RadishCropBlock.AGE, RadishCropBlock.MAX_AGE));
        this.add(ModBlocks.RADISH_CROP.get(), this.createCropDrops(ModBlocks.RADISH_CROP.get(),
                                                                   ModItems.RADISH.get(), ModItems.RADISH_SEEDS.get(), lootItemConditionBuilder));

        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.add(ModBlocks.GOJI_BERRY_BUSH.get(),
                 block -> this.applyExplosionDecay(block,
                                                   LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GOJI_BERRY_BUSH.get())
                                                                                                                                              .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3)))
                                                                                          .add(LootItem.lootTableItem(ModItems.GOJI_BERRIES.get()))
                                                                                          .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                                                                          .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))))
                                                            .withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.GOJI_BERRY_BUSH.get())
                                                                                                                                  .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2)))
                                                                              .add(LootItem.lootTableItem(ModItems.GOJI_BERRIES.get()))
                                                                              .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                                                              .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                                                            )));

        this.dropSelf(ModBlocks.BLOODWOOD_LOG.get());
        this.dropSelf(ModBlocks.BLOODWOOD_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_BLOODWOOD_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_BLOODWOOD_WOOD.get());
        this.dropSelf(ModBlocks.BLOODWOOD_PLANKS.get());
        this.dropSelf(ModBlocks.BLOODWOOD_SAPLING.get());

        this.add(ModBlocks.BLOODWOOD_LEAVES.get(),
                 block -> createLeavesDrops(block, ModBlocks.BLOODWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    /**
     * Creates a loot table for blocks that drop multiple items, with support for Fortune enchantment.
     *
     * @param pBlock   The block to define the loot table for.
     * @param item     The item to drop.
     * @param minDrops The minimum number of items to drop.
     * @param maxDrops The maximum number of items to drop.
     * @return A `LootTable.Builder` defining the loot behavior for the block.
     */
    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                                                 this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                                                                                          .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                                                                          .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
