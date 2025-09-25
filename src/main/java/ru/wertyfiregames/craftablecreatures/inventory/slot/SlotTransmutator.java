/**
 * File created on 16:15 22.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.api.ISoulElement;

public class SlotTransmutator extends Slot {
    public SlotTransmutator(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ISoulElement;
    }
}