package ru.wertyfiregames.craftablecreatures;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import ru.wertyfiregames.craftablecreatures.proxy.CommonProxy;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.*;

@Mod(modid = modId, version = modStatus + "-" + modVersion + "(" + allVersionsNumber + ")-1.7.10", name = name,
        acceptedMinecraftVersions = acceptedMinecraftVersions, guiFactory = guiFactory)
public class CraftableCreatures
{

//    Version
    protected static final String modId = "craftable_creatures";
    protected static final String modVersion = "0.1.3";
    protected static final String acceptedMinecraftVersions = "1.7.10";
    protected static final String majorVersion = "0";
    protected static final String minorVersion = "2";
    protected static final String patch = "0";
    protected static final String allVersionsNumber = "05";
    protected static final String modStatus = "beta";

//    Name
    protected static final String name = "Craftable Creatures";

//    Config
    public static String configDir;

//    Gui
    private static int modGuiIndex = 0;
    public static final int GUI_SOUL_EXTRACTOR = modGuiIndex++;
    public static final String guiFactory = "ru.wertyfiregames.craftablecreatures.client.gui.CCGuiFactory";

//    Proxy
    private static final String clientSide = "ru.wertyfiregames.craftablecreatures.proxy.ClientProxy";
    private static final String serverSide = "ru.wertyfiregames.craftablecreatures.proxy.CommonProxy";
    @Mod.Instance("craftable_creatures")
    public static CraftableCreatures INSTANCE;
    @SidedProxy(clientSide = clientSide, serverSide = serverSide)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

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
}