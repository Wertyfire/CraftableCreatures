/**
 * File created on 15:55 18.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSoulExtractorOutput extends Slot {
    private final EntityPlayer player;
    private int removeCount;

    public SlotSoulExtractorOutput(EntityPlayer player, IInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
        this.player = player;
    }

    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
        super.onCrafting(stack);
        super.onTake(thePlayer, stack);
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        if (this.getHasStack()) this.removeCount += Math.min(amount, this.getStack().getCount());
        return super.decrStackSize(amount);
    }
}