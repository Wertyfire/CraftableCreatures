/**
 * File created on 16:30 26.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.block.BlockCombiner;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerCombiner;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;

import java.util.Arrays;

public class TileEntityCombiner extends TileEntityLockable implements ITickable, ISidedInventory {
    // Slots: 0 - ingredient; 1 - blueprint (of spawn egg, usually); 2 - output
    // Total: 3

    private ItemStack[] combinerItemStacks = new ItemStack[3];
    private static final int[] slotAccess_fromTop = new int[] { 0, 1 };
    private static final int[] slotAccess_fromBottom = new int[] { 2 };
    private static final int[] slotAccess_fromSides = new int[] {};

    private int combineTime;
    private int totalCombineTime;

    private String combinerCustomName;

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

    public ItemStack removeStackFromSlot(int slot) {
        if (combinerItemStacks[slot] != null) {
            ItemStack stack = combinerItemStacks[slot];
            combinerItemStacks[slot] = null;
            return stack;
        } else return null;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        boolean flag = item != null && item.isItemEqual(combinerItemStacks[slot]) && ItemStack.areItemStackTagsEqual(item, combinerItemStacks[slot]);
        combinerItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();

        if ((slot == 0 || slot == 1) && !flag) {
            totalCombineTime = slot == 0 ? getCombineTime(item, combinerItemStacks[1]) : getCombineTime(combinerItemStacks[0], item);
            combineTime = 0;
            markDirty();
        }
    }

    public String getName() {
        return hasCustomName() ? combinerCustomName : "container.combiner";
    }
    public boolean hasCustomName() {
        return combinerCustomName != null && combinerCustomName.length() > 0;
    }
    public void setCustomInventoryName(String combinerCustomName) {
        this.combinerCustomName = combinerCustomName;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList nbtList = nbt.getTagList("Items", 10);
        combinerItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbtList.tagCount(); i++) {
            NBTTagCompound nbtCompound = nbtList.getCompoundTagAt(i);
            int slot = nbtCompound.getByte("Slot");

            if (slot >= 0 && slot < combinerItemStacks.length)
                combinerItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbtCompound);

            combineTime = nbt.getInteger("CombineTime");
            totalCombineTime = nbt.getInteger("CombineTimeTotal");

            if (nbt.hasKey("CustomName", 8))
                combinerCustomName = nbt.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("CombineTime", combineTime);
        nbt.setInteger("CombineTimeTotal", totalCombineTime);
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

        if (hasCustomName())
            nbt.setString("CustomName", combinerCustomName);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public void update() {
        boolean working = canCombineItems();
        boolean changed = false;

        if (!worldObj.isRemote) {
            if (combinerItemStacks[0] != null && combinerItemStacks[1] != null) {
                if (canCombineItems()) {
                    ++combineTime;

                    if (combineTime == totalCombineTime) {
                        combineTime = 0;
                        totalCombineTime = getCombineTime(combinerItemStacks[0], combinerItemStacks[1]);
                        combineItems();
                        changed = true;
                    }
                } else combineTime = 0;
            } else combineTime = 0;

            BlockCombiner.setState(worldObj, pos, working);
        }

        if (changed) markDirty();
    }

    public int getCombineTime(ItemStack stack1, ItemStack stack2) {
        return 200;
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
        return worldObj.getTileEntity(pos) == this &&
                player.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) <= 64d;
    }

    public void openInventory(EntityPlayer player) {}
    public void closeInventory(EntityPlayer player) {}

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot != 2;
    }

    public int[] getSlotsForFace(EnumFacing facing) {
        return facing == EnumFacing.DOWN ? slotAccess_fromBottom : (facing == EnumFacing.UP ? slotAccess_fromTop : slotAccess_fromSides);
    }

    public boolean canInsertItem(int slot, ItemStack item, EnumFacing facing) {
        return isItemValidForSlot(slot, item);
    }
    public boolean canExtractItem(int slot, ItemStack item, EnumFacing facing) {
        return facing != EnumFacing.DOWN || slot == 2;
    }

    public String getGuiID() {
        return CraftableCreatures.getModId() + ":" + "combiner";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerCombiner(playerInventory, this);
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return combineTime;
            case 1:
                return totalCombineTime;
            default:
                return 0;
        }
    }
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                combineTime = value;
                break;
            case 1:
                totalCombineTime = value;
        }
    }
    public int getFieldCount() {
        return 2;
    }

    public void clear() {
        Arrays.fill(combinerItemStacks, null);
    }

    IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
    IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
    IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else return (T) handlerSide;
        }
        return super.getCapability(capability, facing);
    }
}