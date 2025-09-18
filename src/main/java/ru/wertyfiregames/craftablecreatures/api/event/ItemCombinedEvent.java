/**
 * File created on 19:06 24.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ItemCombinedEvent extends Event {
    public final EntityPlayer player;
    public final ItemStack result;

    public ItemCombinedEvent(EntityPlayer player, ItemStack result) {
        this.player = player;
        this.result = result;
    }
}