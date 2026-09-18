/**
 * File created on 13:05 05.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.forge.IGuiHandler;
import net.minecraft.src.mod_CraftableCreatures;

public class CCGuiHandler implements IGuiHandler {
    @Override
    public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == mod_CraftableCreatures.guiSoulExtractor)
            return new ContainerSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getBlockTileEntity(x, y, z));
        else if (ID == mod_CraftableCreatures.guiCombiner)
            return new ContainerCombiner(player.inventory, (TileEntityCombiner) world.getBlockTileEntity(x, y, z));
        return null;
    }
}