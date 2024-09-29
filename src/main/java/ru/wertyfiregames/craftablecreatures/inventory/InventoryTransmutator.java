/**
 * File created on 14:18 22.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ru.wertyfiregames.craftablecreatures.api.ISoulElement;

public class InventoryTransmutator implements IInventory {
    // Slots: 0-8 - small storage; 9 - soul to morph
    // Total: 10
    private final EntityPlayer user;
    private final ItemStack sourceItem;

    private final ItemStack[] transmutatorItemStacks = new ItemStack[10];

    private String customName;

    public InventoryTransmutator(EntityPlayer player) {
        user = player;
        sourceItem = user.getHeldItem();
        if (!sourceItem.hasTagCompound())
            sourceItem.setTagCompound(new NBTTagCompound());

        readFromNBT(sourceItem.getTagCompound());
    }

    public int getSizeInventory() {
        return transmutatorItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return transmutatorItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int amount) {
        if (transmutatorItemStacks[slot] != null) {
            ItemStack stack;

            if (transmutatorItemStacks[slot].stackSize <= amount) {
                stack = transmutatorItemStacks[slot];
                transmutatorItemStacks[slot] = null;
                markDirty();
            } else {
                stack = transmutatorItemStacks[slot].splitStack(amount);
                if (transmutatorItemStacks[slot].stackSize == 0) transmutatorItemStacks[slot] = null;
            }
            return stack;
        } else return null;
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (transmutatorItemStacks[slot] != null) {
            ItemStack stack = transmutatorItemStacks[slot];
            transmutatorItemStacks[slot] = null;
            return stack;
        } else return null;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        transmutatorItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();

        markDirty();
    }

    public String getInventoryName() {
        return hasCustomInventoryName() ? customName : "container.transmutator";
    }
    public boolean hasCustomInventoryName() {
        return !(customName == "" || customName == null);
    }
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        NBTTagList nbtList = nbt.getTagList("Items", 10);

        for (int i = 0; i < nbtList.tagCount(); ++i) {
            NBTTagCompound item = nbtList.getCompoundTagAt(i);
            int slot = item.getInteger("Slot");

            if (slot >= 0 && slot < getSizeInventory())
                transmutatorItemStacks[slot] = ItemStack.loadItemStackFromNBT(item);
        }

        if (nbt.hasKey("CustomName"))
            customName = nbt.getString("CustomName");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList nbtList = new NBTTagList();

        for (int i = 0; i < getSizeInventory(); ++i) {
            if (getStackInSlot(i) != null) {
                NBTTagCompound nbtCompound = new NBTTagCompound();
                nbtCompound.setInteger("Slot", i);
                getStackInSlot(i).writeToNBT(nbtCompound);
                nbtList.appendTag(nbtCompound);
            }
        }

        nbt.setTag("Items", nbtList);
        if (hasCustomInventoryName())
            nbt.setString("CustomName", customName);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void markDirty() {
        for (int i = 0; i < getSizeInventory(); i++) {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
                transmutatorItemStacks[i] = null;
        }

        writeToNBT(sourceItem.getTagCompound());

        user.inventory.mainInventory[user.inventory.currentItem] = sourceItem;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    public void openInventory() {}
    public void closeInventory() {}

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return item.getItem() instanceof ISoulElement;
    }
}