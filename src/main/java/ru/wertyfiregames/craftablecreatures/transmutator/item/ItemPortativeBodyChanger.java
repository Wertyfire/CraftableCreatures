package ru.wertyfiregames.craftablecreatures.transmutator.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.ItemDefault;

import java.util.List;

public class ItemPortativeBodyChanger extends ItemDefault {

    public ItemPortativeBodyChanger() {
        super("portativeBodyChanger", null, CCCreativeTabs.tabCraftableCreatures);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean boolean1) {
        super.addInformation(stack, player, tooltip, boolean1);
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            {
                tooltip.add(I18n.format("item.portativeBodyChanger.tooltip.1"));
                tooltip.add(I18n.format("item.portativeBodyChanger.tooltip.2"));
                tooltip.add(I18n.format("item.portativeBodyChanger.tooltip.3"));
            } else
            {
                tooltip.add(I18n.format("tooltip.lshift.press"));
            }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 8;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote)
        {
            if (!player.isSneaking())
            {
                player.openGui(CraftableCreatures.instance, CraftableCreatures.GUI_TRANSMUTATOR, world, 0, 0, 0);
            }
        }

        return stack;
    }
}