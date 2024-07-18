package ru.wertyfiregames.craftablecreatures.creativetab;

import ru.wertyfiregames.craftablecreatures.item.CCItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CCCreativeTabs
{
    public static final CreativeTabs tabCraftableCreatures = new CreativeTabs("craftableCreatures") {
        @Override
        public Item getTabIconItem() {
            return CCItems.spawn_egg_template;
        }
    };
}