/**
 * File created on 14:26 10.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemAxe;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestonePickaxe extends ItemAxe {
    public ItemBluestonePickaxe() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("bluestonePickaxe");
        setRegistryName("bluestone_pickaxe");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);

        CCItems.ITEMS.add(this);
    }
}