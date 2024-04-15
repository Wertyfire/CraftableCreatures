/**
 * File created on 21:55 07.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.recipe.CCOreDictionary;
import ru.wertyfiregames.craftablecreatures.recipe.CCRecipes;
import ru.wertyfiregames.craftablecreatures.tileentity.CCTileEntities;
import ru.wertyfiregames.craftablecreatures.world.CCWorldGenVines;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        CCBlocks.register();
        CraftableCreatures.getModLogger().debug("CC Blocks loaded");
        CCTileEntities.register();
        CraftableCreatures.getModLogger().debug("CC Tile entities loaded");
        CCWorldGenVines.register();
        CraftableCreatures.getModLogger().debug("CC Ore generation loaded");
        super.preInit(event);
        CraftableCreatures.getModLogger().info("Pre initialization of Craftable Creatures complete");
    }
    @Override
    public void init(FMLInitializationEvent event) {
        CCBlocks.registerRender();
        CraftableCreatures.getModLogger().debug("CC Block item renderer loaded");
        super.init(event);
        CraftableCreatures.getModLogger().info("Initialization of Craftable Creatures complete");
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        CCOreDictionary.register();
        CraftableCreatures.getModLogger().debug("CC Ore dictionary loaded");
        CCRecipes.register();
        CraftableCreatures.getModLogger().debug("CC Recipes loaded");
        super.postInit(event);
        CraftableCreatures.getModLogger().info("Post initialization of Craftable Creatures complete");
    }
}