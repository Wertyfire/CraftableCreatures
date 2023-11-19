/**
 * File created on 18:21 19.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import ru.wertyfiregames.craftablecreatures.transmutator.render.TransmutatorRender;

public class CCItemRenderers
{
    @SideOnly(Side.CLIENT)
    public static void register()
    {
        MinecraftForgeClient.registerItemRenderer(CCItems.portative_body_changer, new TransmutatorRender());
    }
}