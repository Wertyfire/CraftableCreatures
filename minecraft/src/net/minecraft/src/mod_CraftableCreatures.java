/**
 * File created on 18:07 12.03.2026 by Wertyfire
 */

package net.minecraft.src;

import craftablecreatures.*;
import craftablecreatures.BlockOre;
import forge.*;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.Random;

import static net.minecraft.src.mod_CraftableCreatures.CraftableCreaturesIDs.*;

public class mod_CraftableCreatures extends BaseModMp {
    public static final String VERSION = "1.0.0";
    public static final String BUILD = "01";

    public static mod_CraftableCreatures instance;

    private static boolean updateNotified = false;

    public static final String blockAtlas = "/craftablecreatures/block_textures.png";
    public static final String itemAtlas = "/craftablecreatures/item_textures.png";

    public static Block bluestoneBlock;
    public static Block bluestoneOre;
    public static Block soulExtractor;
    public static Block soulExtractorLit;
    public static Block combiner;
    public static Block combinerLit;

    public static Item bluestone;
    public static Item template;
    public static Item spawnEggTemplate;
    public static Item soulElement;
    public static Item guideBook;

    public static final String soul = "soul";

    public static Achievement thanksForDownload;
    public static Achievement bluestoneCollector;
    public static Achievement templateManager;
    public static Achievement mobSpawner;
    public static Achievement soulExtractorAch;
    public static Achievement extractSoulAch;
    public static Achievement combinerAch;
    public static Achievement combineItemAch;

    public mod_CraftableCreatures() {
        instance = this;
    }

    @Override
    public void load() {
        CraftableCreaturesIDs.init(createConfig());
        CCVersionChecker.check(VERSION);

        loadTextures();
        loadBlocks();
        loadItems();
        registerTileEntities();
        registerGuis();
        loadParticles();
        loadAchievements();
        loadRecipes();
        loadChestLoots();

        ModLoader.SetInGameHook(this, true, false);
    }

    @Override
    public GuiScreen HandleGUI(int i) {
        if (i == guiSoulExtractorID)
            return new GuiSoulExtractor(ModLoader.getMinecraftInstance().thePlayer.inventory, BlockSoulExtractor.clientTile);
        if (i == guiCombinerID)
            return new GuiCombiner(ModLoader.getMinecraftInstance().thePlayer.inventory, BlockCombiner.clientTile);

        return null;
    }

