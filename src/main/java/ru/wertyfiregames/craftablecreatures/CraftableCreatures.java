package ru.wertyfiregames.craftablecreatures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.common.CCEventListener;
import ru.wertyfiregames.craftablecreatures.common.CCTradeHandler;
import ru.wertyfiregames.craftablecreatures.compat.CCOreDictionary;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.init.*;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;
import ru.wertyfiregames.craftablecreatures.world.CCWorldOreGenerator;
import ru.wertyfiregames.wertyfirecore.context.InitActions;
import ru.wertyfiregames.wertyfirecore.context.ModContext;

import java.io.File;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modVersion, name = name,
        guiFactory = guiFactory, dependencies = dependencies)
public class CraftableCreatures {
//    Version
    protected static final String modId = "craftable_creatures";
    protected static final String modVersion = "0.7.0";
    protected static final String buildNumber = "14";
    protected static final String modStatus = "rc1";

//    Name
    protected static final String name = "Craftable Creatures";

//    Dependencies
    protected static final String dependencies = "required-after:wertyfirecore@[1.0.2];";

//    Config
    private static Configuration config;

//    Gui
    protected static final String guiFactory = "ru.wertyfiregames.craftablecreatures.config.CCGuiFactory";
    public static final int GUI_SOUL_EXTRACTOR = 0;
    public static final int GUI_COMBINER = 1;
    public static final int GUI_GUIDE_BOOK = 2;
    public static final int GUI_TRANSMUTATOR = 3;

//    Logger
    private static Logger modLogger;

//    Proxy
    protected static final String clientProxy = "ru.wertyfiregames.craftablecreatures.proxy.ClientProxy";
    protected static final String commonProxy = "ru.wertyfiregames.craftablecreatures.proxy.CommonProxy";
    @SidedProxy(clientSide = clientProxy, serverSide = commonProxy)
    public static CommonProxy proxy;

//    Instance and metadata
    @Mod.Instance(modId)
    public static CraftableCreatures INSTANCE;
    @Mod.Metadata(modId)
    public static ModMetadata METADATA;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModContext.setModContext(METADATA);
        modLogger = LogManager.getLogger("Craftable Creatures");
        modLogger.debug("CC Logger loaded");
        File configFile = new File(event.getModConfigurationDirectory().toString() + "/craftableCreatures.cfg");
        config = new Configuration(configFile);
        CCConfig.load();
        getModLogger().debug("CC Config loaded");
        CCItems.register();
        getModLogger().debug("CC Items loaded");
        CCBlocks.register();
        getModLogger().debug("CC Blocks loaded");
        CCTileEntities.register();
        getModLogger().debug("CC Tile entities loaded");
        CCVersionChecker.check(getVersion());
        InitActions.doPreInit(event);
        CraftableCreatures.getModLogger().info("Pre initialization of Craftable Creatures complete");
        ModContext.freeContext();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModContext.setModContext(METADATA);
        CCEventListener eventListener = new CCEventListener();
        FMLCommonHandler.instance().bus().register(eventListener);
        MinecraftForge.EVENT_BUS.register(eventListener);
        getModLogger().debug("CC Event listener loaded");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
        getModLogger().debug("CC Gui handler loaded");
        for (int i = 0; i < 5; i++) {
            VillagerRegistry.instance().registerVillageTradeHandler(i, new CCTradeHandler());
        }
        getModLogger().debug("CC Villager trades loaded");
        GameRegistry.registerWorldGenerator(new CCWorldOreGenerator(), 0);
        getModLogger().debug("CC Ore generation loaded");
        CCAchievementList.register();
        getModLogger().debug("CC Achievements loaded");
        CCRecipes.register();
        getModLogger().debug("CC Recipes loaded");
        proxy.registerParticles();
        CCOreDictionary.register();
        getModLogger().debug("CC Ore dictionary loaded");
        CCChestLoot.register();
        CCAPIInit.register();
        InitActions.doInit(event);
        CraftableCreatures.getModLogger().info("Initialization of Craftable Creatures complete");
        ModContext.freeContext();
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModContext.setModContext(METADATA);
        InitActions.doPostInit(event);
        CraftableCreatures.getModLogger().info("Post initialization of Craftable Creatures complete");
        ModContext.freeContext();
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