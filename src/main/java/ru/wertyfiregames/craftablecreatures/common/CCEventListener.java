package ru.wertyfiregames.craftablecreatures.common;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.KilledByPlayer;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.init.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.util.ChatUtils;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker;
import ru.wertyfiregames.craftablecreatures.version.CCVersionChecker.UpdateResult;

public class CCEventListener {
    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            event.getDrops().add(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ,
                    new ItemStack(CCItems.SOUL, 1, 0)));
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        event.player.addStat(CCAchievementList.thanksForDownload);

        if (CCConfig.enableExperimentalContent) {
            event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.modInfo", true));
        }

        if (CCConfig.checkForUpdates) {
            MinecraftServer server = event.player.getServer();
            if (server != null && !server.isSinglePlayer()) return;
            String homepage = CCVersionChecker.getHomepageUrl();
            if (CCVersionChecker.getStatus() == UpdateResult.FAILED) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.failedToCheckUpdates"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.UP_TO_DATE) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.latest"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.OUTDATED) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.outdated"));
                ChatUtils.sendMessageWithLink(event.player, "craftableCreatures.chat.getUpdate", homepage, homepage, "");
                ChatUtils.sendUpdateChangelogMessage(event.player, CCVersionChecker.getChangelog());
            }
            if (CCVersionChecker.getStatus() == UpdateResult.AHEAD) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.ahead"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.beta"));
            }
            if (CCVersionChecker.getStatus() == UpdateResult.BETA_OUTDATED) {
                event.player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.betaOutdated"));
                ChatUtils.sendMessageWithLink(event.player, "craftableCreatures.chat.getUpdate", homepage, homepage, "");
                ChatUtils.sendUpdateChangelogMessage(event.player, CCVersionChecker.getChangelog());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player != null) {
            for (ItemStack stack : event.player.inventory.mainInventory) {
                if (stack != null) {
                    if (stack.getItem() == CCItems.BLUESTONE) {
                        event.player.addStat(CCAchievementList.bluedustCollector);
                    }
                    if (stack.getItem() == CCItems.BLUEPRINT) {
                        event.player.addStat(CCAchievementList.templateManager);
                    }
                    if (stack.getItem() == CCItems.SPAWN_EGG_BLUEPRINT) {
                        event.player.addStat(CCAchievementList.mobSpawner);
                    }
                    if (stack.getItem() == CCItems.TRANSMUTATOR) {
                        event.player.addStat(CCAchievementList.transmutator);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.POWERED_BLUESTONE_BLOCK)) {
                        event.player.addStat(CCAchievementList.energy);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.SOUL_EXTRACTOR)) {
                        event.player.addStat(CCAchievementList.soulExtractor);
                    }
                    if (stack.getItem() == Item.getItemFromBlock(CCBlocks.COMBINER)) {
                        event.player.addStat(CCAchievementList.combiner);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(CraftableCreatures.getModId())) {
            CCConfig.load();
            if (CCConfig.checkForUpdates && CCVersionChecker.getStatus() == UpdateResult.PENDING)
                CCVersionChecker.check(CraftableCreatures.getVersion());
        }
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent event) {
        LootTable table = event.getTable();
        if (event.getName().equals(LootTableList.ENTITIES_BAT)) {
            LootFunction[] functions = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)),
                    new LootingEnchantBonus(new LootCondition[0], new RandomValueRange(0, 1)) };
            LootEntry[] entries = { new LootEntryItem(CCItems.BAT_WING, 1, 1, functions, new LootCondition[0], "bat_wing") };
            LootCondition[] conditions = { new KilledByPlayer(false) };
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_bat_drop"));
        }
        if (event.getName().equals(LootTableList.ENTITIES_OCELOT)) {
            LootFunction[] functions = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)) };
            LootEntry[] entries = { new LootEntryItem(CCItems.OCELOT_TAIL, 1, 1, functions, new LootCondition[0], "bat_wing") };
            LootCondition[] conditions = { new KilledByPlayer(false) };
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_ocelot_drop"));
        }

        if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) {
            LootFunction[] functions1 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 2)), new SetMetadata(new LootCondition[0], new RandomValueRange(0)) };
            LootFunction[] functions2 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 2)), new SetMetadata(new LootCondition[0], new RandomValueRange(23)) };
            LootFunction[] functions3 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 2)), new SetMetadata(new LootCondition[0], new RandomValueRange(24)) };
            LootFunction[] functions4 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 2)), new SetMetadata(new LootCondition[0], new RandomValueRange(28)) };
            LootEntry[] entries = {
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions1, new LootCondition[0], "soul0"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions2, new LootCondition[0], "soul23"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions3, new LootCondition[0], "soul24"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions4, new LootCondition[0], "soul28")
            };
            LootCondition[] conditions = new LootCondition[0];
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_abandoned_mineshaft_loot"));
        }
        if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
            LootFunction[] functions1 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(0)) };
            LootFunction[] functions2 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(23)) };
            LootFunction[] functions3 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(24)) };
            LootFunction[] functions4 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(28)) };
            LootEntry[] entries = {
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions1, new LootCondition[0], "soul0"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions2, new LootCondition[0], "soul23"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions3, new LootCondition[0], "soul24"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions4, new LootCondition[0], "soul28")
            };
            LootCondition[] conditions = new LootCondition[0];
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_desert_pyramid_loot"));
        }
        if (event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
            LootFunction[] functions1 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(0)) };
            LootFunction[] functions2 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(23)) };
            LootFunction[] functions3 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(24)) };
            LootFunction[] functions4 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(28)) };
            LootEntry[] entries = {
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions1, new LootCondition[0], "soul0"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions2, new LootCondition[0], "soul23"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions3, new LootCondition[0], "soul24"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions4, new LootCondition[0], "soul28")
            };
            LootCondition[] conditions = new LootCondition[0];
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_jungle_temple_loot"));
        }
        if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
            LootFunction[] functions1 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(0)) };
            LootFunction[] functions2 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(23)) };
            LootFunction[] functions3 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(24)) };
            LootFunction[] functions4 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(28)) };
            LootEntry[] entries = {
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions1, new LootCondition[0], "soul0"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions2, new LootCondition[0], "soul23"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions3, new LootCondition[0], "soul24"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions4, new LootCondition[0], "soul28")
            };
            LootCondition[] conditions = new LootCondition[0];
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_simple_dungeon_loot"));
        }
        if (event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR)) {
            LootFunction[] functions1 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(0)) };
            LootFunction[] functions2 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(23)) };
            LootFunction[] functions3 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(24)) };
            LootFunction[] functions4 = { new SetCount(new LootCondition[0], new RandomValueRange(0, 1)), new SetMetadata(new LootCondition[0], new RandomValueRange(28)) };
            LootEntry[] entries = {
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions1, new LootCondition[0], "soul0"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions2, new LootCondition[0], "soul23"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions3, new LootCondition[0], "soul24"),
                    new LootEntryItem(CCItems.SOUL, 5, 1, functions4, new LootCondition[0], "soul28")
            };
            LootCondition[] conditions = new LootCondition[0];
            table.addPool(new LootPool(entries, conditions, new RandomValueRange(1), new RandomValueRange(0), "cc_custom_stronghold_corridor_loot"));
        }
    }
}