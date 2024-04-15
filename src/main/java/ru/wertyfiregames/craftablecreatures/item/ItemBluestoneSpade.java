/**
 * File created on 14:36 10.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemSpade;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneSpade extends ItemSpade {
    public ItemBluestoneSpade() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("bluestoneShovel");
        setRegistryName("bluestone_shovel");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);

        CCItems.ITEMS.add(this);
    }
}