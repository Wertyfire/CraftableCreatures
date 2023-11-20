/**
 * File created on 18:21 19.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import ru.wertyfiregames.craftablecreatures.transmutator.render.TransmutatorRender;

public class CCRenderers
{
    @SideOnly(Side.CLIENT)
    public static void registerItemRenderers()
    {
        MinecraftForgeClient.registerItemRenderer(CCItems.portative_body_changer, new TransmutatorRender());
    }
}