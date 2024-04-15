package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.item.ItemSword;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemBluestoneSword extends ItemSword {
    public ItemBluestoneSword() {
        super(ToolMaterial.IRON);
        setUnlocalizedName("bluestoneSword");
        setRegistryName("bluestone_sword");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);

        CCItems.ITEMS.add(this);
    }
}