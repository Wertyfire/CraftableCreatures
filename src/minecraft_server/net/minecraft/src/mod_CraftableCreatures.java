/**
 * File created on 16:37 04.09.2026 by Wertyfire
 */

package net.minecraft.src;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.forge.*;
import ru.wertyfiregames.craftablecreatures.BlockOre;
import ru.wertyfiregames.craftablecreatures.*;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;
import ru.wertyfiregames.craftablecreatures.api.ICraftableCreaturesAddon;
import ru.wertyfiregames.craftablecreatures.lib.SimpleVersion;

import java.io.File;
import java.util.*;

import static net.minecraft.src.mod_CraftableCreatures.CraftableCreaturesIDs.*;

public class mod_CraftableCreatures extends NetworkMod {
    public static final String VERSION = "1.0.0";
    public static final String BUILD = "01";
    public static final String MATCHING_API_VERSION = "2.0";

    public static mod_CraftableCreatures instance;

    public static Block bluestoneBlock;
    public static Block bluestoneOre;
    public static Block soulExtractor;
    public static Block soulExtractorLit;
    public static Block combiner;
    public static Block combinerLit;

    public static Item bluestone;
    public static Item template;
    public static Item spawnEggTemplate;
    public static Item ocelotTail;
    public static Item soulElement;
    public static Item guideBook;

    public static Achievement thanksForDownload;
    public static Achievement bluedustCollector;
    public static Achievement templateManager;
    public static Achievement mobSpawner;
    public static Achievement soulExtractorAch;
    public static Achievement extractSoul;
    public static Achievement combinerAch;
    public static Achievement combineItem;

    public static final int guiSoulExtractor = 0;
    public static final int guiCombiner = 1;
    public static final int guiGuideBook = 2;

    public mod_CraftableCreatures() {
        instance = this;
        SimpleVersion current = new SimpleVersion(CraftableCreaturesRegistry.apiVersion());
        SimpleVersion matching = new SimpleVersion(MATCHING_API_VERSION);
        if (current.compareTo(matching) != 0)
            throw new RuntimeException("Craftable Creatures: required version of API not present. Probably some addon included API with its code!");
        CraftableCreaturesRegistry.setRegistrar(this, new CCRegistrarImpl());
    }

    @Override
    public void load() {
        CraftableCreaturesIDs.init(createConfig());
        CCVersionChecker.check(VERSION);

        loadBlocks();
        loadItems();
        registerTileEntities();
        loadAchievements();
        loadRecipes();
        loadChestLoots();
        initializeApi();

        MinecraftForge.setGuiHandler(this, new CCGuiHandler());

        ModLoader.setInGameHook(this, true, false);
    }

    @Override
    public void modsLoaded() {
        handleAddons();
    }