    @Override
    public boolean OnTickInGame(float tick, Minecraft game) {
        EntityPlayer player = game.thePlayer;

        if (player != null) {
            if (checkForUpdates && !player.worldObj.multiplayerWorld && !updateNotified) {
                updateNotified = true;
                String homepage = CCVersionChecker.getHomepageUrl();

                if (CCVersionChecker.getStatus() == CCVersionChecker.UpdateResult.FAILED) {
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.failedToCheckUpdates"));
                }
                if (CCVersionChecker.getStatus() == CCVersionChecker.UpdateResult.UP_TO_DATE) {
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.latest"));
                }
                if (CCVersionChecker.getStatus() == CCVersionChecker.UpdateResult.OUTDATED) {
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.outdated"));
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.getUpdate") + " " + homepage);
                    ChatUtils.sendUpdateChangelogMessage(player, CCVersionChecker.getChangelog(), ModLoader.getMinecraftInstance().gameSettings.field_44018_Q);
                }
                if (CCVersionChecker.getStatus() == CCVersionChecker.UpdateResult.AHEAD) {
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.ahead"));
                }
                if (CCVersionChecker.getStatus() == CCVersionChecker.UpdateResult.BETA) {
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.beta"));
                }
                if (CCVersionChecker.getStatus() == CCVersionChecker.UpdateResult.BETA_OUTDATED) {
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.betaOutdated"));
                    player.addChatMessage(StringTranslate.getInstance().translateKey("craftableCreatures.chat.getUpdate") + " " + homepage);
                    ChatUtils.sendUpdateChangelogMessage(player, CCVersionChecker.getChangelog(), ModLoader.getMinecraftInstance().gameSettings.field_44018_Q);
                }
            }

            player.triggerAchievement(thanksForDownload);

            for (ItemStack item : player.inventory.mainInventory) {
                checkForAchievementsTick(player, item);
            }

            if (player.isDead) {
                if (!player.getEntityData().getBoolean("CraftableCreatures_DeathProcessed")) {
                    player.getEntityData().setBoolean("CraftableCreatures_DeathProcessed", true);

                    player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj,
                            player.posX, player.posY, player.posZ, new ItemStack(soulElement, 1, 0)));
                }
            } else player.getEntityData().setBoolean("CraftableCreatures_DeathProcessed", false);
        }

        return true;
    }

    @Override
    public void OnItemPickup(EntityPlayer player, ItemStack item) {
        checkForAchievements(player, item);
    }

    @Override
    public void TakenFromCrafting(EntityPlayer player, ItemStack item, IInventory matrix) {
        checkForAchievements(player, item);
    }

    @Override
    public void TakenFromFurnace(EntityPlayer player, ItemStack item) {
        checkForAchievements(player, item);
    }

    private static void checkForAchievements(EntityPlayer player, ItemStack item) {
        if (item == null) return;

        if (item.itemID == bluestone.shiftedIndex) {
            player.triggerAchievement(bluestoneCollector);
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

    private static void checkForAchievementsTick(EntityPlayer player, ItemStack item) {
        if (item == null) return;

        Minecraft mc = ModLoader.getMinecraftInstance();
        if (!mc.statFileWriter.hasAchievementUnlocked(bluestoneCollector))
            if (item.itemID == bluestone.shiftedIndex)
                player.triggerAchievement(bluestoneCollector);

        if (!mc.statFileWriter.hasAchievementUnlocked(templateManager))
            if (item.itemID == template.shiftedIndex)
                player.triggerAchievement(templateManager);

        if (!mc.statFileWriter.hasAchievementUnlocked(mobSpawner))
            if (item.itemID == spawnEggTemplate.shiftedIndex)
                player.triggerAchievement(mobSpawner);

        if (!mc.statFileWriter.hasAchievementUnlocked(soulExtractorAch))
            if (item.itemID == soulExtractor.blockID)
                player.triggerAchievement(soulExtractorAch);

        if (!mc.statFileWriter.hasAchievementUnlocked(combinerAch))
            if (item.itemID == combiner.blockID)
                player.triggerAchievement(combinerAch);
    }

    private static void loadTextures() {
        MinecraftForgeClient.preloadTexture(blockAtlas);
        MinecraftForgeClient.preloadTexture(itemAtlas);
    }

    private static void loadBlocks() {
        bluestoneBlock = createBlock(new BlockDefault(bluestoneBlockID, Material.iron, 3f, 5f, Block.soundMetalFootstep)
                .setTextureIndex(0).setBlockName("bluestoneBlock"), "pickaxe", 2);
        bluestoneOre = createBlock(new BlockOre(bluestoneOreID, 3f, 5f)
                .setTextureIndex(1).setBlockName("bluestoneOre"), "pickaxe", 2);
        soulExtractor = createBlock(new BlockSoulExtractor(soulExtractorID, false), BlockSoulExtractor.SoulExtractorItemBlock.class, "pickaxe", 1);
        soulExtractorLit = createBlock(new BlockSoulExtractor(litSoulExtractorID, true), "pickaxe", 1);
        combiner = createBlock(new BlockCombiner(combinerID, false), BlockCombiner.CombinerItemBlock.class, "pickaxe", 1);
        combinerLit = createBlock(new BlockCombiner(litCombinerID, true), "pickaxe", 1);
    }

    private static void loadItems() {
        bluestone = new ItemDefault(bluestoneID).setIconCoord(0, 0).setItemName("bluestone");
        template = new ItemDefault(templateID).setIconCoord(1, 0).setItemName("template");
        spawnEggTemplate = new ItemDefault(spawnEggTemplateID).setIconCoord(2, 0).setItemName("spawnEggTemplate");
        guideBook = new ItemGuideBook(guideBookID).setItemName("craftableCreaturesGuideBook");
        soulElement = new ItemSoulElement().setItemName("soul");
        ItemSoulElement.soulTextures[0] = 4;
        for (int i = 1; i < 21; i++) ItemSoulElement.soulTextures[i] = 4 + i;
    }

    private static void registerTileEntities() {
        ModLoader.RegisterTileEntity(TileEntitySoulExtractor.class, "soul_extractor");
        ModLoader.RegisterTileEntity(TileEntityCombiner.class, "combiner");
    }

    private void registerGuis() {
        ModLoaderMp.RegisterGUI(this, guiSoulExtractorID);
        ModLoaderMp.RegisterGUI(this, guiCombinerID);
    }

    private static void loadParticles() {
        ParticleUtils.register(EntitySoulFX.class, soul);
    }

    private static void loadAchievements() {
        thanksForDownload = new Achievement(thanksForDownloadAchID, "craftableCreatures.thanksForDownload", -8, 10, guideBook, null).b().d();
        bluestoneCollector = new Achievement(bluestoneCollectorID, "craftableCreatures.bluedustCollector", -8, 8, bluestone, thanksForDownload).d();
        templateManager = new Achievement(templateManagerID, "craftableCreatures.templateManager", -10, 7, template, bluestoneCollector).d();
        mobSpawner = new Achievement(mobSpawnerID, "craftableCreatures.mobSpawner", -10, 5, spawnEggTemplate, templateManager).d();
        soulExtractorAch = new Achievement(soulExtractorAchID, "craftableCreatures.soulExtractor", -8, 6, soulExtractorLit, bluestoneCollector).d();
        extractSoulAch = new Achievement(extractSoulID, "craftableCreatures.extractSoul", -8, 4, soulExtractor, soulExtractorAch).d();
        combinerAch = new Achievement(combinerAchID, "craftableCreatures.combiner", -6, 7, combinerLit, bluestoneCollector).d();
        combineItemAch = new Achievement(combineItemsID, "craftableCreatures.combineItem", -6, 5, combiner, combinerAch).d();
    }

    private static void loadRecipes() {
        //Shapeless recipes
        ModLoader.AddShapelessRecipe(new ItemStack(spawnEggTemplate), new ItemStack(Item.egg), new ItemStack(template));
        ModLoader.AddShapelessRecipe(new ItemStack(bluestone, 9), new ItemStack(bluestoneBlock));

        //Shaped recipes
        ModLoader.AddRecipe(new ItemStack(template),
                "SR", "RS", 'R', bluestone, 'S', Item.paper);
        ModLoader.AddRecipe(new ItemStack(bluestoneBlock),
                "SSS", "SSS", "SSS", 'S', bluestone);
        ModLoader.AddRecipe(new ItemStack(guideBook),
                " S ", "SUS", " S ", 'S', soulElement, 'U', Item.book);
        ModLoader.AddRecipe(new ItemStack(soulExtractor),
                "SSS", "SUS", "SAS", 'S', Block.cobblestone, 'U', bluestoneBlock, 'A', Block.stoneOvenIdle);
        ModLoader.AddRecipe(new ItemStack(combiner),
                "SSS", "SUS", "SAS", 'S', Block.cobblestone, 'U', template, 'A', Block.torchRedstoneActive);

        //Smelting recipes
        ModLoader.AddSmelting(bluestoneOre.blockID, new ItemStack(bluestone));
    }

    private static void loadChestLoots() {
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 0), 1f, 1, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 2), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 3), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 4), 0.8f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 5), 0.8f, 1, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 8), 0.5f, 0, 2);
        MinecraftForge.addDungeonLoot(new ItemStack(soulElement, 1, 12), 0.8f, 0, 2);
    }

    @Override
    public void GenerateSurface(World world, Random random, int chunkX, int chunkZ) {
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

    private static Configuration createConfig() {
        File configDir = new File(Minecraft.getMinecraftDir(), "config");
        if (!configDir.exists()) configDir.mkdirs();

        return new Configuration(new File(configDir, "craftableCreatures.cfg"));
    }

    private static Block createBlock(Block block, String tool, int level) {
        return createBlock(block, null, tool, level);
    }
    private static Block createBlock(Block block, Class<? extends ItemBlock> itemBlockClass, String tool, int level) {
        ModLoader.RegisterBlock(block, itemBlockClass);

        if (tool != null)
            MinecraftForge.setBlockHarvestLevel(block, tool, level);

        return block;
    }

    public static void info(String info) {
        System.out.println("[Craftable Creatures]: " + info);
    }
    public static void err(String err) {
        System.err.println("[Craftable Creatures]: " + err);
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    public String getBuild() {
        return BUILD;
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

        public static int guiSoulExtractorID = 200;
        public static int guiCombinerID = 201;

        public static void init(Configuration config) {
            try {
                config.load();

                checkForUpdates = b(config.getOrCreateBooleanProperty("checkForUpdates", Configuration.GENERAL_PROPERTY, true));

                bluestoneBlockID = i(config.getOrCreateBlockIdProperty("bluestoneBlock", 210));
                bluestoneOreID = i(config.getOrCreateBlockIdProperty("bluestoneOre", 211));
                soulExtractorID = i(config.getOrCreateBlockIdProperty("soulExtractor", 212));
                litSoulExtractorID = i(config.getOrCreateBlockIdProperty("litSoulExtractor", 213));
                combinerID = i(config.getOrCreateBlockIdProperty("combinerID", 214));
                litCombinerID = i(config.getOrCreateBlockIdProperty("litCombiner", 215));

                bluestoneID = i(config.getOrCreateIntProperty("bluestone", Configuration.ITEM_PROPERTY, 800));
                templateID = i(config.getOrCreateIntProperty("template", Configuration.ITEM_PROPERTY, 801));
                spawnEggTemplateID = i(config.getOrCreateIntProperty("spawnEggTemplate", Configuration.ITEM_PROPERTY, 802));
                soulElementID = i(config.getOrCreateIntProperty("soulElement", Configuration.ITEM_PROPERTY, 803));
                guideBookID = i(config.getOrCreateIntProperty("guideBook", Configuration.ITEM_PROPERTY, 804));

                thanksForDownloadAchID = i(config.getOrCreateIntProperty("thanksForDownloadAch", Configuration.GENERAL_PROPERTY, 210));
                bluestoneCollectorID = i(config.getOrCreateIntProperty("bluestoneCollectorAch", Configuration.GENERAL_PROPERTY, 211));
                templateManagerID = i(config.getOrCreateIntProperty("templateManagerAch", Configuration.GENERAL_PROPERTY, 212));
                mobSpawnerID = i(config.getOrCreateIntProperty("mobSpawnerAch", Configuration.GENERAL_PROPERTY, 213));
                soulExtractorAchID = i(config.getOrCreateIntProperty("soulExtractorAch", Configuration.GENERAL_PROPERTY, 214));
                extractSoulID = i(config.getOrCreateIntProperty("extractSoulAch", Configuration.GENERAL_PROPERTY, 215));
                combinerAchID = i(config.getOrCreateIntProperty("combinerAch", Configuration.GENERAL_PROPERTY, 216));
                combineItemsID = i(config.getOrCreateIntProperty("combineItemsAch", Configuration.GENERAL_PROPERTY, 217));

                guiSoulExtractorID = i(config.getOrCreateIntProperty("guiSoulExtractor", Configuration.GENERAL_PROPERTY, 200));
                guiCombinerID = i(config.getOrCreateIntProperty("guiCombiner", Configuration.GENERAL_PROPERTY, 201));
            } catch (Exception e) {
                err("Error loading config!");
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