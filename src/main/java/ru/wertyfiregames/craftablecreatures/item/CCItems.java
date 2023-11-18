package ru.wertyfiregames.craftablecreatures.item;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.guidebook.item.ItemGuideBook;
import ru.wertyfiregames.craftablecreatures.transmutator.item.ItemPortativeBodyChanger;

public class CCItems
{
    public static final Item bluestone = new ItemDefault("bluestone", CCCreativeTabs.tabCraftableCreatures);
    public static final Item template = new ItemDefault("template", CCCreativeTabs.tabCraftableCreatures);
    public static final Item spawn_egg_template = new ItemDefault("spawnEggTemplate", "spawn_egg_template",
            CCCreativeTabs.tabCraftableCreatures);
    public static final Item bat_wing = new ItemDefault("batWing", "bat_wing", CCCreativeTabs.tabCraftableCreatures);
    public static final Item soul_element = new ItemSoulElement();
    public static final Item guide_book = new ItemGuideBook();
    public static final Item portative_body_changer = new ItemPortativeBodyChanger();
    public static final Item bluestone_sword = new ItemBluestoneSword();
    public static final Item bluestone_pickaxe = new ItemBluestonePickaxe();
    public static final Item bluestone_axe = new ItemBluestoneAxe();
    public static final Item bluestone_spade = new ItemBluestoneSpade();
    public static final Item bluestone_hoe = new ItemBluestoneHoe();

    public static void register()
    {
        GameRegistry.registerItem(bluestone, "bluestone");
        GameRegistry.registerItem(template, "template");
        GameRegistry.registerItem(spawn_egg_template, "spawn_egg_template");
        GameRegistry.registerItem(bat_wing, "bat_wing");
        GameRegistry.registerItem(bluestone_sword, "bluestone_sword");
        GameRegistry.registerItem(bluestone_pickaxe, "bluestone_pickaxe");
        GameRegistry.registerItem(bluestone_axe, "bluestone_axe");
        GameRegistry.registerItem(bluestone_spade, "bluestone_spade");
        GameRegistry.registerItem(bluestone_hoe, "bluestone_hoe");

        if (CCConfigHandler.enableExperimentalContent)
        {
            GameRegistry.registerItem(guide_book, "guide");
            GameRegistry.registerItem(portative_body_changer, "transmutator");
        }
        //Must be in the end
        GameRegistry.registerItem(soul_element, "soul_element");
    }
}