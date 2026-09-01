/**
 * File created on 15:44 22.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.*;

public class ContainerSoulExtractor extends Container {
    private final TileEntitySoulExtractor soulExtractor;
    private int lastExtractTime, lastFuelWorkTime, lastSoulExtractTime;

    public ContainerSoulExtractor(InventoryPlayer inv, TileEntitySoulExtractor tileSoulExtractor) {
        soulExtractor = tileSoulExtractor;

        addSlot(new Slot(soulExtractor, 0, 41, 17));
        addSlot(new Slot(soulExtractor, 1, 41, 53));
        addSlot(new Slot(soulExtractor, 2, 78, 17));
        addSlot(new SlotSoulExtractor(inv.player, soulExtractor, 3, 116, 35));

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

            if (lastExtractTime != soulExtractor.extractTime)
                icrafting.updateCraftingInventoryInfo(this, 0, soulExtractor.extractTime);

            if (lastFuelWorkTime != soulExtractor.fuelWorkTime)
                icrafting.updateCraftingInventoryInfo(this, 1, soulExtractor.fuelWorkTime);

            if (lastSoulExtractTime != soulExtractor.currentFuelWorkTime)
                icrafting.updateCraftingInventoryInfo(this, 2, soulExtractor.currentFuelWorkTime);
        }

        lastExtractTime = soulExtractor.extractTime;
        lastFuelWorkTime = soulExtractor.fuelWorkTime;
        lastSoulExtractTime = soulExtractor.currentFuelWorkTime;
    }

    @Override
    public void onCraftGuiOpened(ICrafting icrafting) {
        super.onCraftGuiOpened(icrafting);
        icrafting.updateCraftingInventoryInfo(this, 0, soulExtractor.extractTime);
        icrafting.updateCraftingInventoryInfo(this, 1, soulExtractor.fuelWorkTime);
        icrafting.updateCraftingInventoryInfo(this, 2, soulExtractor.currentFuelWorkTime);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return soulExtractor.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(int fromSlot) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (fromSlot == 3) {
                if (!mergeItemStack(slotStack, 3 + 1, 3 + 36 + 1, true)) return null;
            } else if (fromSlot != 0 && fromSlot != 1 && fromSlot != 2) {
                if (SoulExtractorRecipes.get().getExtractingResult(slotStack) != null) {
                    if (!mergeItemStack(slotStack, 0, 0 + 1, false)) return null;
                } else if (SoulExtractorRecipes.get().isItemExtractHelper(slotStack)) {
                    if (!mergeItemStack(slotStack, 2, 2 + 1, false)) return null;
                } else if (TileEntitySoulExtractor.isItemFuel(slotStack)) {
                    if (!mergeItemStack(slotStack, 1, 1 + 1, false)) return null;
                } else if (fromSlot < 3 + 28) {
                    if (!mergeItemStack(slotStack, 3 + 28, 3 + 37, false)) return null;
                } else if (fromSlot < 3 + 37 && !mergeItemStack(slotStack, 3 + 1, 3 + 28, false)) return null;
            } else if (!mergeItemStack(slotStack, 3 + 1, 3 + 37, false)) return null;

            if (slotStack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (slotStack.stackSize == itemStack.stackSize) return null;

            slot.onPickupFromSlot(slotStack);
        }
        return itemStack;
    }
}