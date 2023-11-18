package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemSword;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneSword extends ItemSword
{
    public ItemBluestoneSword()
    {
        super(CCToolMaterials.BLUESTONE_TOOL_MATERIAL);
        setUnlocalizedName("bluestoneSword");
        setTextureName(CraftableCreatures.getModId() + ":bluestone_sword");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
    }
}