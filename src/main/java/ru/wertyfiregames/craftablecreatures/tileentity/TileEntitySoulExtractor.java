/**
 * File created on 12:55 22.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.block.BlockSoulExtractor;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import javax.annotation.Nullable;
import java.util.Arrays;

public class TileEntitySoulExtractor extends TileEntityLockable implements ITickable, ISidedInventory {
    // Slots: 0 - main ingredient (mob's item); 1 - fuel; 2 - extract helper item; 3 - output
    // Total: 4

    private ItemStack[] soulExtractorItemStacks = new ItemStack[4];
    private static final int[] slotAccess_fromTop = new int[] { 0, 2 };
    private static final int[] slotAccess_fromBottom = new int[] { 3, 1 };
    private static final int[] slotAccess_fromSides = new int[] { 1 };

    private int fuelWorkTime, currentFuelWorkTime, extractTime, totalExtractTime;

    private String soulExtractorCustomName;

    public int getSizeInventory() {
        return soulExtractorItemStacks.length;
    }

    public ItemStack getStackInSlot(int slot) {
        return soulExtractorItemStacks[slot];
    }

    public ItemStack decrStackSize(int slot, int amount) {
        return ItemStackHelper.getAndSplit(soulExtractorItemStacks, slot, amount);
    }

    public ItemStack removeStackFromSlot(int slot) {
        return ItemStackHelper.getAndRemove(soulExtractorItemStacks, slot);
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        boolean flag = item != null && item.isItemEqual(soulExtractorItemStacks[slot]) && ItemStack.areItemStackTagsEqual(item, soulExtractorItemStacks[slot]);
        soulExtractorItemStacks[slot] = item;

        if (item != null && item.stackSize > getInventoryStackLimit())
            item.stackSize = getInventoryStackLimit();

        if (slot == 0 && !flag) {
            totalExtractTime = getExtractTime(item);
            extractTime = 0;
            markDirty();
        }
    }

    public String getName() {
        return hasCustomName() ? soulExtractorCustomName : "container.soulExtractor";
    }
    public boolean hasCustomName() {
        return soulExtractorCustomName != null && !soulExtractorCustomName.isEmpty();
    }
    public void setCustomInventoryName(String soulExtractorCustomName) {
        this.soulExtractorCustomName = soulExtractorCustomName;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList nbtList = nbt.getTagList("Items", 10);
        soulExtractorItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbtList.tagCount(); ++i) {
            NBTTagCompound nbtCompound = nbtList.getCompoundTagAt(i);
            int slot = nbtCompound.getByte("Slot");

            if (slot >= 0 && slot < soulExtractorItemStacks.length)
                soulExtractorItemStacks[slot] = ItemStack.loadItemStackFromNBT(nbtCompound);
        }

        fuelWorkTime = nbt.getInteger("FuelWorkTime");
        extractTime = nbt.getInteger("ExtractTime");
        totalExtractTime = nbt.getInteger("ExtractTimeTotal");
        currentFuelWorkTime = getFuelWorkTime(soulExtractorItemStacks[1]);

        if (nbt.hasKey("CustomName", 8))
            soulExtractorCustomName = nbt.getString("CustomName");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("FuelWorkTime", fuelWorkTime);
        nbt.setInteger("ExtractTime", extractTime);
        nbt.setInteger("ExtractTimeTotal", totalExtractTime);
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

        if (hasCustomName())
            nbt.setString("CustomName", soulExtractorCustomName);

        return nbt;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isWorking() {
        return fuelWorkTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isWorking(IInventory inv) {
        return inv.getField(0) > 0;
    }

    public void update() {
        boolean working = isWorking();
        boolean changed = false;

        if (isWorking()) --fuelWorkTime;

        if (!worldObj.isRemote) {
            if (isWorking() || soulExtractorItemStacks[1] != null && soulExtractorItemStacks[0] != null && soulExtractorItemStacks[2] != null) {
                if (!isWorking() && canExtractSoul()) {
                    currentFuelWorkTime = fuelWorkTime = getFuelWorkTime(soulExtractorItemStacks[1]);

                    if (isWorking()) {
                        changed = true;

                        if (soulExtractorItemStacks[1] != null) {
                            --soulExtractorItemStacks[1].stackSize;

                            if (soulExtractorItemStacks[1].stackSize == 0)
                                soulExtractorItemStacks[1] = soulExtractorItemStacks[1].getItem().getContainerItem(soulExtractorItemStacks[1]);
                        }
                    }
                }

                if (isWorking() && canExtractSoul()) {
                    ++extractTime;

                    if (extractTime == totalExtractTime) {
                        extractTime = 0;
                        totalExtractTime = getExtractTime(soulExtractorItemStacks[0]);
                        extractSoul();
                        changed = true;
                    }
                } else extractTime = 0;
            } else if (!isWorking() && extractTime > 0)
                extractTime = MathHelper.clamp_int(extractTime - 2, 0, totalExtractTime);

            if (working != isWorking()) {
                changed = true;
                BlockSoulExtractor.setState(worldObj, pos, isWorking());
            }
        }

        if (changed) markDirty();
    }

    public int getExtractTime(@Nullable ItemStack stack) {
        return 200;
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

            if (ingredient.getItem() instanceof ItemBucket || ingredient.getItem() instanceof ItemBucketMilk &&
                    (soulExtractorItemStacks[1] == null || soulExtractorItemStacks[1].getItem() == Items.BUCKET))
                soulExtractorItemStacks[1] = new ItemStack(Items.BUCKET);

            if (soulExtractorItemStacks[0].stackSize <= 0) soulExtractorItemStacks[0] = null;
            if (soulExtractorItemStacks[2].stackSize <= 0) soulExtractorItemStacks[2] = null;
        }
    }

    public static int getFuelWorkTime(ItemStack fuel) {
        if (fuel == null) return 0;
        else {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == CCBlocks.BLUESTONE_BLOCK) return 16000;
            }

            if (item == CCItems.BLUESTONE) return 1600;
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
        return worldObj.getTileEntity(pos) == this &&
                player.getDistanceSq(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d) <= 64d;
    }

    public void openInventory(EntityPlayer player) {}
    public void closeInventory(EntityPlayer player) {}

    public boolean isItemValidForSlot(int slot, ItemStack item) {
        if (slot == 0)
            return SoulExtractorRecipes.get().getExtractingResult(item) != null;
        else if (slot == 1)
            return isItemFuel(item);
        else if (slot == 2)
            return isItemExtractHelper(item);
        return false;
    }

    public int[] getSlotsForFace(EnumFacing facing) {
        return facing == EnumFacing.DOWN ? slotAccess_fromBottom : (facing == EnumFacing.UP ? slotAccess_fromTop : slotAccess_fromSides);
    }

    public boolean canInsertItem(int slot, ItemStack item, EnumFacing facing) {
        return isItemValidForSlot(slot, item);
    }
    public boolean canExtractItem(int slot, ItemStack item, EnumFacing facing) {
        return facing != EnumFacing.DOWN || slot == 3;
    }

    public String getGuiID() {
        return CraftableCreatures.getModId() + ":" + "soul_extractor";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerSoulExtractor(playerInventory, this);
    }

    public int getField(int id) {
        switch (id) {
            case 0:
                return fuelWorkTime;
            case 1:
                return currentFuelWorkTime;
            case 2:
                return extractTime;
            case 3:
                return totalExtractTime;
            default:
                return 0;
        }
    }
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                fuelWorkTime = value;
                break;
            case 1:
                currentFuelWorkTime = value;
                break;
            case 2:
                extractTime = value;
                break;
            case 3:
                totalExtractTime = value;
        }
    }
    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        Arrays.fill(soulExtractorItemStacks, null);
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