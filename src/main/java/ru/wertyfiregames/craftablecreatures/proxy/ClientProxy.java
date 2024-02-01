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
        FMLLog.info("CC Config loaded");

        CCItems.register();
        FMLLog.info("CC Items loaded");
        CCBlocks.register();
        FMLLog.info("CC Blocks loaded");
        CCWorldGenVines.register();
        FMLLog.info("CC Ore generation loaded");
        CCAchievementList.register();
        FMLLog.info("CC Achievements loaded");

        for (int i = 0; i < 5; i++) {
            VillagerRegistry.instance().registerVillageTradeHandler(i, new TradeHandler());
        }
        FMLLog.info("CC Villager trades loaded");

        super.preInit(event);
        FMLLog.info("Pre initialization of Craftable Creatures complete");
    }
    public void init(FMLInitializationEvent event) {
        CCRenderers.registerItemRenderers();
        FMLLog.info("CC Item renderers loaded");
        super.init(event);
        FMLLog.info("Initialization of Craftable Creatures complete");
    }
    public void postInit(FMLPostInitializationEvent event) {
        CCOreDictionary.register();
        FMLLog.info("CC Recipes loaded");
        CCRecipes.register();
        FMLLog.info("CC Ore dictionary loaded");
        super.postInit(event);
        FMLLog.info("Post initialization of Craftable Creatures complete");
    }
}