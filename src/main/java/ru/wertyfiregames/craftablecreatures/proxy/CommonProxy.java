/**
 * File created on 16:45 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.InventoryTransmutator;
import ru.wertyfiregames.craftablecreatures.inventory.gui.*;
import ru.wertyfiregames.craftablecreatures.inventory.container.*;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntityCombiner;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class CommonProxy implements IGuiHandler {
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR)
            return new GuiSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getTileEntity(new BlockPos(x, y, z)));
        else if (ID == CraftableCreatures.GUI_COMBINER)
            return new GuiCombiner(player.inventory, (TileEntityCombiner) world.getTileEntity(new BlockPos(x, y, z)));
        else if (ID == CraftableCreatures.GUI_GUIDE_BOOK)
            return new GuiScreenGuideBook(player);
        else if (ID == CraftableCreatures.GUI_TRANSMUTATOR) {
            InventoryTransmutator transmutator = new InventoryTransmutator(player);
            if (player.getHeldItem().hasDisplayName())
                transmutator.setCustomInventoryName(player.getHeldItem().getDisplayName());
            return new GuiTransmutator(player.inventory, transmutator);
        }

        return null;
    }

    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR)
            return new ContainerSoulExtractor(player.inventory, (TileEntitySoulExtractor) world.getTileEntity(new BlockPos(x, y, z)));
        else if (ID == CraftableCreatures.GUI_COMBINER)
            return new ContainerCombiner(player.inventory, (TileEntityCombiner) world.getTileEntity(new BlockPos(x, y, z)));
        else if (ID == CraftableCreatures.GUI_TRANSMUTATOR) {
            InventoryTransmutator transmutator = new InventoryTransmutator(player);
            if (player.getHeldItem().hasDisplayName())
                transmutator.setCustomInventoryName(player.getHeldItem().getDisplayName());
            return new ContainerTransmutator(player.inventory, transmutator);
        }

        return null;
    }

    public void registerRenders() {}
    public void registerParticles() {}
}