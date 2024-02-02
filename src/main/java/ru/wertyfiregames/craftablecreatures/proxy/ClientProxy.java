package ru.wertyfiregames.craftablecreatures.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler;
import ru.wertyfiregames.craftablecreatures.common.handler.TradeHandler;
import ru.wertyfiregames.craftablecreatures.common.listener.CCFMLEventListener;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import ru.wertyfiregames.craftablecreatures.recipe.CCOreDictionary;
import ru.wertyfiregames.craftablecreatures.render.CCRenderers;
import ru.wertyfiregames.craftablecreatures.recipe.CCRecipes;
import ru.wertyfiregames.craftablecreatures.stats.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.world.CCWorldGenVines;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        CraftableCreatures.configDir = event.getModConfigurationDirectory().toString();
        CCConfigHandler.register(CraftableCreatures.configDir);
        FMLCommonHandler.instance().bus().register(new CCConfigHandler());
        FMLCommonHandler.instance().bus().register(new CCFMLEventListener());
        CraftableCreatures.getModLogger().debug("CC Config loaded");

        CCItems.register();
        CraftableCreatures.getModLogger().debug("CC Items loaded");
        CCBlocks.register();
        CraftableCreatures.getModLogger().debug("CC Blocks loaded");
        CCWorldGenVines.register();
        CraftableCreatures.getModLogger().debug("CC Ore generation loaded");
        CCAchievementList.register();
        CraftableCreatures.getModLogger().debug("CC Achievements loaded");

        for (int i = 0; i < 5; i++) {
            VillagerRegistry.instance().registerVillageTradeHandler(i, new TradeHandler());
        }
        CraftableCreatures.getModLogger().debug("CC Villager trades loaded");

        super.preInit(event);
        CraftableCreatures.getModLogger().info("Pre initialization of Craftable Creatures complete");
    }
    public void init(FMLInitializationEvent event) {
        CCRenderers.registerItemRenderers();
        CraftableCreatures.getModLogger().debug("CC Item renderers loaded");
        super.init(event);
        CraftableCreatures.getModLogger().info("Initialization of Craftable Creatures complete");
    }
    public void postInit(FMLPostInitializationEvent event) {
        CCOreDictionary.register();
        CraftableCreatures.getModLogger().debug("CC Recipes loaded");
        CCRecipes.register();
        CraftableCreatures.getModLogger().debug("CC Ore dictionary loaded");
        super.postInit(event);
        CraftableCreatures.getModLogger().info("Post initialization of Craftable Creatures complete");
    }
}