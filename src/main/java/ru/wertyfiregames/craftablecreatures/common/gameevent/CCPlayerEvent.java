package ru.wertyfiregames.craftablecreatures.common.gameevent;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CCPlayerEvent extends Event
{
    public final EntityPlayer player;
    private CCPlayerEvent(EntityPlayer player)
    {
        this.player = player;
    }
    
    public static class SoulExtracted extends CCPlayerEvent
    {
        public final ItemStack extracting;
        public SoulExtracted(EntityPlayer player, ItemStack extracting)
        {
            super(player);
            this.extracting = extracting;
        }
    }
}