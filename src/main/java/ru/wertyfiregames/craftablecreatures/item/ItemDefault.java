/**
 * File created on 22:29 07.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDefault extends Item {
    public ItemDefault(String name, String textureName, CreativeTabs creativeTab) {
        this.setRegistryName(textureName);
        this.setUnlocalizedName(name);
        this.setCreativeTab(creativeTab);

        CCItems.ITEMS.add(this);
    }
    public ItemDefault(String name, String textureName, boolean addToITEMS, CreativeTabs creativeTab) {
        this.setRegistryName(textureName);
        this.setUnlocalizedName(name);
        this.setCreativeTab(creativeTab);

        if (addToITEMS)
            CCItems.ITEMS.add(this);
    }
    public ItemDefault(String nameAll, CreativeTabs creativeTab) {
        this.setRegistryName(nameAll);
        this.setUnlocalizedName(nameAll);
        this.setCreativeTab(creativeTab);

        CCItems.ITEMS.add(this);
    }
}