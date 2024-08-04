/**
 * File created on 15:34 31.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat;

import net.minecraft.item.ItemStack;

public interface ISEFuelHandler {
    int getBurnTime(ItemStack fuel);
}