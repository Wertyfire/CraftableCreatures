/**
 * File created on 18:07 16.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemAxe;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneAxe extends ItemAxe
{
    public ItemBluestoneAxe()
    {
        super(CCToolMaterials.BLUESTONE_TOOL_MATERIAL);
        setUnlocalizedName("bluestoneAxe");
        setTextureName(CraftableCreatures.getModId() + ":bluestone_axe");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
    }
}