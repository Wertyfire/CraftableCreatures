package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemPickaxe;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestonePickaxe extends ItemPickaxe
{
    public ItemBluestonePickaxe()
    {
        super(CCToolMaterials.BLUESTONE_TOOL_MATERIAL);
        setUnlocalizedName("bluestonePickaxe");
        setTextureName(CraftableCreatures.getModId() + ":bluestone_pickaxe");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
    }
}