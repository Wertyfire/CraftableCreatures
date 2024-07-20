package ru.wertyfiregames.craftablecreatures.proxy;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.common.handler.TradeHandler;
import ru.wertyfiregames.craftablecreatures.common.CCEventListener;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.compat.CCOreDictionary;
import ru.wertyfiregames.craftablecreatures.init.CCRecipes;
import ru.wertyfiregames.craftablecreatures.stats.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.world.CCWorldOreGenerator;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CCConfig());
        MinecraftForge.EVENT_BUS.register(new CCEventListener());
        CraftableCreatures.getModLogger().debug("CC Config loaded");
        CCItems.register();
        CraftableCreatures.getModLogger().debug("CC Items loaded");
        CCBlocks.register();
        CraftableCreatures.getModLogger().debug("CC Blocks loaded");
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
        GameRegistry.registerWorldGenerator(new CCWorldOreGenerator(), 0);
        CraftableCreatures.getModLogger().debug("CC Ore generation loaded");
        CCOreDictionary.register();
        CraftableCreatures.getModLogger().debug("CC Recipes loaded");
        CCRecipes.register();
        CraftableCreatures.getModLogger().debug("CC Ore dictionary loaded");
        super.init(event);
        CraftableCreatures.getModLogger().info("Initialization of Craftable Creatures complete");
    }
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        CraftableCreatures.getModLogger().info("Post initialization of Craftable Creatures complete");
    }
}