package ru.wertyfiregames.craftablecreatures.common;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.stats.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.util.Utils;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker.UpdateResult;

public class CCEventListener {
    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            event.drops.add(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ,
                    new ItemStack(CCItems.soul_element, 1, 0)));
        }

        if (event.entityLiving instanceof EntityBat) {
            EntityBat bat = (EntityBat) event.entityLiving;

            event.drops.add(new EntityItem(bat.worldObj, bat.posX, bat.posY, bat.posZ,
                    new ItemStack(CCItems.bat_wing)));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.triggerAchievement(CCAchievementList.thanksForDownload);
        event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.modInfo") + " " + CCConfig.enableExperimentalContent));

        if (CCConfig.checkForUpdates) {
            String homepage = CCVersionChecker.getHomepageUrl();
            if (CCVersionChecker.getStatus() == UpdateResult.FAILED) {
                event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.failedToCheckUpdates")));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.UP_TO_DATE) {
                event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.latest")));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.OUTDATED) {
                event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.outdated")));
                Utils.sendClickableLink(event.player, "chat.craftableCreatures.getUpdate", homepage, homepage, "");
                for (String change : CCVersionChecker.getChangelog().split("<n>")) {
                    if (change.contains("<t>"))
                        change = change.replace("<t>", "    ");
                    event.player.addChatMessage(new ChatComponentText(change));
                }
            }
            if (CCVersionChecker.getStatus() == UpdateResult.AHEAD) {
                event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.ahead")));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA) {
                event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.beta")));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA_OUTDATED) {
                event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftableCreatures.betaOutdated")));
                Utils.sendClickableLink(event.player, "chat.craftableCreatures.getUpdate", homepage, homepage, "");
                for (String change : CCVersionChecker.getChangelog().split("<n>")) {
                    if (change.contains("<t>"))
                        change = change.replace("<t>", "    ");
                    event.player.addChatMessage(new ChatComponentText(change));
                }
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
                    if (stack.getItem() == CCItems.template) {
                        event.player.triggerAchievement(CCAchievementList.templateManager);
                    }
                    if (stack.getItem() == CCItems.spawn_egg_template) {
                        event.player.triggerAchievement(CCAchievementList.mobSpawner);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.powered_bluestone_block)) {
                        event.player.triggerAchievement(CCAchievementList.energy);
                    }
                }
            }
        }
    }
}