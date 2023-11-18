/**
 * File created on 16:58 08.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.transmutator.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.item.ItemSoulElement;
import ru.wertyfiregames.craftablecreatures.transmutator.item.ItemTransmutatorInv;

public class ContainerTransmutator extends Container
{
    public final ItemTransmutatorInv inventory;

    private static final int HOTBAR_START = ItemTransmutatorInv.INV_SIZE, HOTBAR_END = HOTBAR_START + 8;

    public ContainerTransmutator(EntityPlayer player, InventoryPlayer playerInv, ItemTransmutatorInv item)
    {
        this.inventory = item;

//        Slots for souls
        {
            this.addSlotToContainer(new SlotTransmutatorSoul(this.inventory, 0, 136, 67));
        }

//        Slots of hotbar
        {
            this.addSlotToContainer(new Slot(this.inventory, 1, 8, 142));
            this.addSlotToContainer(new Slot(this.inventory, 2, 26, 142));
            this.addSlotToContainer(new Slot(this.inventory, 3, 44, 142));
            this.addSlotToContainer(new Slot(this.inventory, 4, 62, 142));
            this.addSlotToContainer(new Slot(this.inventory, 5, 80, 142));
            this.addSlotToContainer(new Slot(this.inventory, 6, 98, 142));
            this.addSlotToContainer(new Slot(this.inventory, 7, 116, 142));
            this.addSlotToContainer(new Slot(this.inventory, 8, 134, 142));
            this.addSlotToContainer(new Slot(this.inventory, 9, 152, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null)
        {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (index < HOTBAR_START)
            {
                if (!this.mergeItemStack(itemStack1, HOTBAR_START, HOTBAR_END + 1, true))
                {
                    return null;
                }
            } else
            {
                if (itemStack1.getItem() instanceof ItemSoulElement)
                {
                    if (!this.mergeItemStack(itemStack1, 0, ItemTransmutatorInv.INV_SIZE, false))
                    {
                        return null;
                    }
                }
            }

            if (itemStack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemStack1.stackSize == itemStack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemStack1);
        }

        return itemStack;
    }

    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player)
    {
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem())
        {
            return null;
        }

        return super.slotClick(slot, button, flag, player);
    }
}