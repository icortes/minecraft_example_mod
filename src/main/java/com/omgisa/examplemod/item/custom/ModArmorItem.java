package com.omgisa.examplemod.item.custom;

import com.google.common.collect.ImmutableMap;
import com.omgisa.examplemod.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {
    /**
     * A mapping of armor materials to their associated effects when a full suit is worn.
     * This map contains the material and the list of effects that should be applied to the player.
     */
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(ModArmorMaterials.BISMUTH_ARMOR_MATERIAL,
                         List.of(new MobEffectInstance(MobEffects.JUMP, 200, 1, false, false),
                                 new MobEffectInstance(MobEffects.GLOWING, 200, 1, false, false)))
                    .build();
    
    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    /**
     * Called every tick the item is in the player's inventory.
     * This method checks if the player is wearing a full suit of armor and applies effects accordingly.
     *
     * @param stack      The ItemStack being ticked.
     * @param level      The level in which the entity resides.
     * @param entity     The entity holding the item.
     * @param slotId     The slot ID of the item in the inventory.
     * @param isSelected Whether the item is currently selected in the player's hotbar.
     */
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof Player player && !level.isClientSide() && hasFullSuitOfArmorOn(player)) {
            evaluateArmorEffects(player);
        }
    }

    /**
     * Evaluates the armor effects based on the player's equipped armor.
     * If the player is wearing a full suit of armor made from a material that has associated effects,
     * those effects are applied to the player.
     *
     * @param player The player whose armor effects are being evaluated.
     */
    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapEffect = entry.getValue();

            if (hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    /**
     * Adds the specified effects to the player if they do not already have them.
     *
     * @param player    The player to whom the effects will be added.
     * @param mapEffect The list of effects to be added.
     */
    private void addEffectToPlayer(Player player, List<MobEffectInstance> mapEffect) {
        boolean hasPlayerEffect = mapEffect.stream().allMatch(effect -> player.hasEffect(effect.getEffect()));

        if (!hasPlayerEffect) {
            for (MobEffectInstance effect : mapEffect) {
                player.addEffect(new MobEffectInstance(effect.getEffect(),
                                                       effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
            }
        }
    }

    /**
     * Checks if the player is wearing a full suit of armor made from the specified material.
     *
     * @param mapArmorMaterial The armor material to check against.
     * @param player           The player whose armor is being checked.
     * @return true if the player is wearing a full suit of armor made from the specified material, false otherwise.
     */
    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> mapArmorMaterial, Player player) {
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem boots = ((ArmorItem) player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem) player.getInventory().getArmor(1).getItem());
        ArmorItem chestplate = ((ArmorItem) player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem) player.getInventory().getArmor(3).getItem());

        return boots.getMaterial() == mapArmorMaterial && leggings.getMaterial() == mapArmorMaterial
                && chestplate.getMaterial() == mapArmorMaterial && helmet.getMaterial() == mapArmorMaterial;
    }

    /**
     * Checks if the player is wearing a full suit of armor (boots, leggings, chestplate, and helmet).
     *
     * @param player The player whose armor is being checked.
     * @return true if the player has a full suit of armor on, false otherwise.
     */
    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }
}
