package ru.wertyfiregames.craftablecreatures.guidebook.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.ItemDefault;

public class ItemGuideBook extends ItemDefault
{
    public ItemGuideBook()
    {
        super("guideBook", "guide_book", CCCreativeTabs.tabCraftableCreatures);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return super.onItemRightClick(stack, world, player);
    }
}