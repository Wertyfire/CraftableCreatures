/**
 * File created on 18:54 09.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.wertyfirecore.item.DefaultItem;

import java.util.List;

public class ItemTransmutator extends DefaultItem {
    public ItemTransmutator() {
        super("transmutator", CCCreativeTabs.tabCraftableCreatures);
        setMaxStackSize(1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean flag) {
        tooltip.add(I18n.format("tooltip.transmutator"));
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_TRANSMUTATOR, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }
}