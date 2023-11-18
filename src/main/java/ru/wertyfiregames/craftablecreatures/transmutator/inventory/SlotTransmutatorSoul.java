/**
 * File created on 20:38 08.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.transmutator.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.item.ItemSoulElement;

public class SlotTransmutatorSoul extends Slot
{
    public SlotTransmutatorSoul(IInventory inv, int index, int xPos, int yPos)
    {
        super(inv, index, xPos, yPos);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return !(stack.getItem() instanceof ItemSoulElement);
    }
}