    @Override
    public boolean onTickInGame(MinecraftServer game) {
        List<EntityOcelot> deadOcelots = new ArrayList<>();
        for (World world : DimensionManager.getWorlds()) {
            for (Entity entity : (List<Entity>) world.loadedEntityList) {
                if (entity instanceof EntityOcelot && ((EntityOcelot) entity).health <= 0 && !entity.getEntityData().getBoolean("CraftableCreatures_DeathProcessed")) {
                    deadOcelots.add((EntityOcelot) entity);
                    entity.getEntityData().setBoolean("CraftableCreatures_DeathProcessed", true);
                }
            }
            for (EntityOcelot ocelot : deadOcelots)
                ocelot.worldObj.spawnEntityInWorld(new EntityItem(ocelot.worldObj, ocelot.posX, ocelot.posY, ocelot.posZ, new ItemStack(ocelotTail, 1)));
        }

        String[] players = game.getPlayerNamesAsList();
        for (String playerName : players) {
            EntityPlayerMP player = game.configManager.getPlayerEntity(playerName);

            if (player != null) {
                for (ItemStack item : player.inventory.mainInventory) {
                    checkForAchievements(player, item);
                }

                if (player.health <= 0 && !player.worldObj.isRemote) {
                    if (!player.getEntityData().getBoolean("CraftableCreatures_DeathProcessed")) {
                        player.getEntityData().setBoolean("CraftableCreatures_DeathProcessed", true);

                        player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(soulElement, 1, 0)));
                    }
                } else player.getEntityData().setBoolean("CraftableCreatures_DeathProcessed", false);
            }
        }

        return true;
    }

    private static void checkForAchievements(EntityPlayer player, ItemStack item) {
        if (item == null) return;

        if (item.itemID == bluestone.shiftedIndex) {
            player.triggerAchievement(bluedustCollector);
            return;
        }
        if (item.itemID == template.shiftedIndex) {
            player.triggerAchievement(templateManager);
            return;
        }
        if (item.itemID == spawnEggTemplate.shiftedIndex) {
            player.triggerAchievement(mobSpawner);
            return;
        }
        if (item.itemID == soulExtractor.blockID) {
            player.triggerAchievement(soulExtractorAch);
            return;
        }
        if (item.itemID == combiner.blockID) {
            player.triggerAchievement(combinerAch);
            return;
        }
    }

    private static void loadBlocks() {
        bluestoneBlock = createBlock(new BlockDefault(bluestoneBlockID, Material.iron, 3f, 5f, Block.soundMetalFootstep)
                .setBlockName("bluestoneBlock"), "pickaxe", 2);
        bluestoneOre = createBlock(new BlockOre(bluestoneOreID, 3f, 5f)
                .setBlockName("bluestoneOre"), "pickaxe", 2);
        soulExtractor = createBlock(new BlockSoulExtractor(soulExtractorID, false), "pickaxe", 1);
        soulExtractorLit = createBlock(new BlockSoulExtractor(litSoulExtractorID, true), "pickaxe", 1);
        combiner = createBlock(new BlockCombiner(combinerID, false), "pickaxe", 1);
        combinerLit = createBlock(new BlockCombiner(litCombinerID, true), "pickaxe", 1);
    }

    private static void loadItems() {
        bluestone = new ItemDefault(bluestoneID).setItemName("bluestone");
        template = new ItemDefault(templateID).setItemName("template");
        spawnEggTemplate = new ItemDefault(spawnEggTemplateID).setItemName("spawnEggTemplate");
        ocelotTail = new ItemDefault(ocelotTailID).setItemName("ocelotTail");
        guideBook = new ItemGuideBook(guideBookID).setItemName("craftableCreaturesGuideBook");
        soulElement = new ItemSoulElement().setItemName("soul");
    }

    private static void registerTileEntities() {
        ModLoader.registerTileEntity(TileEntitySoulExtractor.class, "soul_extractor");
        ModLoader.registerTileEntity(TileEntityCombiner.class, "combiner");
    }

    private static void loadAchievements() {
        thanksForDownload = new Achievement(thanksForDownloadAchID, "craftableCreatures.thanksForDownload",
                2, -1, guideBook, null).registerAchievement();
        bluedustCollector = new Achievement(bluestoneCollectorID, "craftableCreatures.bluedustCollector",
                0, 0, bluestone, thanksForDownload).registerAchievement();
        templateManager = new Achievement(templateManagerID, "craftableCreatures.templateManager",
                1, 2, template, bluedustCollector).registerAchievement();
        mobSpawner = new Achievement(mobSpawnerID, "craftableCreatures.mobSpawner",
                2, 1, spawnEggTemplate, templateManager).registerAchievement();
        soulExtractorAch = new Achievement(soulExtractorAchID, "craftableCreatures.soulExtractor",
                0, -2, soulExtractorLit, bluedustCollector).registerAchievement();
        extractSoul = new Achievement(extractSoulID, "craftableCreatures.extractSoul",
                0, -4, soulExtractor, soulExtractorAch).registerAchievement();
        combinerAch = new Achievement(combinerAchID, "craftableCreatures.combiner",
                -2, 0, combinerLit, bluedustCollector).registerAchievement();
        combineItem = new Achievement(combineItemsID, "craftableCreatures.combineItem",
                -4, 0, combiner, combinerAch).registerAchievement();

        MinecraftForge.registerAchievementPage(new AchievementPage(
                "Craftable Creatures", thanksForDownload, bluedustCollector, templateManager, mobSpawner, soulExtractorAch, extractSoul, combinerAch, combineItem));
    }

    private static void loadRecipes() {
        //Shapeless recipes
        ModLoader.addShapelessRecipe(new ItemStack(spawnEggTemplate), new ItemStack(Item.egg), new ItemStack(template));
        ModLoader.addShapelessRecipe(new ItemStack(bluestone, 9), new ItemStack(bluestoneBlock));

        //Shaped recipes
        ModLoader.addRecipe(new ItemStack(template),
                "SR", "RS", 'R', bluestone, 'S', Item.paper);
        ModLoader.addRecipe(new ItemStack(bluestoneBlock),
                "SSS", "SSS", "SSS", 'S', bluestone);
        ModLoader.addRecipe(new ItemStack(guideBook),
                " S ", "SUS", " S ", 'S', soulElement, 'U', Item.book);
        ModLoader.addRecipe(new ItemStack(soulExtractor),
                "SSS", "SUS", "SAS", 'S', Block.cobblestone, 'U', bluestoneBlock, 'A', Block.stoneOvenIdle);
        ModLoader.addRecipe(new ItemStack(combiner),
                "SSS", "SUS", "SAS", 'S', Block.cobblestone, 'U', template, 'A', Block.torchRedstoneActive);
        ModLoader.addRecipe(new ItemStack(Block.mobSpawner),
                "SSS", "SUS", "SSS", 'S', Block.fenceIron, 'U', soulElement);

        //Smelting recipes
        ModLoader.addSmelting(bluestoneOre.blockID, new ItemStack(bluestone));
    }

    private static void loadChestLoots() {
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 0), 1f, 1, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 2), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 3), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 4), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 5), 0.8f, 1, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 8), 0.5f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 12), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 14), 0.5f, 0, 2);
    }

    private static void initializeApi() {
        CraftableCreaturesRegistry.registerItemAsSoul(soulElement);
    }

    @Override
    public void generateSurface(World world, Random random, int chunkX, int chunkZ) {
        addOreSpawn(bluestoneOreID, world, random, chunkX, chunkZ, 16, 16, 5, 7, 15, 45);
    }

    private void addOreSpawn(int block, World world, Random random, int chunkXPos, int chunkZPos, int maxX, int maxZ, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = chunkXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = chunkZPos + random.nextInt(maxZ);
            new WorldGenMinable(block, maxVeinSize).generate(world, random, posX, posY, posZ);
        }
    }

    private static void handleAddons() {
        Set<ICraftableCreaturesAddon> addons = CraftableCreaturesRegistry.getAddons();
        if (addons.isEmpty()) {
            info("No addons found");
            return;
        }

        info("Found %s addon(-s)", addons.size());

        Set<String> byName = new HashSet<>(addons.size());
        for (ICraftableCreaturesAddon addon : addons)
            byName.add(addon.getName().toLowerCase());
        int erred = 0;

        for (ICraftableCreaturesAddon addon : addons) {
            StringBuilder missingDependenciesBuilder = new StringBuilder();
            for (String dependency : addon.getDependencies())
                if (!byName.contains(dependency.toLowerCase())) missingDependenciesBuilder.append("'").append(dependency).append("', ");
            String missingDependencies = missingDependenciesBuilder.toString();
            if (!missingDependencies.isEmpty()) {
                info("Addon '%s', version %s missing following dependencies: %s, skipping...", addon.getName(), addon.getVersion(), missingDependencies.substring(0, missingDependencies.lastIndexOf(",")));
                erred++;
                continue;
            }
            try {
                boolean success = addon.loadCompatibility();
                if (success) info("Successfully loaded addon '%s', version %s", addon.getName(), addon.getVersion());
                else {
                    info("Addon '%s', version %s was not loaded", addon.getName(), addon.getVersion());
                    erred++;
                }
            } catch (Exception e) {
                err("Failed to load addon '%s', version %s. Message: %s", addon.getName(), addon.getVersion(), e.getMessage());
                erred++;
            }
        }

        info("Successfully loaded %s/%s addons", addons.size() - erred, addons.size());
    }

    private static Configuration createConfig() {
        File configDir = new File(".", "config");
        if (!configDir.exists()) configDir.mkdirs();

        return new Configuration(new File(configDir, "craftableCreatures.cfg"));
    }

    private static Block createBlock(Block block, String tool, int level) {
        return createBlock(block, ItemBlock.class, tool, level);
    }

    private static Block createBlock(Block block, Class<? extends ItemBlock> itemBlockClass, String tool, int level) {
        ModLoader.registerBlock(block, itemBlockClass);

        if (tool != null)
            MinecraftForge.setBlockHarvestLevel(block, tool, level);

        return block;
    }

    public static void info(String info) {
        System.out.println("[Craftable Creatures]: " + info);
    }
    public static void info(String info, Object... format) {
        info(String.format(info, format));
    }
    public static void err(String err) {
        System.err.println("[Craftable Creatures]: " + err);
    }
    public static void err(String err, Object... format) {
        err(String.format(err, format));
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    public String getBuild() {
        return BUILD;
    }

    public String getRequiredApiVersion() {
        return MATCHING_API_VERSION;
    }

    public static final class CraftableCreaturesIDs {
        public static boolean checkForUpdates;

        public static int bluestoneBlockID;
        public static int bluestoneOreID;
        public static int soulExtractorID;
        public static int litSoulExtractorID;
        public static int combinerID;
        public static int litCombinerID;

        public static int bluestoneID;
        public static int templateID;
        public static int spawnEggTemplateID;
        public static int ocelotTailID;
        public static int soulElementID;
        public static int guideBookID;

        public static int thanksForDownloadAchID;
        public static int bluestoneCollectorID;
        public static int templateManagerID;
        public static int mobSpawnerID;
        public static int soulExtractorAchID;
        public static int extractSoulID;
        public static int combinerAchID;
        public static int combineItemsID;

        public static void init(Configuration config) {
            try {
                config.load();

                checkForUpdates = b(config.getOrCreateBooleanProperty("checkForUpdates", Configuration.CATEGORY_GENERAL, true));

                bluestoneBlockID = i(config.getOrCreateBlockIdProperty("bluestoneBlock", 210));
                bluestoneOreID = i(config.getOrCreateBlockIdProperty("bluestoneOre", 211));
                soulExtractorID = i(config.getOrCreateBlockIdProperty("soulExtractor", 212));
                litSoulExtractorID = i(config.getOrCreateBlockIdProperty("litSoulExtractor", 213));
                combinerID = i(config.getOrCreateBlockIdProperty("combinerID", 214));
                litCombinerID = i(config.getOrCreateBlockIdProperty("litCombiner", 215));

                bluestoneID = i(config.getOrCreateIntProperty("bluestone", Configuration.CATEGORY_ITEM, 800));
                templateID = i(config.getOrCreateIntProperty("template", Configuration.CATEGORY_ITEM, 801));
                spawnEggTemplateID = i(config.getOrCreateIntProperty("spawnEggTemplate", Configuration.CATEGORY_ITEM, 802));
                soulElementID = i(config.getOrCreateIntProperty("soulElement", Configuration.CATEGORY_ITEM, 803));
                guideBookID = i(config.getOrCreateIntProperty("guideBook", Configuration.CATEGORY_ITEM, 804));
                ocelotTailID = i(config.getOrCreateIntProperty("ocelotTail", Configuration.CATEGORY_ITEM, 805));

                thanksForDownloadAchID = i(config.getOrCreateIntProperty("thanksForDownloadAch", Configuration.CATEGORY_GENERAL, 210));
                bluestoneCollectorID = i(config.getOrCreateIntProperty("bluestoneCollectorAch", Configuration.CATEGORY_GENERAL, 211));
                templateManagerID = i(config.getOrCreateIntProperty("templateManagerAch", Configuration.CATEGORY_GENERAL, 212));
                mobSpawnerID = i(config.getOrCreateIntProperty("mobSpawnerAch", Configuration.CATEGORY_GENERAL, 213));
                soulExtractorAchID = i(config.getOrCreateIntProperty("soulExtractorAch", Configuration.CATEGORY_GENERAL, 214));
                extractSoulID = i(config.getOrCreateIntProperty("extractSoulAch", Configuration.CATEGORY_GENERAL, 215));
                combinerAchID = i(config.getOrCreateIntProperty("combinerAch", Configuration.CATEGORY_GENERAL, 216));
                combineItemsID = i(config.getOrCreateIntProperty("combineItemsAch", Configuration.CATEGORY_GENERAL, 217));
            } catch (Exception e) {
                err("Error loading config: " + e.getMessage());
            } finally {
                config.save();
            }
        }

        private static boolean b(Property id) {
            return Boolean.parseBoolean(id.value);
        }
        private static int i(Property id) {
            return Integer.parseInt(id.value);
        }
    }
}