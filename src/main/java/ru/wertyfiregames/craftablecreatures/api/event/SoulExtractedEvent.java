/**
 * File created on 18:31 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SoulExtractedEvent extends Event {
    public final EntityPlayer player;
    public final ItemStack result;

    public SoulExtractedEvent(EntityPlayer player, ItemStack result) {
        this.player = player;
        this.result = result;
    }
}