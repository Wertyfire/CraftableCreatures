package ru.wertyfiregames.craftablecreatures.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.transmutator.inventory.ContainerTransmutator;
import ru.wertyfiregames.craftablecreatures.transmutator.inventory.GuiTransmutator;
import ru.wertyfiregames.craftablecreatures.transmutator.item.ItemTransmutatorInv;

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
            return new ContainerTransmutator(player, player.inventory, new ItemTransmutatorInv(player.getHeldItem()));
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == CraftableCreatures.GUI_TRANSMUTATOR)
        {
            return new GuiTransmutator(new ContainerTransmutator(player, player.inventory, new ItemTransmutatorInv(player.getHeldItem())));
        }

        return null;
    }
}