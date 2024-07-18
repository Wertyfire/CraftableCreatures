package ru.wertyfiregames.craftablecreatures.config;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Property;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class CCConfig {
    public static final String CATEGORY_CRAFTABLE_CREATURES = "craftable_creatures";

    public static boolean checkForUpdates;
    public static boolean enableExperimentalContent;

    public static void load() {
        checkForUpdates = getBoolean(CATEGORY_CRAFTABLE_CREATURES, "checkForUpdates", "craftableCreatures.configGui.checkForUpdates",
                "Allow this mod to check for updates (needs access to internet)", false, false);

        enableExperimentalContent = getBoolean(CATEGORY_CRAFTABLE_CREATURES, "enableExperimentalContent", "craftableCreatures.configGui.enableExperimentalContent",
                "Enable blocks, items, recipes, etc. that are in development", false, true);

        if (CraftableCreatures.getConfig().hasChanged())
            CraftableCreatures.getConfig().save();
    }

    private static boolean getBoolean(@SuppressWarnings("SameParameterValue") String category, String name, String languageKey, String comment,
                                      @SuppressWarnings("SameParameterValue") boolean def, boolean requiresMcRestart) {
        Property prop = CraftableCreatures.getConfig().get(category, name, def);
        prop.setLanguageKey(languageKey);
        prop.comment = comment;
        prop.setRequiresMcRestart(requiresMcRestart);
        return prop.getBoolean();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(CraftableCreatures.getModId())) {
            load();
        }
    }
}