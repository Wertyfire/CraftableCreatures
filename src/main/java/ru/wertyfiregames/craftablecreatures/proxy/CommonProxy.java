/**
 * File created on 21:52 07.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.client.gui.inventory.GuiSoulExtractor;
import ru.wertyfiregames.craftablecreatures.inventory.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

import javax.annotation.Nullable;

public class CommonProxy implements IGuiHandler {
    public void preInit(FMLPreInitializationEvent event) {
    }
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(CraftableCreatures.INSTANCE, this);
        CraftableCreatures.getModLogger().debug("CC Gui handler registered");
    }
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR) return new ContainerSoulExtractor(player.inventory,
                (TileEntitySoulExtractor) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == CraftableCreatures.GUI_SOUL_EXTRACTOR) return new GuiSoulExtractor(player.inventory,
                (TileEntitySoulExtractor) world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}