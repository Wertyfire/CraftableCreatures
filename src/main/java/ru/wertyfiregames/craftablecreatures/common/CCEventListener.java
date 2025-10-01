package ru.wertyfiregames.craftablecreatures.common;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
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
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            event.getDrops().add(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ,
                    new ItemStack(CCItems.SOUL, 1, 0)));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addStat(CCAchievementList.thanksForDownload);

        if (CCConfig.enableExperimentalContent) {
            event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.modInfo", true));
        }

        if (CCConfig.checkForUpdates) {
            MinecraftServer server = event.player.getServer();
            if (server != null && !server.isSinglePlayer()) return;
            String homepage = CCVersionChecker.getHomepageUrl();
            if (CCVersionChecker.getStatus() == UpdateResult.FAILED) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.failedToCheckUpdates"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.UP_TO_DATE) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.latest"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.OUTDATED) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.outdated"));
                ChatUtils.sendMessageWithLink(event.player, "craftableCreatures.chat.getUpdate", homepage, homepage, "");
                ChatUtils.sendUpdateChangelogMessage(event.player, CCVersionChecker.getChangelog());
            }
            if (CCVersionChecker.getStatus() == UpdateResult.AHEAD) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.ahead"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.beta"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA_OUTDATED) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.betaOutdated"));
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
                    if (stack.getItem() == CCItems.BLUESTONE) {
                        event.player.addStat(CCAchievementList.bluedustCollector);
                    }
                    if (stack.getItem() == CCItems.BLUEPRINT) {
                        event.player.addStat(CCAchievementList.templateManager);
                    }
                    if (stack.getItem() == CCItems.SPAWN_EGG_BLUEPRINT) {
                        event.player.addStat(CCAchievementList.mobSpawner);
                    }
                    if (stack.getItem() == CCItems.TRANSMUTATOR) {
                        event.player.addStat(CCAchievementList.transmutator);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.POWERED_BLUESTONE_BLOCK)) {
                        event.player.addStat(CCAchievementList.energy);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.SOUL_EXTRACTOR)) {
                        event.player.addStat(CCAchievementList.soulExtractor);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.COMBINER)) {
                        event.player.addStat(CCAchievementList.combiner);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(CraftableCreatures.getModId())) {
            CCConfig.load();
            if (CCConfig.checkForUpdates && CCVersionChecker.getStatus() == UpdateResult.PENDING)
                CCVersionChecker.check(CraftableCreatures.getVersion());
        }
    }
}