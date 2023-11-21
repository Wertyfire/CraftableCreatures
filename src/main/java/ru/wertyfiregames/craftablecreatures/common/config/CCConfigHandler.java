package ru.wertyfiregames.craftablecreatures.common.config;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Property;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CCConfigHandler
{
    public static Configuration config;

    public static boolean checkForUpdates = false;
    public static boolean enableExperimentalContent = false;

    public static void register(String configDir)
    {
        if (config == null)
        {
            File path = new File(configDir + "/craftableCreatures.cfg");
            config = new Configuration(path);
            loadConfig();
        }
    }

    public static void loadConfig()
    {
        List<String> propOrder = new ArrayList<>();

        Property prop;

        prop = config.get(Configuration.CATEGORY_GENERAL, "checkForUpdates", false,
                "Allow this mod to check for updates (needs access to internet)");
        prop.setLanguageKey("craftableCreatures.configGui.checkForUpdates");
        checkForUpdates = prop.getBoolean(checkForUpdates);
        propOrder.add(prop.getName());

        prop = config.get(Configuration.CATEGORY_GENERAL, "enableExperimentalContent", false,
                "Enable blocks, items, recipes, etc. that are in development");
		prop.setLanguageKey("craftableCreatures.configGui.enableExperimentalContent");
		enableExperimentalContent = prop.getBoolean(enableExperimentalContent);
        prop.setRequiresMcRestart(true);
		propOrder.add(prop.getName());

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(CraftableCreatures.getModId())) {
            loadConfig();
        }
    }

    public static Configuration getConfig()
    {
        return config;
    }
}