/**
 * File created on 20:00 04.02.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.common.listener;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.util.config.CCConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class CCEventListener {
    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.sendMessage(new TextComponentTranslation("chat.craftableCreatures.modInfo"));

        if (CCConfig.checkForUpdates) {
            try {
                URL link = new URL("https://modrinth.com/mod/craftable_creatures");
            }
            catch (MalformedURLException e) {
                CraftableCreatures.getModLogger().error("Failed to create update URL");
            }
        }
    }
}