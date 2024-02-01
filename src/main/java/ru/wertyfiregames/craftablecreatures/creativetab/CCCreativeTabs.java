package ru.wertyfiregames.craftablecreatures.creativetab;

import ru.wertyfiregames.craftablecreatures.item.CCItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CCCreativeTabs extends CreativeTabs
{
    public static final CCCreativeTabs tabCraftableCreatures = new CCCreativeTabs("craftableCreatures");

    private CCCreativeTabs(String tabId)
    {
        super(tabId);
    }

    @Override
    public Item getTabIconItem()
    {
        return CCItems.spawn_egg_template;
    }
}