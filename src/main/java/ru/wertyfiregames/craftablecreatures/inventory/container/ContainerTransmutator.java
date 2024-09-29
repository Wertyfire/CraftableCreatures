/**
 * File created on 14:12 22.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.inventory.InventoryTransmutator;
import ru.wertyfiregames.craftablecreatures.inventory.slot.SlotTransmutator;

public class ContainerTransmutator extends Container {
    private final InventoryTransmutator inventoryTransmutator;

    public ContainerTransmutator(InventoryPlayer inv, InventoryTransmutator transmutator) {
        inventoryTransmutator = transmutator;

        for (int i = 0; i < 8; i++) {
            addSlotToContainer(new SlotTransmutator(transmutator, i, 8 + i * 18, 48));
        }
        addSlotToContainer(new SlotTransmutator(transmutator, 9, 152, 21));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return inventoryTransmutator.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (fromSlot == 8) {
                if (!mergeItemStack(slotStack, 0, 8, false)) return null;
                if (!mergeItemStack(slotStack, 8 + 1, 8 + 36 + 1, true)) return null;
                slot.onSlotChange(slotStack, itemStack);
            } else if (fromSlot > 8) {
                if (fromSlot < 8 + 28) {
                    if (!mergeItemStack(slotStack, 8 + 28, 8 + 37, false)) return null;
                } else if (fromSlot < 8 + 37 + 1 && !mergeItemStack(slotStack, 8 + 1, 8 + 28, false)) return null;
            } else if (!mergeItemStack(slotStack, 8 + 1, 8 + 37, false)) return null;

            if (slotStack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (slotStack.stackSize == itemStack.stackSize) return null;

            slot.onPickupFromSlot(player, slotStack);
        }
        return itemStack;
    }

    public ItemStack slotClick(int slot, int mouseButton, int flag, EntityPlayer player) {
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) return null;
        return super.slotClick(slot, mouseButton, flag, player);
    }
}