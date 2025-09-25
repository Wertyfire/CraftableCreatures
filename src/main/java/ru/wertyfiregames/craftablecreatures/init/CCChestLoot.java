///**
// * File created on 16:38 31.08.2024 by Wertyfire
// */
//
//package ru.wertyfiregames.craftablecreatures.init;
//
//import net.minecraft.util.WeightedRandomChestContent;
//import net.minecraftforge.common.ChestGenHooks;
//import ru.wertyfiregames.craftablecreatures.item.EnumSoulElement;
//
//public class CCChestLoot {
//    public static void register() {
//        //Souls
//        for (int i = 14; i <= EnumSoulElement.values().length; i++) {
//            ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 0, 1, 1));
//            ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 1, 3, 2));
//            ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 0, 1, 1));
//            ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 0, 1, 1));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 0, 1, 1));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 0, 1, 1));
//            ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(CCItems.SOUL, i, 0, 1, 1));
//        }
//    }
//}