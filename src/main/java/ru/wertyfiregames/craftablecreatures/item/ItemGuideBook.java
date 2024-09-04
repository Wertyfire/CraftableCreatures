package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemGuideBook extends ItemDefault {
    public ItemGuideBook() {
        super("guideBook", "guide_book", CCCreativeTabs.tabCraftableCreatures);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_GUIDE_BOOK, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }
}