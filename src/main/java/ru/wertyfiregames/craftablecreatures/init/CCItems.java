package ru.wertyfiregames.craftablecreatures.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.ItemGuideBook;
import ru.wertyfiregames.craftablecreatures.item.*;

public class CCItems {
    public static final Item bluestone = new ItemDefault("bluestone", CCCreativeTabs.tabCraftableCreatures);
    public static final Item template = new ItemDefault("template", CCCreativeTabs.tabCraftableCreatures);
    public static final Item spawn_egg_template = new ItemDefault("spawnEggTemplate", "spawn_egg_template",
            CCCreativeTabs.tabCraftableCreatures);
    public static final Item bat_wing = new ItemDefault("batWing", "bat_wing", CCCreativeTabs.tabCraftableCreatures);
    public static final Item ocelot_tail = new ItemDefault("ocelotTail", "ocelot_tail", CCCreativeTabs.tabCraftableCreatures);
    public static final Item soul_element = new ItemSoulElement();
    public static final Item guide_book = new ItemGuideBook();

    public static void register() {
        GameRegistry.registerItem(bluestone, "bluestone");
        GameRegistry.registerItem(template, "template");
        GameRegistry.registerItem(spawn_egg_template, "spawn_egg_template");
        GameRegistry.registerItem(bat_wing, "bat_wing");
        GameRegistry.registerItem(ocelot_tail, "ocelot_tail");
        registerExperimental();
        //Must be in the end
        GameRegistry.registerItem(soul_element, "soul_element");
    }

    private static void registerExperimental() {
        if (!CCConfig.enableExperimentalContent) return;

        GameRegistry.registerItem(guide_book, "guide");
    }
}