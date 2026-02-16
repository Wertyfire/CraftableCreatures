package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class CCAchievementList {
    public static final Achievement thanksForDownload = new Achievement("achievement.craftableCreatures.thanksForDownload",
            "craftableCreatures.thanksForDownload", -1, 0,
            CCItems.GUIDE_BOOK, null).registerStat();
    public static final Achievement bluedustCollector = new Achievement("achievement.craftableCreatures.bluedustCollector",
            "craftableCreatures.bluedustCollector", 1, 0, CCItems.BLUESTONE, thanksForDownload).registerStat();
    public static final Achievement transmutator = new Achievement("achievement.craftableCreatures.transmutator",
            "craftableCreatures.transmutator", 1, -2, CCItems.TRANSMUTATOR, bluedustCollector).registerStat();
    public static final Achievement templateManager = new Achievement("achievement.craftableCreatures.templateManager",
            "craftableCreatures.templateManager", 1, 2, CCItems.BLUEPRINT, bluedustCollector).registerStat();
    public static final Achievement mobSpawner = new Achievement("achievement.craftableCreatures.mobSpawner",
            "craftableCreatures.mobSpawner", 2, 4, CCItems.SPAWN_EGG_BLUEPRINT, templateManager).registerStat();
    public static final Achievement energy = new Achievement("achievement.craftableCreatures.energy",
            "craftableCreatures.energy", 3, 0, CCBlocks.POWERED_BLUESTONE_BLOCK, bluedustCollector).registerStat();
    public static final Achievement soulExtractor = new Achievement("achievement.craftableCreatures.soulExtractor",
            "craftableCreatures.soulExtractor", 5, 2, CCBlocks.LIT_SOUL_EXTRACTOR, energy).registerStat();
    public static final Achievement extractSoul = new Achievement("achievement.craftableCreatures.extractSoul",
            "craftableCreatures.extractSoul", 7, 2, CCItems.SOUL, soulExtractor).registerStat();
    public static final Achievement combiner = new Achievement("achievement.craftableCreatures.combiner",
            "craftableCreatures.combiner", 5, -2, CCBlocks.LIT_COMBINER, energy).registerStat();
    public static final Achievement combineItem = new Achievement("achievement.craftableCreatures.combineItem",
            "craftableCreatures.combineItem", 7, -2, Items.SPAWN_EGG, combiner).registerStat();

    public static void register() {
        AchievementPage.registerAchievementPage(new AchievementPage(
                "Craftable Creatures", thanksForDownload, bluedustCollector, templateManager, mobSpawner, transmutator, energy, soulExtractor, extractSoul, combiner, combineItem));
    }
}