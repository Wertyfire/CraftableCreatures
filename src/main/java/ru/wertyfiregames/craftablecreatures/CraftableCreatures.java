package ru.wertyfiregames.craftablecreatures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;

import java.io.File;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modStatus + "-" + modVersion + " (" + allVersionsNumber + ")-1.12.2", name = name,
        canBeDeactivated = true)
public class CraftableCreatures
{
//    Version
    public static final String modId = "craftable_creatures";
    protected static final String modVersion = "0.1.0";
    protected static final String majorVersion = "0";
    protected static final String minorVersion = "1";
    protected static final String patch = "0";
    protected static final String allVersionsNumber = "01";
    protected static final String modStatus = "beta";

//    Name
    protected static final String name = "Craftable Creatures";

//    Gui
    private static final int modGui = 0;
    public static final int GUI_SOUL_EXTRACTOR = modGui;


//    Logger
    private static Logger modLogger;

//    Proxy
    private static final String clientSide = "ru.wertyfiregames.craftablecreatures.proxy.ClientProxy";
    private static final String serverSide = "ru.wertyfiregames.craftablecreatures.proxy.CommonProxy";
    @Mod.Instance
    public static CraftableCreatures INSTANCE;
    @SidedProxy(clientSide = clientSide, serverSide = serverSide)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        modLogger = event.getModLog();
        modLogger.debug("CC Logger loaded");
        proxy.preInit(event);
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