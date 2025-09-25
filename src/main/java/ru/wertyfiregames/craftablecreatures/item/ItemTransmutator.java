/**
 * File created on 18:54 09.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

import java.util.List;

public class ItemTransmutator extends DefaultItem {
    public ItemTransmutator() {
        super("transmutator", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add(I18n.format("tooltip.transmutator1"));
            tooltip.add(I18n.format("tooltip.transmutator2"));
        }
        else {
            tooltip.add(I18n.format("tooltip.pressLshift1"));
            tooltip.add(I18n.format("tooltip.pressLshift2"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_TRANSMUTATOR, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}