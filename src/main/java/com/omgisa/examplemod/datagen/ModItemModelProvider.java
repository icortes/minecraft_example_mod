package com.omgisa.examplemod.datagen;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.block.ModBlocks;
import com.omgisa.examplemod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();

    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExampleMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.BISMUTH.get());
        basicItem(ModItems.RAW_BISMUTH.get());

        basicItem(ModItems.RADISH.get());
        basicItem(ModItems.FROSTFIRE_ICE.get());
        basicItem(ModItems.STARLIGHT_ASHES.get());
        chiselItem(ModItems.CHISEL);

        buttonItem(ModBlocks.BISMUTH_BUTTON, ModBlocks.BISMUTH_BLOCK);
        fenceItem(ModBlocks.BISMUTH_FENCE, ModBlocks.BISMUTH_BLOCK);
        wallItem(ModBlocks.BISMUTH_WALL, ModBlocks.BISMUTH_BLOCK);

        basicItem(ModBlocks.BISMUTH_DOOR.asItem());

        handheldItem(ModItems.BISMUTH_SWORD.get());
        handheldItem(ModItems.BISMUTH_PICKAXE.get());
        handheldItem(ModItems.BISMUTH_SHOVEL.get());
        handheldItem(ModItems.BISMUTH_AXE.get());
        handheldItem(ModItems.BISMUTH_HOE.get());
        handheldItem(ModItems.BISMUTH_HAMMER.get());

        trimmedArmorItem(ModItems.BISMUTH_HELMET);
        trimmedArmorItem(ModItems.BISMUTH_CHESTPLATE);
        trimmedArmorItem(ModItems.BISMUTH_LEGGINGS);
        trimmedArmorItem(ModItems.BISMUTH_BOOTS);

        basicItem(ModItems.BISMUTH_HORSE_ARMOR.get());
        basicItem(ModItems.KAUPEN_SMITHING_TEMPLATE.get());
        basicItem(ModItems.BAR_BRAWL_MUSIC_DISC.get());

        basicItem(ModItems.RADISH_SEEDS.get());
        basicItem(ModItems.GOJI_BERRIES.get());

        saplingItem(ModBlocks.BLOODWOOD_SAPLING);

        withExistingParent(ModItems.GECKO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder saplingItem(DeferredBlock<Block> item) {
        return withExistingParent(item.getId().getPath(), ResourceLocation.parse("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "block/" + item.getId().getPath()));
    }

    /**
     * Generates item models for trimmed armor items.
     * Supports multiple trim materials and their associated textures.
     *
     * @param itemDeferredItem The deferred item representing the armor item.
     */
    private void trimmedArmorItem(DeferredItem<ArmorItem> itemDeferredItem) {
        final String MOD_ID = ExampleMod.MOD_ID;

        if (itemDeferredItem.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemDeferredItem.getId().getPath(),
                                        mcLoc("item/generated"))
                    .override()
                    .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                    .predicate(mcLoc("trim_type"), trimValue).end()
                    .texture("layer0",
                             ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                                                   "item/" + itemDeferredItem.getId().getPath()));
            });
        }
    }

    private void chiselItem(DeferredItem<Item> item) {
        // Chisel Item
        this.withExistingParent(item.getId().getPath(), mcLoc("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "item/chisel"))
            .override()
            .model(new ModelFile.UncheckedModelFile(modLoc("item/chisel_used")))
            .predicate(modLoc("used"), 1F)
            .end();

        // Chisel Used
        this.withExistingParent("chisel_used", mcLoc("item/generated"))
            .texture("layer0", ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "item/chisel_used"));
    }

    /**
     * Generates an item model for a button block.
     *
     * @param block     The button block to generate the model for.
     * @param baseBlock The base block used for the texture.
     */
    public void buttonItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/button_inventory"))
            .texture("texture", ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID,
                                                                      "block/" + baseBlock.getId().getPath()));
    }

    /**
     * Generates an item model for a fence block.
     *
     * @param block     The fence block to generate the model for.
     * @param baseBlock The base block used for the texture.
     */
    public void fenceItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/fence_inventory"))
            .texture("texture", ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID,
                                                                      "block/" + baseBlock.getId().getPath()));
    }

    /**
     * Generates an item model for a wall block.
     *
     * @param block     The wall block to generate the model for.
     * @param baseBlock The base block used for the texture.
     */
    public void wallItem(DeferredBlock<?> block, DeferredBlock<Block> baseBlock) {
        this.withExistingParent(block.getId().getPath(), mcLoc("block/wall_inventory"))
            .texture("wall", ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID,
                                                                   "block/" + baseBlock.getId().getPath()));
    }
}
