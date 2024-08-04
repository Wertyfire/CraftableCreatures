/**
 * File created on 16:45 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiSoulExtractor;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class CommonProxy implements IGuiHandler {
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR)
            return new GuiSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getTileEntity(x, y, z));
        return null;
    }

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR)
            return new ContainerSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getTileEntity(x, y, z));

        return null;
    }
}