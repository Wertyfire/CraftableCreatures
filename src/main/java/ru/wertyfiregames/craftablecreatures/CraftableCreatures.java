package ru.wertyfiregames.craftablecreatures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraftforge.common.ForgeInternalHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.common.CCEventListener;
import ru.wertyfiregames.craftablecreatures.common.CCTradeHandler;
import ru.wertyfiregames.craftablecreatures.compat.CCOreDictionary;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.init.*;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;
import ru.wertyfiregames.craftablecreatures.stats.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;
import ru.wertyfiregames.craftablecreatures.world.CCWorldOreGenerator;

import java.io.File;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modVersion, name = name,
        guiFactory = guiFactory)
public class CraftableCreatures
{
//    Version
    protected static final String modId = "craftable_creatures";
    protected static final String modVersion = "0.5.0";
    protected static final String buildNumber = "09";
    protected static final String modStatus = "beta";

//    Name
    protected static final String name = "Craftable Creatures";

//    Config
    private static Configuration config;

//    Gui
    protected static final String guiFactory = "ru.wertyfiregames.craftablecreatures.config.CCGuiFactory";
    public static final int GUI_SOUL_EXTRACTOR = 0;
    
    private static Logger modLogger;

    @Mod.Instance("craftable_creatures")
    public static CraftableCreatures INSTANCE;
    @Mod.Metadata
    public static ModMetadata METADATA;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = event.getModLog();
        modLogger.debug("CC Logger loaded");
        File configFile = new File(event.getModConfigurationDirectory().toString() + "/craftableCreatures.cfg");
        config = new Configuration(configFile);
        CCConfig.load();
        CraftableCreatures.getModLogger().debug("CC Config loaded");
        CCItems.register();
        CraftableCreatures.getModLogger().debug("CC Items loaded");
        CCBlocks.register();
        CraftableCreatures.getModLogger().debug("CC Blocks loaded");
        CCTileEntities.register();
        CraftableCreatures.getModLogger().debug("CC Tile entities loaded");
        CCVersionChecker.check(getVersion());
        CraftableCreatures.getModLogger().info("Pre initialization of Craftable Creatures complete");
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        CCEventListener eventListener = new CCEventListener();
        FMLCommonHandler.instance().bus().register(eventListener);
        MinecraftForge.EVENT_BUS.register(eventListener);
        CraftableCreatures.getModLogger().debug("CC Event listener loaded");
        CraftableCreatures.getModLogger().warn("Registered event listeners: {}", ReflectionHelper.getPrivateValue(EventBus.class, MinecraftForge.EVENT_BUS, "listenerOwners").toString());
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
        CraftableCreatures.getModLogger().debug("CC Gui handler loaded");
        for (int i = 0; i < 5; i++) {
            VillagerRegistry.instance().registerVillageTradeHandler(i, new CCTradeHandler());
        }
        CraftableCreatures.getModLogger().debug("CC Villager trades loaded");
        GameRegistry.registerWorldGenerator(new CCWorldOreGenerator(), 0);
        CraftableCreatures.getModLogger().debug("CC Ore generation loaded");
        CCAchievementList.register();
        CraftableCreatures.getModLogger().debug("CC Achievements loaded");
        CCRecipes.register();
        CraftableCreatures.getModLogger().debug("CC Recipes loaded");
        CCOreDictionary.register();
        CraftableCreatures.getModLogger().debug("CC Ore dictionary loaded");
        CraftableCreatures.getModLogger().info("Initialization of Craftable Creatures complete");
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CraftableCreatures.getModLogger().info("Post initialization of Craftable Creatures complete");
    }

//    Getters
    public static String getVersion() {
        return modVersion;
    }
    public static String getModId() {
        return modId;
    }
    public static String getName() {
        return name;
    }
    public static int getMajorVersion() {
        String[] version = getVersion().split("-");
        String[] parts = version[0].split("\\.");
        return Integer.parseInt(parts[0]);
    }
    public static int getMinorVersion() {
        String[] version = getVersion().split("-");
        String[] parts = version[0].split("\\.");
        return Integer.parseInt(parts[1]);
    }
    public static int getPatchVersion() {
        String[] version = getVersion().split("-");
        String[] parts = version[0].split("\\.");
        return Integer.parseInt(parts[2]);
    }
    public static String getBuildNum() {
        return buildNumber;
    }
    public static int getBuildNumInt() {
        return Integer.parseInt(buildNumber);
    }
    public static String getModStatus() {
        return modStatus;
    }

    public static Logger getModLogger() {
        return modLogger;
    }
    public static Configuration getConfig() {
        return config;
    }
}