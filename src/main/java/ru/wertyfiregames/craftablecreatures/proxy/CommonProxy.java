package ru.wertyfiregames.craftablecreatures.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class CommonProxy implements IGuiHandler
{

    public void preInit(FMLPreInitializationEvent event)
    {

    }
    public void init(FMLInitializationEvent event)
    {

    }
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == CraftableCreatures.GUI_TRANSMUTATOR)
        {
            return null;
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == CraftableCreatures.GUI_TRANSMUTATOR)
        {
            return null;
        }

        return null;
    }
}