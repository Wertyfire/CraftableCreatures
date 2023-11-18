/**
 * File created on 17:57 16.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemHoe;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneHoe extends ItemHoe
{
    public ItemBluestoneHoe()
    {
        super(CCToolMaterials.BLUESTONE_TOOL_MATERIAL);
        setUnlocalizedName("bluestoneHoe");
        setTextureName(CraftableCreatures.getModId() + ":bluestone_hoe");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
    }
}