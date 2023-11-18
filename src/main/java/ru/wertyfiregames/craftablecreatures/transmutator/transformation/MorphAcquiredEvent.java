/**
 * File created on 18:56 18.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.transmutator.transformation;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class MorphAcquiredEvent extends PlayerEvent
{
    public final EntityLivingBase morphParent;

    public MorphAcquiredEvent(EntityPlayer player, EntityLivingBase acquired)
    {
        super(player);
        morphParent = acquired;
    }
}