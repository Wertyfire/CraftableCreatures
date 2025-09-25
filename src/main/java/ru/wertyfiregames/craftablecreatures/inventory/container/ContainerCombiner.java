/**
 * File created on 19:06 27.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.inventory.slot.SlotCombiner;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;

public class ContainerCombiner extends Container {
    private final IInventory tileCombiner;
    private int combineTime, totalCombineTime;

    public ContainerCombiner(InventoryPlayer inv, IInventory combiner) {
        tileCombiner = combiner;
        addSlotToContainer(new Slot(combiner, 0, 49, 15));
        addSlotToContainer(new Slot(combiner, 1, 111, 15));
        addSlotToContainer(new SlotCombiner(inv.player, combiner, 2, 80, 55));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileCombiner);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (IContainerListener crafting : listeners) {
            if (combineTime != tileCombiner.getField(0))
                crafting.sendProgressBarUpdate(this, 0, tileCombiner.getField(0));

            if (totalCombineTime != tileCombiner.getField(1))
                crafting.sendProgressBarUpdate(this, 1, tileCombiner.getField(1));

            combineTime = tileCombiner.getField(0);
            totalCombineTime = tileCombiner.getField(1);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        tileCombiner.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer player) {
        return tileCombiner.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
        ItemStack itemStack = null;
        Slot slot = inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (fromSlot == 2) {
                if (!mergeItemStack(slotStack, 2 + 1, 2 + 36 + 1, true)) return null;
                slot.onSlotChange(slotStack, itemStack);
            } else if (fromSlot != 0 && fromSlot != 1) {
                if (CombinerRecipes.get().isIngredient(slotStack, 1)) {
                    if (!mergeItemStack(slotStack, 0, 0 + 1, false)) return null;
                } else if (CombinerRecipes.get().isIngredient(slotStack, 2)) {
                    if (!mergeItemStack(slotStack, 1, 1 + 1, false)) return null;
                } else if (fromSlot < 2 + 28) {
                    if (!mergeItemStack(slotStack, 2 + 28, 2 + 37, false)) return null;
                } else if (fromSlot < 2 + 37 && !mergeItemStack(slotStack, 2 + 1, 2 + 28, false)) return null;
            } else if (!mergeItemStack(slotStack, 2 + 1, 2 + 37, false)) return null;

            if (slotStack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (slotStack.stackSize == itemStack.stackSize) return null;

            slot.onPickupFromSlot(player, slotStack);
        }
        return itemStack;
    }
}