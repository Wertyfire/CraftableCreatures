/**
 * File created on 15:51 18.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class SlotSoulExtractorFuel extends Slot {
    public SlotSoulExtractorFuel(IInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack) {
        return TileEntitySoulExtractor.isItemFuel(stack);
    }

    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }
}