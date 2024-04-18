package ru.wertyfiregames.craftablecreatures;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;
import ru.wertyfiregames.craftablecreatures.version.CCVersion;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modVersion, name = name,
        guiFactory = guiFactory, canBeDeactivated = true)
public class CraftableCreatures
{
//    Version
    protected static final String modId = "craftable_creatures";
    protected static final String modVersion = "0.2.0";
    protected static final String majorVersion = "0";
    protected static final String minorVersion = "2";
    protected static final String patch = "0";
    protected static final String allVersionsNumber = "05";
    protected static final String modStatus = "05";

//    Name
    protected static final String name = "Craftable Creatures";

//    Config
    public static String configDir;

//    Gui
    public static final String guiFactory = "ru.wertyfiregames.craftablecreatures.client.gui.CCGuiFactory";
    
    private static Logger modLogger;

//    Proxy
    private static final String clientSide = "ru.wertyfiregames.craftablecreatures.proxy.ClientProxy";
    private static final String serverSide = "ru.wertyfiregames.craftablecreatures.proxy.CommonProxy";
    @Mod.Instance("craftable_creatures")
    public static CraftableCreatures INSTANCE;
    @SidedProxy(clientSide = clientSide, serverSide = serverSide)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = event.getModLog();
        modLogger.debug("CC Logger loaded");
        proxy.preInit(event);
        CCVersion.startVersionCheck();
        getModLogger().debug("CC Version checking...");
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
    public static String getMajorVersion() {
        return majorVersion;
    }
    public static String getMinorVersion() {
        return minorVersion;
    }
    public static String getPatchVersion() {
        return patch;
    }
    public static String getModStatus() {
        return modStatus;
    }

    public static Logger getModLogger() {
        return modLogger;
    }
}