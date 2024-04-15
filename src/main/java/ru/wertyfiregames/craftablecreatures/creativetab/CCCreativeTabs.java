/**
 * File created on 22:19 07.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

public class CCCreativeTabs {
    public static final CreativeTabs tabCraftableCreatures = new CreativeTabs("craftableCreatures") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(CCItems.SPAWN_EGG_BLUEPRINT);
        }
    };
}