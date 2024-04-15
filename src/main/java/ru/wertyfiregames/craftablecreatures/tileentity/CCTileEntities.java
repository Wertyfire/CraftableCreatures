/**
 * File created on 16:43 18.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.tileentity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class CCTileEntities {
    public static void register() {
        GameRegistry.registerTileEntity(TileEntitySoulExtractor.class,
                new ResourceLocation(CraftableCreatures.getModId(), "soul_extractor"));
    }
}