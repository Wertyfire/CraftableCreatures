/**
 * File created on 19:06 24.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemCombinedEvent extends Event {
    public final EntityPlayer player;
    public final ItemStack result;

    public ItemCombinedEvent(EntityPlayer player, ItemStack result) {
        this.player = player;
        this.result = result;
    }
}