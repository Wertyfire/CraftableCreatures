/**
 * File created on 19:06 27.08.2024 by Wertyfire
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
import ru.wertyfiregames.craftablecreatures.inventory.slot.SlotCombiner;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntityCombiner;

public class ContainerCombiner extends Container {
    private final TileEntityCombiner tileCombiner;
    private int lastCombineTime;

    public ContainerCombiner(InventoryPlayer inv, TileEntityCombiner combiner) {
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

    public void addCraftingToCrafters(ICrafting crafting) {
        super.addCraftingToCrafters(crafting);
        crafting.sendProgressBarUpdate(this, 0, tileCombiner.combineTime);
    }

    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (lastCombineTime != tileCombiner.combineTime)
                icrafting.sendProgressBarUpdate(this, 0, tileCombiner.combineTime);
        }

        lastCombineTime = tileCombiner.combineTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        if (id == 0) tileCombiner.combineTime = value;
    }

    public boolean canInteractWith(EntityPlayer player) {
        return tileCombiner.isUseableByPlayer(player);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(fromSlot);

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