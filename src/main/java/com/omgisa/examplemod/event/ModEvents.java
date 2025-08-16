package com.omgisa.examplemod.event;

import com.omgisa.examplemod.ExampleMod;
import com.omgisa.examplemod.item.ModItems;
import com.omgisa.examplemod.item.custom.HammerItem;
import com.omgisa.examplemod.potion.ModPotions;
import com.omgisa.examplemod.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles mod-specific events, such as custom block-breaking behavior.
 * This class listens to events on the mod's event bus.
 */
@EventBusSubscriber(modid = ExampleMod.MOD_ID)
public class ModEvents {
    // Tracks blocks that have already been harvested to prevent duplicate processing.
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    /**
     * Handles the block-breaking event when a hammer is used.
     * Allows the hammer to break multiple blocks in an area, provided the tool is appropriate.
     *
     * @param event The block-breaking event triggered in the game.
     */
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        // Retrieves the player who triggered the event and their main hand item.
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        // Checks if the item in the player's hand is a HammerItem and if the player is a server player.
        if (mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();

            // Prevents processing the same block multiple times.
            if (HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            // Iterates through the blocks to be destroyed by the hammer's area effect.
            for (BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                // Skips the initial block or blocks that cannot be harvested with the hammer.
                if (pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                // Adds the block to the harvested set, destroys it, and then removes it from the set.
                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    /**
     * This event is triggered when a player uses an end rod on a sheep.
     * It applies a poison effect to the sheep and sends a message to the player.
     *
     * @param event The LivingDamageEvent that contains the entity being damaged and the source of the damage.
     */
    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " is using an end rod on a sheep!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 6));
                player.getMainHandItem().shrink(1);
            }
        }
    }

    /**
     * Registers custom brewing recipes for potions.
     *
     * @param event The event that contains the brewing recipe builder.
     */
    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((entity, randomSource) ->
                                      new MerchantOffer(new ItemCost(Items.EMERALD, 3),
                                                        new ItemStack(ModItems.GOJI_BERRIES.get(), 18), 6, 3, 0.05f));

            trades.get(1).add((entity, randomSource) ->
                                      new MerchantOffer(new ItemCost(Items.DIAMOND, 12),
                                                        new ItemStack(ModItems.RADISH.get(), 1), 6, 3, 0.05f));

            trades.get(2).add((entity, randomSource) ->
                                      new MerchantOffer(new ItemCost(Items.ENDER_PEARL, 1),
                                                        new ItemStack(ModItems.RADISH_SEEDS.get(), 1), 2, 8, 0.05f));
        }

        if (event.getType() == ModVillagers.KAUPENGER.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((entity, randomSource) ->
                                      new MerchantOffer(new ItemCost(Items.EMERALD, 2),
                                                        new ItemStack(ModItems.RAW_BISMUTH.get(), 18), 6, 3, 0.05f));

            trades.get(1).add((entity, randomSource) ->
                                      new MerchantOffer(new ItemCost(Items.DIAMOND, 16),
                                                        new ItemStack(ModItems.RADIATION_STAFF.get(), 1), 6, 3, 0.05f));

            trades.get(2).add((entity, randomSource) ->
                                      new MerchantOffer(new ItemCost(Items.ENDER_PEARL, 2),
                                                        new ItemStack(ModItems.BISMUTH_SWORD.get(), 1), 2, 8, 0.05f));
        }
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((entity, randomSource) ->
                                  new MerchantOffer(new ItemCost(Items.EMERALD, 16),
                                                    new ItemStack(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), 1), 1, 10, 0.2f));

        rareTrades.add((entity, randomSource) ->
                               new MerchantOffer(new ItemCost(Items.NETHERITE_INGOT, 1),
                                                 new ItemStack(ModItems.BAR_BRAWL_MUSIC_DISC.get(), 1), 1, 10, 0.2f));
    }

}
