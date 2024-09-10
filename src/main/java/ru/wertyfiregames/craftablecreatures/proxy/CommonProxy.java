/**
 * File created on 16:45 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.gui.*;
import ru.wertyfiregames.craftablecreatures.inventory.container.*;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntityCombiner;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class CommonProxy implements IGuiHandler {
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR)
            return new GuiSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getTileEntity(x, y, z));
        else if (ID == CraftableCreatures.GUI_COMBINER)
            return new GuiCombiner(player.inventory, (TileEntityCombiner) world.getTileEntity(x, y, z));
        else if (ID == CraftableCreatures.GUI_GUIDE_BOOK)
            return new GuiScreenGuideBook(player);

        return null;
    }

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR)
            return new ContainerSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getTileEntity(x, y, z));
        else if (ID == CraftableCreatures.GUI_COMBINER)
            return new ContainerCombiner(player.inventory, (TileEntityCombiner) world.getTileEntity(x, y, z));

        return null;
    }

    public void registerParticles() {}
}