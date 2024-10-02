/**
 * File created on 12:55 22.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ru.wertyfiregames.craftablecreatures.block.BlockSoulExtractor;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;
import ru.wertyfiregames.craftablecreatures.api.event.CraftableCreaturesEventFactory;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

public class TileEntitySoulExtractor extends TileEntity implements ISidedInventory {
    // Slots: 0 - main ingredient (mob's item); 1 - fuel; 2 - extract helper item; 3 - output
    // Total: 4

    private ItemStack[] soulExtractorItemStacks = new ItemStack[4];
    private static final int[] slotAccess_fromTop = new int[] { 0, 2 };
    private static final int[] slotAccess_fromBottom = new int[] { 3, 1 };
    private static final int[] slotAccess_fromSides = new int[] { 1 };

    public int fuelWorkTime, currentFuelWorkTime, extractTime;

    private String customName;

    public int getSizeInventory() {
        return soulExtractorItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return soulExtractorItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int amount) {
        if (soulExtractorItemStacks[slot] != null) {
            ItemStack stack;

            if (soulExtractorItemStacks[slot].stackSize <= amount) {
                stack = soulExtractorItemStacks[slot];
                soulExtractorItemStacks[slot] = null;
            } else {
                stack = soulExtractorItemStacks[slot].splitStack(amount);
                if (soulExtractorItemStacks[slot].stackSize == 0) soulExtractorItemStacks[slot] = null;
            }
            return stack;
        } else return null;
    }

    public ItemStack getStackInSlotOnClosing(int slot) {
        if (soulExtractorItemStacks[slot] != null) {
            ItemStack stack = soulExtractorItemStacks[slot];
            soulExtractorItemStacks[slot] = null;
            return stack;
        } else return null;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        soulExtractorItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();
    }

    public String getInventoryName() {
        return hasCustomInventoryName() ? customName : "container.soulExtractor";
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
        soulExtractorItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbtList.tagCount(); ++i) {
            NBTTagCompound nbtCompound = nbtList.getCompoundTagAt(i);
            byte slot = nbtCompound.getByte("Slot");

            if (slot >= 0 && slot < soulExtractorItemStacks.length)
                soulExtractorItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbtCompound);

            fuelWorkTime = nbt.getShort("FuelWorkTime");
            extractTime = nbt.getShort("ExtractTime");
            currentFuelWorkTime = getFuelWorkTime(soulExtractorItemStacks[1]);

            if (nbt.hasKey("CustomName", 8))
                customName = nbt.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setShort("FuelWorkTime", (short) fuelWorkTime);
        nbt.setShort("ExtractTime", (short) extractTime);
        NBTTagList nbtList = new NBTTagList();

        for (int i = 0; i < soulExtractorItemStacks.length; ++i) {
            if (soulExtractorItemStacks[i] != null) {
                NBTTagCompound nbtCompound = new NBTTagCompound();
                nbtCompound.setByte("Slot", (byte) i);
                soulExtractorItemStacks[i].writeToNBT(nbtCompound);
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
    public int getExtractProgressScaled(int scale) {
        return extractTime * scale / 200;
    }

    @SideOnly(Side.CLIENT)
    public int getFuelWorkTimeRemainingScaled(int scale) {
        if (currentFuelWorkTime == 0) currentFuelWorkTime = 200;

        return fuelWorkTime * scale / currentFuelWorkTime;
    }

    public boolean hasFuel() {
        return fuelWorkTime > 0;
    }

    public void updateEntity() {
        boolean working = fuelWorkTime > 0;
        boolean changed = false;

        if (fuelWorkTime > 0) --fuelWorkTime;

        if (!worldObj.isRemote) {
            if (fuelWorkTime != 0 || soulExtractorItemStacks[1] != null && soulExtractorItemStacks[0] != null && soulExtractorItemStacks[2] != null) {
                if (fuelWorkTime == 0 && canExtractSoul()) {
                    currentFuelWorkTime = fuelWorkTime = getFuelWorkTime(soulExtractorItemStacks[1]);

                    if (fuelWorkTime > 0) {
                        changed = true;

                        if (soulExtractorItemStacks[1] != null) {
                            --soulExtractorItemStacks[1].stackSize;

                            if (soulExtractorItemStacks[1].stackSize == 0)
                                soulExtractorItemStacks[1] = soulExtractorItemStacks[1].getItem().getContainerItem(soulExtractorItemStacks[1]);
                        }
                    }
                }

                if (hasFuel() && canExtractSoul()) {
                    ++extractTime;

                    if (extractTime == 200) {
                        extractTime = 0;
                        extractSoul();
                        changed = true;
                    }
                } else extractTime = 0;
            }

            if (working != fuelWorkTime > 0) {
                changed = true;
                BlockSoulExtractor.updateSoulExtractorBlockState(worldObj, xCoord, yCoord, zCoord, fuelWorkTime > 0);
            }
        }

        if (changed) markDirty();
    }

    private boolean canExtractSoul() {
        if (soulExtractorItemStacks[0] == null || soulExtractorItemStacks[2] == null) return false;
        else {
            ItemStack stack = SoulExtractorRecipes.get().getExtractingResult(soulExtractorItemStacks[0]);
            if (stack == null) return false;
            if (soulExtractorItemStacks[3] == null) return true;
            if (!soulExtractorItemStacks[3].isItemEqual(stack)) return false;

            int result = soulExtractorItemStacks[3].stackSize + stack.stackSize;
            return result <= getInventoryStackLimit() && result <= soulExtractorItemStacks[3].getMaxStackSize();
        }
    }

    public void extractSoul() {
        if (canExtractSoul()) {
            ItemStack stack = SoulExtractorRecipes.get().getExtractingResult(soulExtractorItemStacks[0]);
            if (soulExtractorItemStacks[3] == null) soulExtractorItemStacks[3] = stack.copy();
            else if (soulExtractorItemStacks[3].getItem() == stack.getItem()) soulExtractorItemStacks[3].stackSize +=
                    stack.stackSize;

            --soulExtractorItemStacks[0].stackSize;
            --soulExtractorItemStacks[2].stackSize;

            if (soulExtractorItemStacks[0].stackSize <= 0) soulExtractorItemStacks[0] = null;
            if (soulExtractorItemStacks[2].stackSize <= 0) soulExtractorItemStacks[2] = null;
        }
    }

    public static int getFuelWorkTime(ItemStack fuel) {
        if (fuel == null) return 0;
        else {
            int moddedBurnTime = CraftableCreaturesEventFactory.getSEFuelBurnTime(fuel);
            if (moddedBurnTime >= 0) return moddedBurnTime;

            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
                Block block = Block.getBlockFromItem(item);

                if (block == CCBlocks.bluestone_block) return 16000;
            }

            if (item == CCItems.bluestone) return 1600;
            return CraftableCreaturesRegistry.getSEFuelValue(fuel);
        }
    }

    public static boolean isItemFuel(ItemStack item) {
        return getFuelWorkTime(item) > 0;
    }

    public static boolean isItemExtractHelper(ItemStack item) {
        return SoulExtractorRecipes.get().isItemExtractHelper(item);
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d) <= 64d;
    }

    public void openInventory() {}
    public void closeInventory() {}

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return slot != 3 && (slot == 1 ? isItemFuel(item) : slot != 2 || isItemExtractHelper(item));
    }

    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 0 ? slotAccess_fromBottom : (side == 1 ? slotAccess_fromTop : slotAccess_fromSides);
    }

    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return side != 0 || slot == 3;
    }
}