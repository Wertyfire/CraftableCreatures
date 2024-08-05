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
import ru.wertyfiregames.craftablecreatures.compat.SoulExtractorRecipes;
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

    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        return null;
        //TODO: do shift clicking
        /*ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotStack = slot.getStack();
            itemStack = slotStack.copy();

            if (slotIndex == 3) {
                if (!this.mergeItemStack(slotStack, 3, 39, true)) return null;
                slot.onSlotChange(slotStack, itemStack);
            } else if (slotIndex == 0 || slotIndex == 1) {
                if (SoulExtractorRecipes.get().getExtractingResult(slotStack) != null) {
                    if (!mergeItemStack(slotStack, 3, 4, false)) return null;
                } else if (TileEntitySoulExtractor.isItemFuel(slotStack)) {
                    if (!mergeItemStack(slotStack, 1, 2, false)) return null;
                }
            } else if (slotIndex == 2) {
                if (TileEntitySoulExtractor.isItemExtractHelper(slotStack)) {
                    if (!mergeItemStack(slotStack, 3, 4, false)) return null;
                } else return null;
            }

            if (slotStack.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();

            if (slotStack.stackSize == itemStack.stackSize) return null;

            slot.onPickupFromSlot(player, slotStack);
        }

        return itemStack;*/
    }
}