/**
 * File created on 18:58 18.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.transmutator.transformation;

import cpw.mods.fml.common.eventhandler.Cancelable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;

@Cancelable
public class MorphEvent extends PlayerEvent
{
    public final EntityLivingBase prevMorph;

    public final EntityLivingBase curMorph;

    public MorphEvent(EntityPlayer player, EntityLivingBase prevMorph, EntityLivingBase curMorph)
    {
        super(player);
        this.prevMorph = prevMorph;
        this.curMorph = curMorph;
    }
}