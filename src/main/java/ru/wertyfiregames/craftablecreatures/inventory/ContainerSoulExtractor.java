/**
 * File created on 17:11 17.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class ContainerSoulExtractor extends Container {
    private final TileEntitySoulExtractor tile;
    private int cookTime, totalCookTime, burnTime, currentBurnTime;

    public ContainerSoulExtractor(InventoryPlayer player, TileEntitySoulExtractor soulExtractor) {
        tile = soulExtractor;

        this.addSlotToContainer(new Slot(tile, 0, 56, 17));
        this.addSlotToContainer(new Slot(tile, 1, 56, 53));
        this.addSlotToContainer(new SlotSoulExtractorFuel(tile, 2, 15, 53));
        this.addSlotToContainer(new SlotSoulExtractorOutput(player.player, tile, 3, 116, 35));

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
        }
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tile);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < listeners.size(); i++) {
            IContainerListener listener = listeners.get(i);

            if (cookTime != tile.getField(2)) listener.sendWindowProperty(this,
                    2, tile.getField(2));
            if (burnTime != tile.getField(0)) listener.sendWindowProperty(this,
                    0, tile.getField(0));
            if (currentBurnTime != tile.getField(1)) listener.sendWindowProperty(this,
                    1, tile.getField(1));
            if (totalCookTime != tile.getField(3)) listener.sendWindowProperty(this,
                    3, tile.getField(3));
        }

        cookTime = tile.getField(2);
        burnTime = tile.getField(0);
        currentBurnTime = tile.getField(1);
        totalCookTime = tile.getField(3);
    }

    @Override
    public void updateProgressBar(int id, int data) {
        tile.setField(id, data);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if (index == 3) {
                if (!mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
                slot.onSlotChange(stack1, stack);
            } else if (index != 2 && index != 1 && index != 0) {
                Slot slot1 = inventorySlots.get(index + 1);

                if (!SoulExtractorRecipes.get().getExtractingResult(stack1, slot1.getStack()).isEmpty()) {
                    if (!mergeItemStack(stack1, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    } else if (TileEntitySoulExtractor.isItemFuel(stack1)) {
                        if (!mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    } else if (TileEntitySoulExtractor.isItemFuel(stack1)) {
                        if (!mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    } else if (TileEntitySoulExtractor.isItemFuel(stack1)) {
                        if (!mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
                    } else if (index >= 4 && index < 31) {
                        if (!mergeItemStack(stack1, 31 ,40, false)) return ItemStack.EMPTY;
                    } else if (index >= 31 && index < 40 && !mergeItemStack(stack1, 4, 31, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!mergeItemStack(stack1, 4, 40, false)) {
                return ItemStack.EMPTY;
            }

            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, stack1);
        }

        return stack;
    }
}