package ru.wertyfiregames.craftablecreatures;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;

import java.io.File;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modVersion, name = name,
        guiFactory = guiFactory)
public class CraftableCreatures
{
//    Version
    protected static final String modId = "craftable_creatures";
    protected static final String modVersion = "0.4.0";
    protected static final String allVersionsNumber = "07";
    protected static final String modStatus = "beta";

//    Name
    protected static final String name = "Craftable Creatures";

//    Config
    public static Configuration config;

//    Gui
    public static final String guiFactory = "ru.wertyfiregames.craftablecreatures.config.CCGuiFactory";
    
    private static Logger modLogger;

//    Proxy
    private static final String clientSide = "ru.wertyfiregames.craftablecreatures.proxy.ClientProxy";
    private static final String serverSide = "ru.wertyfiregames.craftablecreatures.proxy.CommonProxy";

    @Mod.Instance("craftable_creatures")
    public static CraftableCreatures INSTANCE;
    @Mod.Metadata
    public static ModMetadata METADATA;
    @SidedProxy(clientSide = clientSide, serverSide = serverSide)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = event.getModLog();
        modLogger.debug("CC Logger loaded");
        File configFile = new File(event.getModConfigurationDirectory().toString() + "/craftableCreatures.cfg");
        config = new Configuration(configFile);
        CCConfig.load();
        proxy.preInit(event);
        CCVersionChecker.check(getVersion());
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
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