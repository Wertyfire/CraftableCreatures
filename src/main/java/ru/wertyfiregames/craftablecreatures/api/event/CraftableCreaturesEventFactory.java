/**
 * File created on 15:13 31.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api.event;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class CraftableCreaturesEventFactory {
    public static void soulExtractedEvent(EntityPlayer player, ItemStack result) {
        SoulExtractedEvent event = new SoulExtractedEvent(player, result);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void itemCombinedEvent(EntityPlayer player, ItemStack result) {
        ItemCombinedEvent event = new ItemCombinedEvent(player, result);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static int getSEFuelBurnTime(ItemStack fuel) {
        SEFuelBurnTimeEvent event = new SEFuelBurnTimeEvent(fuel);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResult() == Result.DEFAULT ? -1 : event.burnTime;
    }
}