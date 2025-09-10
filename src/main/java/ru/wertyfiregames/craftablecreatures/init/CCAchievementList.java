package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class CCAchievementList {
    public static final Achievement thanksForDownload = new Achievement("achievement.craftableCreatures.thanksForDownload",
            "craftableCreatures.thanksForDownload", 2, -1,
            CCItems.guide_book, null).registerStat();
    public static final Achievement bluedustCollector = new Achievement("achievement.craftableCreatures.bluedustCollector",
            "craftableCreatures.bluedustCollector", 0, 0, CCItems.bluestone, thanksForDownload).registerStat();
    public static final Achievement transmutator = new Achievement("achievement.craftableCreatures.transmutator",
            "craftableCreatures.transmutator", -2, 0, CCItems.transmutator, bluedustCollector).registerStat();
    public static final Achievement templateManager = new Achievement("achievement.craftableCreatures.templateManager",
            "craftableCreatures.templateManager", 1, 2, CCItems.template, bluedustCollector).registerStat();
    public static final Achievement mobSpawner = new Achievement("achievement.craftableCreatures.mobSpawner",
            "craftableCreatures.mobSpawner", 2, 1, CCItems.spawn_egg_template, templateManager).registerStat();
    public static final Achievement energy = new Achievement("achievement.craftableCreatures.energy", "craftableCreatures.energy", -2, -2,
            CCBlocks.powered_bluestone_block, bluedustCollector).registerStat();
    public static final Achievement soulExtractor = new Achievement("achievement.craftableCreatures.soulExtractor", "craftableCreatures.soulExtractor",
            -2, -4, CCBlocks.lit_soul_extractor, energy).registerStat();
    public static final Achievement extractSoul = new Achievement("achievement.craftableCreatures.extractSoul", "craftableCreatures.extractSoul",
            0, -4, CCItems.soul_element, soulExtractor).registerStat();
    public static final Achievement combiner = new Achievement("achievement.craftableCreatures.combiner", "craftableCreatures.combiner",
            -4, -2, CCBlocks.lit_combiner, energy).registerStat();
    public static final Achievement combineItem = new Achievement("achievement.craftableCreatures.combineItem", "craftableCreatures.combineItem",
            -4, 0, Items.spawn_egg, combiner).registerStat();

    public static void register() {
        AchievementPage.registerAchievementPage(new AchievementPage(
                "Craftable Creatures", thanksForDownload, bluedustCollector, templateManager, mobSpawner, transmutator, energy, soulExtractor, extractSoul, combiner, combineItem));
    }
}