/**
 * File created on 16:54 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class CCTileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(TileEntitySoulExtractor.class, "soul_extractor");
    }
}