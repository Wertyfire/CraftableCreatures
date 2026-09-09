/**
 * File created on 21:48 17.03.2026 by Wertyfire
 */

package craftablecreatures;

import craftablecreatures.api.CraftableCreaturesRegistry;
import forge.ISidedInventory;
import net.minecraft.src.*;

public class TileEntitySoulExtractor extends TileEntity implements IInventory, ISidedInventory {
    // Slots: 0 - main ingredient (mob's item); 1 - fuel; 2 - extract helper item; 3 - output
    // Total: 4

    private ItemStack[] soulExtractorItemStacks = new ItemStack[4];

    public int fuelWorkTime, currentFuelWorkTime, extractTime;
    public int getSizeInventory() {
        return soulExtractorItemStacks.length;
    }

    public int getStartInventorySide(int side) {
        return side == 0 ? 1 : (side == 1 ? 0 : 3);
    }

    public int getSizeInventorySide(int side) {
        return 1;
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

    public void setInventorySlotContents(int slot, ItemStack item) {
        soulExtractorItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();
    }

    public String getInvName() {
        return "Soul Extractor";
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList nbtList = nbt.getTagList("Items");
        soulExtractorItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbtList.tagCount(); ++i) {
            NBTTagCompound nbtCompound = (NBTTagCompound) nbtList.tagAt(i);
            byte slot = nbtCompound.getByte("Slot");

            if (slot >= 0 && slot < soulExtractorItemStacks.length)
                soulExtractorItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbtCompound);
        }

        fuelWorkTime = nbt.getShort("FuelWorkTime");
        extractTime = nbt.getShort("ExtractTime");
        currentFuelWorkTime = getFuelWorkTime(soulExtractorItemStacks[1]);
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
                nbtList.setTag(nbtCompound);
            }
        }

        nbt.setTag("Items", nbtList);
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean hasFuel() {
        return fuelWorkTime > 0;
    }

    public void updateEntity() {
        boolean working = fuelWorkTime > 0;
        boolean changed = false;

        if (fuelWorkTime > 0) --fuelWorkTime;

        if (fuelWorkTime != 0 || soulExtractorItemStacks[1] != null && soulExtractorItemStacks[0] != null && soulExtractorItemStacks[2] != null) {
            if (fuelWorkTime == 0 && canExtractSoul()) {
                currentFuelWorkTime = fuelWorkTime = getFuelWorkTime(soulExtractorItemStacks[1]);

                if (fuelWorkTime > 0) {
                    changed = true;

                    if (soulExtractorItemStacks[1] != null) {
                        --soulExtractorItemStacks[1].stackSize;

                        if (soulExtractorItemStacks[1].stackSize == 0)
                            soulExtractorItemStacks[1] = null;
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

        if (changed) onInventoryChanged();
    }

    private boolean canExtractSoul() {
        if (soulExtractorItemStacks[0] == null || soulExtractorItemStacks[2] == null) return false;
        else {
            ItemStack stack = SoulExtractorRecipes.get().getExtractingResult(soulExtractorItemStacks[0]);
            boolean flag = SoulExtractorRecipes.get().isItemExtractHelper(soulExtractorItemStacks[2]);
            if (stack == null || !flag) return false;
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


            ItemStack ingredient = soulExtractorItemStacks[0];

            if (ingredient.getItem() instanceof ItemBucket ||
                    ingredient.getItem() instanceof ItemBucketMilk && (soulExtractorItemStacks[1] == null || soulExtractorItemStacks[1].getItem() == Item.bucketEmpty))
                soulExtractorItemStacks[1] = new ItemStack(Item.bucketEmpty);

            if (soulExtractorItemStacks[0].stackSize <= 0) soulExtractorItemStacks[0] = null;
            if (soulExtractorItemStacks[2].stackSize <= 0) soulExtractorItemStacks[2] = null;
        }
    }

    public static int getFuelWorkTime(ItemStack fuel) {
        if (fuel == null) return 0;
        else {
            int item = fuel.itemID;
            return item == mod_CraftableCreatures.bluestone.shiftedIndex ? 1600 : (item == mod_CraftableCreatures.bluestoneBlock.blockID ? 16000 : CraftableCreaturesRegistry.getSEFuelValue(fuel));
        }
    }

    public static boolean isItemFuel(ItemStack item) {
        return getFuelWorkTime(item) > 0;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5d, yCoord + 0.5d, zCoord + 0.5d) <= 64d;
    }

    public void openChest() {}
    public void closeChest() {}
}