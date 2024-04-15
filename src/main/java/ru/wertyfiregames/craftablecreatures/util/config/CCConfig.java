/**
 * File created on 18:43 15.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

@Config(modid = CraftableCreatures.modId, name = "craftableCreatures")
public class CCConfig {
    @Config.LangKey("craftableCreatures.configGui.checkForUpdates")
    @Config.Comment("Allow this mod to check for updates (needs access to internet)")
    public static boolean checkForUpdates = false;

    @Config.LangKey("craftableCreatures.configGui.enableExperimentalContent")
    @Config.Comment("Enable blocks, items, etc. that are in development")
    @Config.RequiresMcRestart()
    public static boolean enableExperimentalContent = false;

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(CraftableCreatures.getModId())) {
            CraftableCreatures.getModLogger().debug("[CC Config] Config changed. Saving...");
            ConfigManager.sync(CraftableCreatures.getModId(), Config.Type.INSTANCE);
            CraftableCreatures.getModLogger().debug("[CC Config] Saved");
        }
    }
}