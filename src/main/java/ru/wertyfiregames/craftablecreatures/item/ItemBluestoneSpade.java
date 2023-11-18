/**
 * File created on 17:48 16.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemSpade;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneSpade extends ItemSpade
{
    public ItemBluestoneSpade()
    {
        super(CCToolMaterials.BLUESTONE_TOOL_MATERIAL);
        setUnlocalizedName("bluestoneShovel");
        setTextureName(CraftableCreatures.getModId() + ":bluestone_shovel");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
    }
}