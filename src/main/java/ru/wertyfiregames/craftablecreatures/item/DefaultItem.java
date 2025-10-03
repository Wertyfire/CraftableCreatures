/**
 * File created on 23:05 22.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class DefaultItem extends Item {
    public DefaultItem(String name, String textureName, CreativeTabs creativeTab) {
        setUnlocalizedName(name);
        setTextureName(CraftableCreatures.getModId() + ":" + textureName);
        setCreativeTab(creativeTab);
    }
    public DefaultItem(String nameAll, CreativeTabs creativeTab) {
        this(nameAll, nameAll, creativeTab);
    }
}