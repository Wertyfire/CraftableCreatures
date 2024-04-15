/**
 * File created on 14:26 10.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemAxe;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneHoe extends ItemAxe {
    public ItemBluestoneHoe() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("bluestoneHoe");
        setRegistryName("bluestone_hoe");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);

        CCItems.ITEMS.add(this);
    }
}