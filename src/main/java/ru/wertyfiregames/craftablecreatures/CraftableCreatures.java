package ru.wertyfiregames.craftablecreatures;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.common.CCEventListener;
import ru.wertyfiregames.craftablecreatures.common.CCTradeHandler;
import ru.wertyfiregames.craftablecreatures.compat.CCOreDictionary;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.init.*;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;
import ru.wertyfiregames.craftablecreatures.world.CCWorldOreGenerator;

import java.io.File;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modVersion, name = name,
        guiFactory = guiFactory)
public class CraftableCreatures {
//    Version
    protected static final String modId = "craftable_creatures";
    protected static final String modVersion = "1.0.1";
    protected static final String buildNumber = "02";
    protected static final String modStatus = "r";

//    Name
    protected static final String name = "Craftable Creatures";


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

//    Networking
    public static final SimpleNetworkWrapper NETWORK = new SimpleNetworkWrapper(modId);

//    Instance and metadata
    @Instance(modId)
    public static CraftableCreatures INSTANCE;
    @Metadata(modId)
    public static ModMetadata METADATA;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = event.getModLog();
        modLogger.debug("CC Logger loaded");
        File configFile = new File(event.getModConfigurationDirectory().toString() + "/craftableCreatures.cfg");
        config = new Configuration(configFile);
        CCConfig.load();
        getModLogger().debug("CC Config loaded");
        CCBlocks.register();
        getModLogger().debug("CC Blocks loaded");
        CCItems.register();
        getModLogger().debug("CC Items loaded");
        CCTileEntities.register();
        getModLogger().debug("CC Tile entities loaded");
        CCPackets.register();
        getModLogger().debug("CC Packets loaded");
        CCVersionChecker.check(getVersion());
        CraftableCreatures.getModLogger().info("Pre initialization of Craftable Creatures complete");
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new CCEventListener());
        getModLogger().debug("CC Event listener loaded");
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
        getModLogger().debug("CC Gui handler loaded");
        CCTradeHandler.registerTrades();
        getModLogger().debug("CC Villager trades loaded");
        GameRegistry.registerWorldGenerator(new CCWorldOreGenerator(), 0);
        getModLogger().debug("CC Ore generation loaded");
        CCAchievementList.register();
        getModLogger().debug("CC Achievements loaded");
        CCRecipes.register();
        getModLogger().debug("CC Recipes loaded");
        proxy.registerRenders();
        proxy.registerParticles();
        CCOreDictionary.register();
        getModLogger().debug("CC Ore dictionary loaded");
        CCAPIInit.register();
        CraftableCreatures.getModLogger().info("Initialization of Craftable Creatures complete");
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CraftableCreatures.getModLogger().info("Post initialization of Craftable Creatures complete");
    }

    @EventHandler
    public void missingMappings(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping missing : event.get()) {
            if (!missing.resourceLocation.getResourceDomain().equals(modId)) continue;

            if (missing.resourceLocation.getResourcePath().equals("template"))
                missing.remap(CCItems.BLUEPRINT);
            if (missing.resourceLocation.getResourcePath().equals("spawn_egg_template"))
                missing.remap(CCItems.SPAWN_EGG_BLUEPRINT);
            if (missing.resourceLocation.getResourcePath().equals("soul_element"))
                missing.remap(CCItems.SOUL);
        }
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