/**
 * File created on 15:08 31.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.Event.HasResult;
import net.minecraft.item.ItemStack;

@HasResult
public class SEFuelBurnTimeEvent extends Event {
    private final ItemStack fuel;
    public int burnTime;

    public SEFuelBurnTimeEvent(ItemStack fuel) {
        this.fuel = fuel;
    }

    public ItemStack getFuel() {
        return fuel;
    }
}