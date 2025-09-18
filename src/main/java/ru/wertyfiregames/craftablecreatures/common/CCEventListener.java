package ru.wertyfiregames.craftablecreatures.common;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.init.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.util.ChatUtils;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker.UpdateResult;

public class CCEventListener {
    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            event.drops.add(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ,
                    new ItemStack(CCItems.soul, 1, 0)));
        }

        if (event.entityLiving instanceof EntityBat) {
            EntityBat bat = (EntityBat) event.entityLiving;

            event.drops.add(new EntityItem(bat.worldObj, bat.posX, bat.posY, bat.posZ,
                    new ItemStack(CCItems.bat_wing)));
        }

        if (event.entityLiving instanceof EntityOcelot) {
            EntityOcelot ocelot = (EntityOcelot) event.entityLiving;

            event.drops.add(new EntityItem(ocelot.worldObj, ocelot.posX, ocelot.posY, ocelot.posZ,
                    new ItemStack(CCItems.ocelot_tail)));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.triggerAchievement(CCAchievementList.thanksForDownload);

        if (CCConfig.enableExperimentalContent) {
            event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.modInfo", true));
        }

        if (CCConfig.checkForUpdates) {
            MinecraftServer server = MinecraftServer.getServer();
            if (server != null && !server.isSinglePlayer()) return;
            String homepage = CCVersionChecker.getHomepageUrl();
            if (CCVersionChecker.getStatus() == UpdateResult.FAILED) {
                event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.failedToCheckUpdates"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.UP_TO_DATE) {
                event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.latest"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.OUTDATED) {
                event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.outdated"));
                ChatUtils.sendMessageWithLink(event.player, "craftableCreatures.chat.getUpdate", homepage, homepage, "");
                ChatUtils.sendUpdateChangelogMessage(event.player, CCVersionChecker.getChangelog());
            }
            if (CCVersionChecker.getStatus() == UpdateResult.AHEAD) {
                event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.ahead"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA) {
                event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.beta"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA_OUTDATED) {
                event.player.addChatMessage(new ChatComponentTranslation("craftableCreatures.chat.betaOutdated"));
                ChatUtils.sendMessageWithLink(event.player, "craftableCreatures.chat.getUpdate", homepage, homepage, "");
                ChatUtils.sendUpdateChangelogMessage(event.player, CCVersionChecker.getChangelog());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player != null) {
            for (ItemStack stack : event.player.inventory.mainInventory) {
                if (stack != null) {
                    if (stack.getItem() == CCItems.bluestone) {
                        event.player.triggerAchievement(CCAchievementList.bluedustCollector);
                    }
                    if (stack.getItem() == CCItems.blueprint) {
                        event.player.triggerAchievement(CCAchievementList.templateManager);
                    }
                    if (stack.getItem() == CCItems.spawn_egg_blueprint) {
                        event.player.triggerAchievement(CCAchievementList.mobSpawner);
                    }
                    if (stack.getItem() == CCItems.transmutator) {
                        event.player.triggerAchievement(CCAchievementList.transmutator);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.powered_bluestone_block)) {
                        event.player.triggerAchievement(CCAchievementList.energy);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.soul_extractor)) {
                        event.player.triggerAchievement(CCAchievementList.soulExtractor);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.combiner)) {
                        event.player.triggerAchievement(CCAchievementList.combiner);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(CraftableCreatures.getModId()))
            CCConfig.load();
    }
}