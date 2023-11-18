package ru.wertyfiregames.craftablecreatures.stats;

import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class CCAchievementList
{
    public static Achievement thanksForDownload = (new Achievement("achievement.thanksForDownload",
            "thanksForDownload", 2, -1,
            CCItems.spawn_egg_template, null).registerStat());
    public static Achievement bluedustCollector = (new Achievement("achievement.bluedustCollector",
            "bluedustCollector", 0, 0, CCItems.bluestone, thanksForDownload).registerStat());
    public static Achievement templateManager = (new Achievement("achievement.templateManager",
            "templateManager", 1, 2, CCItems.template, bluedustCollector).registerStat());
    public static Achievement mobSpawner = (new Achievement("achievement.mobSpawner", "mobSpawner", 2,
            1, CCItems.spawn_egg_template, templateManager).registerStat());
    public static Achievement energy = (new Achievement("achievement.energy", "energy", -2, -1,
            CCBlocks.powered_bluestone_block, bluedustCollector).registerStat());
    public static Achievement pbc = (new Achievement("achievement.pbc", "transmutator", -2, 1,
            CCItems.portative_body_changer, bluedustCollector).registerStat());

    public static void register()
    {
        AchievementPage.registerAchievementPage(new AchievementPage(
                "Craftable Creatures", thanksForDownload, bluedustCollector, templateManager, mobSpawner, energy, pbc));
    }
}