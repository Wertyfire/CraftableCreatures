/**
 * File created on 18:31 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SoulExtractedEvent extends Event {
    public final EntityPlayer player;
    public final ItemStack extracted;

    public SoulExtractedEvent(EntityPlayer player, ItemStack extracted) {
        this.player = player;
        this.extracted = extracted;
    }
}