/**
 * File created on 14:22 14.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DefaultItem extends Item {
    public DefaultItem(String name, CreativeTabs creativeTab) {
        setUnlocalizedName(name);
        setCreativeTab(creativeTab);
    }
}