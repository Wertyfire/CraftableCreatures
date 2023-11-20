package ru.wertyfiregames.craftablecreatures.common.listener;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import ru.wertyfiregames.craftablecreatures.stats.CCAchievementList;

public class CCFMLEventListener {
    @SubscribeEvent
    public void onPlayerDied(PlayerEvent.PlayerRespawnEvent event)
    {
        event.player.dropItem(CCItems.soul_element, 1);
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        event.player.triggerAchievement(CCAchievementList.thanksForDownload);
        event.player.addChatMessage(new ChatComponentText(I18n.format("chat.craftable_creations.mod_info") + CCConfigHandler.enableExperimentalContent));
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent event)
    {
        if (event.player != null) {
            if (event.crafting.getItem() == CCItems.bluestone) {
                event.player.triggerAchievement(CCAchievementList.bluedustCollector);
            }
            if (event.crafting.getItem() == CCItems.template) {
                event.player.triggerAchievement(CCAchievementList.templateManager);
            }
            if (event.crafting.getItem() == CCItems.spawn_egg_template) {
                event.player.triggerAchievement(CCAchievementList.mobSpawner);
            }
            if (event.crafting.getItem() == Item.getItemFromBlock(CCBlocks.powered_bluestone_block)) {
                event.player.triggerAchievement(CCAchievementList.energy);
            }
        }
    }
}