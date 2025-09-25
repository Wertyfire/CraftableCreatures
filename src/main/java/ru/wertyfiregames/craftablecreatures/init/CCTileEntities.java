/**
 * File created on 16:54 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.init;

import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntityCombiner;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class CCTileEntities {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntitySoulExtractor.class, CraftableCreatures.getModId() + ":" + "soul_extractor");
        GameRegistry.registerTileEntity(TileEntityCombiner.class, CraftableCreatures.getModId() + ":" + "combiner");
    }
}