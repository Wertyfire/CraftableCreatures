package ru.wertyfiregames.craftablecreatures.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.*;
import ru.wertyfiregames.wertyfirecore.item.DefaultItem;

public class CCItems {
    public static final Item bluestone = new DefaultItem("bluestone", CCCreativeTabs.tabCraftableCreatures);
    public static final Item template = new DefaultItem("template", CCCreativeTabs.tabCraftableCreatures);
    public static final Item spawn_egg_template = new DefaultItem("spawnEggTemplate", "spawn_egg_template",
            CCCreativeTabs.tabCraftableCreatures);
    public static final Item bat_wing = new DefaultItem("batWing", "bat_wing", CCCreativeTabs.tabCraftableCreatures);
    public static final Item ocelot_tail = new DefaultItem("ocelotTail", "ocelot_tail", CCCreativeTabs.tabCraftableCreatures);
    public static final Item soul_element = new ItemSoulElement();
    public static final Item guide_book = new ItemGuideBook();
    public static final Item transmutator = new ItemTransmutator();

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
        GameRegistry.registerItem(transmutator, "transmutator");
    }
}