/**
 * File created on 12:53 16.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.block.BlockSoulExtractor;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;

import javax.annotation.Nullable;

public class TileEntitySoulExtractor extends TileEntity implements IInventory, ITickable {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    private String title;

    private int burnTime,
            currentBurnTime,
            cookTime,
            totalCookTime;

    @Override
    public String getName() {
        return this.hasCustomName() ? this.title : "container.soulExtractor";
    }

    @Override
    public boolean hasCustomName() {
        return title != null && !title.isEmpty();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack stack1 = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(stack1) && ItemStack.areItemStackTagsEqual(stack, stack1);
        this.inventory.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
            stack.setCount(this.getInventoryStackLimit());
        if (index + 1 == 1 && !flag) {
            ItemStack stack2 = this.inventory.get(index + 1);
            this.totalCookTime = this.getCookTime(stack, stack2);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inventory);
        this.burnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("TotalCookTime");
        this.currentBurnTime = getItemBurnTime(this.inventory.get(2));

        if (compound.hasKey("CustomName", 8)) this.setTitle(compound.getString("CustomName"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", this.burnTime);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("TotalCookTime", this.totalCookTime);
        ItemStackHelper.saveAllItems(compound, this.inventory);

        if (this.hasCustomName()) compound.setString("CustomName", this.title);
        return compound;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory) {
        return inventory.getField(0) > 0;
    }

    @Override
    public void update() {
        boolean flag = this.isBurning(),
                flag1 = false;
        if (this.isBurning()) --this.burnTime;

        if (!this.world.isRemote) {
            ItemStack stack = this.inventory.get(2);

            if (this.isBurning() || !stack.isEmpty() && !(((this.inventory.get(0)).isEmpty()) || (this.inventory.get(1)).isEmpty())) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.burnTime = getItemBurnTime(stack);
                    this.currentBurnTime = this.burnTime;

                    if (this.isBurning()) {
                        flag1 = true;

                        if (!stack.isEmpty()) {
                            Item item = stack.getItem();
                            stack.shrink(1);

                            if (stack.isEmpty()) {
                                ItemStack stack1 = item.getContainerItem(stack);
                                inventory.set(2, stack1);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(inventory.get(0), inventory.get(1));
                        flag1 = true;
                    }
                } else this.cookTime = 0;
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != isBurning()) {
                flag1 = true;
                BlockSoulExtractor.setState(this.isBurning(), this.world, this.pos);
            }
        }
        if (flag1) this.markDirty();
    }

    public int getCookTime(ItemStack input1, ItemStack input2) {
        return 200;
    }

    private boolean canSmelt() {
        if ((inventory.get(0)).isEmpty() || (inventory.get(1)).isEmpty()) return false;
        else {
            ItemStack result = SoulExtractorRecipes.get().getExtractingResult(inventory.get(0), inventory.get(1));
            if (result.isEmpty()) return false;
            else {
                ItemStack output = inventory.get(3);
                if (output.isEmpty()) return true;
                if (!output.isItemEqual(result)) return false;
                int res = output.getCount() + result.getCount();
                return res <= getInventoryStackLimit() && res <= output.getMaxStackSize();
            }
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack input1 = inventory.get(0);
            ItemStack input2 = inventory.get(1);
            ItemStack result = SoulExtractorRecipes.get().getExtractingResult(input1, input2);
            ItemStack output = inventory.get(3);

            if (output.isEmpty()) inventory.set(3, result.copy());
            else if (output.getItem() == result.getItem()) output.grow(result.getCount());

            input1.shrink(1);
            input2.shrink(1);
        }
    }

    public static int getItemBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) return 0;
        else {
            Item item = fuel.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == CCBlocks.BLUESTONE_ORE) return 1500;
                if (block == CCBlocks.BLUESTONE_BLOCK) return 16000;
            }

            if (item == CCItems.BLUESTONE) return 1600;

            return 0;
        }
    }

    public static boolean isItemFuel(ItemStack fuel) {
        return getItemBurnTime(fuel) > 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5D,
                this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (index == 3) return false;
        else if (index != 2) return true;
        else return isItemFuel(stack);
    }

    public String getGuiID() {
        return "craftable_creatures:soul_extractor";
    }

    @Override
    public int getField(int ID) {
        switch (ID) {
            case 0:
                return this.burnTime;
            case 1:
                return this.currentBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int ID, int value) {
        switch (ID) {
            case 0:
                this.burnTime = value;
                break;
            case 1:
                this.currentBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount() {
        return 4;
    }

    public void clear() {
        inventory.clear();
    }
}