/**
 * File created on 14:18 22.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import ru.wertyfiregames.craftablecreatures.api.ISoulElement;

import java.util.Arrays;

public class InventoryTransmutator implements IInventory {
    // Slots: 0-8 - small storage; 9 - soul to morph
    // Total: 10
    public final EntityPlayer user;
    public final ItemStack sourceItem;

    private final ItemStack[] transmutatorItemStacks = new ItemStack[10];

    private String transmutatorCustomName;

    public InventoryTransmutator(EntityPlayer player) {
        user = player;
        if (user.getHeldItem(EnumHand.MAIN_HAND) != null)
            sourceItem = user.getHeldItem(EnumHand.MAIN_HAND);
        else sourceItem = user.getHeldItem(EnumHand.OFF_HAND);
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

    public ItemStack removeStackFromSlot(int slot) {
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

    public String getName() {
        return hasCustomName() ? transmutatorCustomName : "container.transmutator";
    }
    public boolean hasCustomName() {
        return transmutatorCustomName != null && !transmutatorCustomName.isEmpty();
    }
    public void setCustomInventoryName(String transmutatorCustomName) {
        this.transmutatorCustomName = transmutatorCustomName;
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
            transmutatorCustomName = nbt.getString("CustomName");
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
        if (hasCustomName())
            nbt.setString("CustomName", transmutatorCustomName);
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

    public void openInventory(EntityPlayer player) {}
    public void closeInventory(EntityPlayer player) {}

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return item.getItem() instanceof ISoulElement;
    }

    public int getField(int id) {
        return 0;
    }

    public void setField(int id, int value) {}

    public int getFieldCount() {
        return 0;
    }

    public void clear() {
        Arrays.fill(transmutatorItemStacks, null);
    }

    public ITextComponent getDisplayName() {
        return (hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName()));
    }
}