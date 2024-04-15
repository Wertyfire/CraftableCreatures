/**
 * File created on 14:26 10.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemAxe;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneAxe extends ItemAxe {
    public ItemBluestoneAxe() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("bluestoneAxe");
        setRegistryName("bluestone_axe");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);

        CCItems.ITEMS.add(this);
    }
}