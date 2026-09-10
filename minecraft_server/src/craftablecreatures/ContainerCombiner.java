/**
 * File created on 17:35 29.04.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.*;

public class ContainerCombiner extends Container {
    private final TileEntityCombiner combiner;
    private int lastCombineTime;

    public ContainerCombiner(InventoryPlayer inv, TileEntityCombiner tileCombiner) {
        combiner = tileCombiner;

        addSlot(new Slot(combiner, 0, 49, 15));
        addSlot(new Slot(combiner, 1, 111, 15));
        addSlot(new SlotCombiner(inv.player, combiner, 2, 80, 55));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void updateCraftingResults() {
        super.updateCraftingResults();

        for (Object crafter : crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (lastCombineTime != combiner.combineTime)
                icrafting.updateCraftingInventoryInfo(this, 0, combiner.combineTime);
        }

        lastCombineTime = combiner.combineTime;
    }

    @Override
    public void onCraftGuiOpened(ICrafting icrafting) {
        super.onCraftGuiOpened(icrafting);
        icrafting.updateCraftingInventoryInfo(this, 0, combiner.combineTime);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return combiner.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(int fromSlot) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (fromSlot == 2) {
                if (!mergeItemStack(slotStack, 2 + 1, 2 + 36 + 1, true)) return null;
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

            slot.onPickupFromSlot(slotStack);
        }
        return itemStack;
    }
}