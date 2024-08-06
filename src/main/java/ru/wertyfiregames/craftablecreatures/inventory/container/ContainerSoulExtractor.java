/**
 * File created on 12:44 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.init.SoulExtractorRecipes;
import ru.wertyfiregames.craftablecreatures.inventory.slot.SlotSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class ContainerSoulExtractor extends Container {
    private final TileEntitySoulExtractor tileSoulExtractor;
    private int lastExtractTime, lastFuelWorkTime, lastSoulExtractTime;

    public ContainerSoulExtractor(InventoryPlayer inv, TileEntitySoulExtractor soulExtractor) {
        tileSoulExtractor = soulExtractor;

        addSlotToContainer(new Slot(soulExtractor, 0, 41, 17));
        addSlotToContainer(new Slot(soulExtractor, 1, 41, 53));
        addSlotToContainer(new Slot(soulExtractor, 2, 78, 17));
        addSlotToContainer(new SlotSoulExtractor(inv.player, soulExtractor, 3, 116, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
        }
    }

    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tileSoulExtractor.soulExtractorExtractTime);
        crafting.sendProgressBarUpdate(this, 1, tileSoulExtractor.soulExtractorFuelWorkTime);
        crafting.sendProgressBarUpdate(this, 2, tileSoulExtractor.currentSoulExtractTime);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (lastExtractTime != tileSoulExtractor.soulExtractorExtractTime)
                icrafting.sendProgressBarUpdate(this, 0, tileSoulExtractor.soulExtractorExtractTime);

            if (lastFuelWorkTime != tileSoulExtractor.soulExtractorFuelWorkTime)
                icrafting.sendProgressBarUpdate(this, 1, tileSoulExtractor.soulExtractorFuelWorkTime);

            if (lastSoulExtractTime != tileSoulExtractor.currentSoulExtractTime)
                icrafting.sendProgressBarUpdate(this, 2, tileSoulExtractor.currentSoulExtractTime);
        }

        lastExtractTime = tileSoulExtractor.soulExtractorExtractTime;
        lastFuelWorkTime = tileSoulExtractor.soulExtractorFuelWorkTime;
        lastSoulExtractTime = tileSoulExtractor.currentSoulExtractTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if (id == 0) tileSoulExtractor.soulExtractorExtractTime = value;
        if (id == 1) tileSoulExtractor.soulExtractorFuelWorkTime = value;
        if (id == 2) tileSoulExtractor.currentSoulExtractTime = value;
    }

    public boolean canInteractWith(EntityPlayer player) {
        return tileSoulExtractor.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (fromSlot == 3) {
                if (!this.mergeItemStack(slotStack, 3 + 1, 3 + 36 + 1, true)) return null;
                slot.onSlotChange(slotStack, itemStack);
            } else if (fromSlot != 1 && fromSlot != 0 && fromSlot != 2) {
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

            slot.onPickupFromSlot(player, slotStack);
        }
        return itemStack;
    }
}