/**
 * File created on 16:30 26.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ru.wertyfiregames.craftablecreatures.block.BlockCombiner;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;

public class TileEntityCombiner extends TileEntity implements ISidedInventory {
    // Slots: 0 - ingredient; 1 - blueprint (of spawn egg, usually); 2 - output
    // Total: 3

    private ItemStack[] combinerItemStacks = new ItemStack[3];
    private static final int[] slotAccess_fromTop = new int[] { 0, 1 };
    private static final int[] slotAccess_fromBottom = new int[] { 2 };
    private static final int[] slotAccess_fromSides = new int[] {};

    public int combineTime;

    private String customName;

    public int getSizeInventory() {
        return combinerItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return combinerItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int amount) {
        if (combinerItemStacks[slot] != null) {
            ItemStack stack;

            if (combinerItemStacks[slot].stackSize <= amount) {
                stack = combinerItemStacks[slot];
                combinerItemStacks[slot] = null;
            } else {
                stack = combinerItemStacks[slot].splitStack(amount);
                if (combinerItemStacks[slot].stackSize == 0) combinerItemStacks[slot] = null;
            }
            return stack;
        } else return null;
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (combinerItemStacks[slot] != null) {
            ItemStack stack = combinerItemStacks[slot];
            combinerItemStacks[slot] = null;
            return stack;
        } else return null;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        combinerItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();
    }

    public String getInventoryName() {
        return hasCustomInventoryName() ? customName : "container.combiner";
    }
    public boolean hasCustomInventoryName() {
        return !(customName == "" || customName == null);
    }
    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList nbtList = nbt.getTagList("Items", 10);
        combinerItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbtList.tagCount(); i++) {
            NBTTagCompound nbtCompound = nbtList.getCompoundTagAt(i);
            byte slot = nbtCompound.getByte("Slot");

            if (slot >= 0 && slot < combinerItemStacks.length)
                combinerItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbtCompound);

            combineTime = nbt.getShort("CombineTime");

            if (nbt.hasKey("CustomName"))
                customName = nbt.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setShort("CombineTime", (short) combineTime);
        NBTTagList nbtList = new NBTTagList();

        for (int i = 0; i < combinerItemStacks.length; i++) {
            if (combinerItemStacks[i] != null) {
                NBTTagCompound nbtCompound = new NBTTagCompound();
                nbtCompound.setByte("Slot", (byte) i);
                combinerItemStacks[i].writeToNBT(nbtCompound);
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

    @SideOnly(Side.CLIENT)
    public int getCombineProgressScaled(int scale) {
        return combineTime * scale / 200;
    }

    public void updateEntity() {
        boolean working = canCombineItems();
        boolean changed = false;

        if (!worldObj.isRemote) {
            if (combinerItemStacks[0] != null && combinerItemStacks[1] != null) {
                if (canCombineItems()) {
                    ++combineTime;

                    if (combineTime == 200) {
                        combineTime = 0;
                        combineItems();
                        changed = true;
                    }
                } else combineTime = 0;
            } else combineTime = 0;

            BlockCombiner block = (BlockCombiner) worldObj.getBlock(xCoord, yCoord, zCoord);
            if (block == CCBlocks.combiner && working)
                BlockCombiner.updateCombinerBlockState(worldObj, xCoord, yCoord, zCoord, true);
            else if (block == CCBlocks.lit_combiner && !working)
                BlockCombiner.updateCombinerBlockState(worldObj, xCoord, yCoord, zCoord, false);
        }

        if (changed) markDirty();
    }

    private boolean canCombineItems() {
        if (combinerItemStacks[0] == null || combinerItemStacks[1] == null) return false;
        else {
            ItemStack stack = CombinerRecipes.get().getCombiningResult(combinerItemStacks[0], combinerItemStacks[1]);
            if (stack == null) return false;
            if (combinerItemStacks[2] == null) return true;
            if (!combinerItemStacks[2].isItemEqual(stack)) return false;

            int result = combinerItemStacks[2].stackSize + stack.stackSize;
            return result <= getInventoryStackLimit() && result <= combinerItemStacks[2].getMaxStackSize();
        }
    }

    public void combineItems() {
        if (canCombineItems()) {
            ItemStack stack = CombinerRecipes.get().getCombiningResult(combinerItemStacks[0], combinerItemStacks[1]);
            if (combinerItemStacks[2] == null) combinerItemStacks[2] = stack.copy();
            else if (combinerItemStacks[2].getItem() == stack.getItem()) combinerItemStacks[2].stackSize +=
                    stack.stackSize;

            --combinerItemStacks[0].stackSize;
            --combinerItemStacks[1].stackSize;

            if (combinerItemStacks[0].stackSize <= 0) combinerItemStacks[0] = null;
            if (combinerItemStacks[1].stackSize <= 0) combinerItemStacks[1] = null;
        }
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d) <= 64d;
    }

    public void openInventory() {}
    public void closeInventory() {}

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot != 2;
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slotAccess_fromBottom : (side == 1 ? slotAccess_fromTop : slotAccess_fromSides);
    }

    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return side != 0 || slot == 2;
    }
}