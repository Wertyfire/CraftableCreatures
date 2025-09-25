package ru.wertyfiregames.craftablecreatures.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

public class CCCreativeTabs {
    public static final CreativeTabs TAB_CRAFTABLE_CREATURES = new CreativeTabs("craftableCreatures") {
        @Override
        public Item getTabIconItem() {
            return CCItems.SPAWN_EGG_BLUEPRINT;
        }
    };
}