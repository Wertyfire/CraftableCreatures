/**
 * File created on 22:03 28.04.2026 by Wertyfire
 */

package craftablecreatures;

import forge.ISidedInventory;
import net.minecraft.src.*;

public class TileEntityCombiner extends TileEntity implements IInventory, ISidedInventory {
    // Slots: 0 - ingredient; 1 - blueprint (of spawn egg, usually); 2 - output
    // Total: 3

    private ItemStack[] combinerItemStacks = new ItemStack[3];

    public int combineTime;

    public int getSizeInventory() {
        return combinerItemStacks.length;
    }

    public int getStartInventorySide(int side) {
        return side == 0 ? 2 : (side == 1 ? 1 : 0);
    }

    public int getSizeInventorySide(int side) {
        return 1;
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

    public void setInventorySlotContents(int slot, ItemStack item) {
        combinerItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();
    }

    @Override
    public String getInvName() {
        return "Combiner";
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList nbtList = nbt.getTagList("Items");
        combinerItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbtList.tagCount(); ++i) {
            NBTTagCompound nbtCompound = (NBTTagCompound) nbtList.tagAt(i);
            byte slot = nbtCompound.getByte("Slot");

            if (slot >= 0 && slot < combinerItemStacks.length)
                combinerItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbtCompound);
        }

        combineTime = nbt.getShort("CombineTime");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setShort("CombineTime", (short) combineTime);
        NBTTagList nbtList = new NBTTagList();

        for (int i = 0; i < combinerItemStacks.length; ++i) {
            if (combinerItemStacks[i] != null) {
                NBTTagCompound nbtCompound = new NBTTagCompound();
                nbtCompound.setByte("Slot", (byte) i);
                combinerItemStacks[i].writeToNBT(nbtCompound);
                nbtList.setTag(nbtCompound);
            }
        }

        nbt.setTag("Items", nbtList);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void updateEntity() {
        boolean working = canCombineItems();
        boolean changed = false;

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

        BlockCombiner.updateCombinerBlockState(worldObj, xCoord, yCoord, zCoord, working);

        if (changed) onInventoryChanged();
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
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d) <= 64d;
    }

    public void openChest() {}
    public void closeChest() {}
}