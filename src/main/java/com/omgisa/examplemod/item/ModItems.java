package com.omgisa.examplemod.item;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.block.ModBlocks;
import com.omgisa.examplemod.entity.ModEntities;
import com.omgisa.examplemod.item.custom.ChiselItem;
import com.omgisa.examplemod.item.custom.FuelItem;
import com.omgisa.examplemod.item.custom.HammerItem;
import com.omgisa.examplemod.item.custom.ModArmorItem;
import com.omgisa.examplemod.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExampleMod.MOD_ID);

    public static final DeferredItem<Item> BISMUTH = ITEMS.register("bismuth", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth",
                                                                        () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
                                                                   () -> new ChiselItem(new Item.Properties().durability(32)));

    public static final DeferredItem<Item> RADISH = ITEMS.register("radish",
                                                                   () -> new Item(new Item.Properties().food(ModFoodProperties.RADISH)) {
                                                                       @Override
                                                                       public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
                                                                           tooltipComponents.add(Component.translatable("tooltip.examplemod.radish.tooltip"));
                                                                           super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                                                                       }
                                                                   });

    public static final DeferredItem<Item> FROSTFIRE_ICE = ITEMS.register("frostfire_ice",
                                                                          () -> new FuelItem(new Item.Properties(), 800));
    public static final DeferredItem<Item> STARLIGHT_ASHES = ITEMS.register("starlight_ashes", () -> new Item(new Item.Properties()));

    public static final DeferredItem<SwordItem> BISMUTH_SWORD = ITEMS.register("bismuth_sword",
                                                                               () -> new SwordItem(ModToolTiers.BISMUTH, new Item.Properties().attributes(SwordItem.createAttributes(ModToolTiers.BISMUTH, 5, -2.4F))));
    public static final DeferredItem<PickaxeItem> BISMUTH_PICKAXE = ITEMS.register("bismuth_pickaxe",
                                                                                   () -> new PickaxeItem(ModToolTiers.BISMUTH, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.BISMUTH, 1.0F, -2.8F))));
    public static final DeferredItem<ShovelItem> BISMUTH_SHOVEL = ITEMS.register("bismuth_shovel",
                                                                                 () -> new ShovelItem(ModToolTiers.BISMUTH, new Item.Properties().attributes(ShovelItem.createAttributes(ModToolTiers.BISMUTH, 1.5F, -3.0F))));
    public static final DeferredItem<AxeItem> BISMUTH_AXE = ITEMS.register("bismuth_axe",
                                                                           () -> new AxeItem(ModToolTiers.BISMUTH, new Item.Properties().attributes(AxeItem.createAttributes(ModToolTiers.BISMUTH, 6.0F, -3.2F))));
    public static final DeferredItem<HoeItem> BISMUTH_HOE = ITEMS.register("bismuth_hoe",
                                                                           () -> new HoeItem(ModToolTiers.BISMUTH, new Item.Properties().attributes(HoeItem.createAttributes(ModToolTiers.BISMUTH, 0F, -3.0F))));
    public static final DeferredItem<HammerItem> BISMUTH_HAMMER = ITEMS.register("bismuth_hammer",
                                                                                 () -> new HammerItem(ModToolTiers.BISMUTH, new Item.Properties().attributes(PickaxeItem.createAttributes(ModToolTiers.BISMUTH, 7.0F, -3.5F))));

    public static final DeferredItem<ArmorItem> BISMUTH_HELMET = ITEMS.register("bismuth_helmet",
                                                                                () -> new ModArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(19))));
    public static final DeferredItem<ArmorItem> BISMUTH_CHESTPLATE = ITEMS.register("bismuth_chestplate",
                                                                                    () -> new ArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(26))));
    public static final DeferredItem<ArmorItem> BISMUTH_LEGGINGS = ITEMS.register("bismuth_leggings",
                                                                                  () -> new ArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(24))));
    public static final DeferredItem<ArmorItem> BISMUTH_BOOTS = ITEMS.register("bismuth_boots",
                                                                               () -> new ArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    public static final DeferredItem<Item> BISMUTH_HORSE_ARMOR =
            ITEMS.register("bismuth_horse_armor", () -> new AnimalArmorItem(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL, AnimalArmorItem.BodyType.EQUESTRIAN, false, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> KAUPEN_SMITHING_TEMPLATE =
            ITEMS.register("kaupen_armor_trim_smithing_template", () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "kaupen")));

    public static final DeferredItem<Item> KAUPEN_BOW = ITEMS.register("kaupen_bow",
                                                                       () -> new BowItem(new Item.Properties().durability(500)));

    public static final DeferredItem<Item> BAR_BRAWL_MUSIC_DISC =
            ITEMS.register("bar_brawl_music_disc", () -> new Item(new Item.Properties().jukeboxPlayable(ModSounds.BAR_BRAWL_KEY).stacksTo(1)));

    public static final DeferredItem<Item> RADISH_SEEDS =
            ITEMS.register("radish_seeds", () -> new ItemNameBlockItem(ModBlocks.RADISH_CROP.get(), new Item.Properties()));

    public static final DeferredItem<Item> GOJI_BERRIES = ITEMS.register("goji_berries",
                                                                         () -> new ItemNameBlockItem(ModBlocks.GOJI_BERRY_BUSH.get(), new Item.Properties().food(ModFoodProperties.GOJI_BERRY)));

    public static final DeferredItem<Item> GECKO_SPAWN_EGG =
            ITEMS.register("gecko_spawn_egg", () -> new DeferredSpawnEggItem(ModEntities.GECKO, 0x31AFAF, 0xFFAC00, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
