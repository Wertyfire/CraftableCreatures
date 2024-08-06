/**
 * File created on 15:07 31.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.init.SoulExtractorRecipes;

import java.util.List;

public class CraftableCreaturesRegistry {
    private static final List<ISEFuelHandler> seFuelHandlers = Lists.newArrayList();

    public static void addSoulExtractorExtractHelper(Block helper) {
        SoulExtractorRecipes.get().addExtractHelper(helper);
    }
    public static void addSoulExtractorExtractHelper(Item helper) {
        SoulExtractorRecipes.get().addExtractHelper(helper);
    }
    public static void addSoulExtractorExtractHelper(String nameInOreDict) {
        SoulExtractorRecipes.get().addExtractHelper(nameInOreDict);
    }
    public static void addSoulExtractorExtractHelper(ItemStack helper) {
        SoulExtractorRecipes.get().addExtractHelper(helper);
    }

    public static void addSoulExtractorRecipe(Block input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }
    public static void addSoulExtractorRecipe(Item input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }
    public static void addSoulExtractorRecipe(String nameInOreDict, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(nameInOreDict, output, xp);
    }
    public static void addSoulExtractorRecipe(ItemStack input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }

    public static void registerSEFuelHandler(ISEFuelHandler handler) {
        seFuelHandlers.add(handler);
    }

    public static int getSEFuelValue(ItemStack itemStack) {
        int fuelValue = 0;
        for (ISEFuelHandler handler : seFuelHandlers) {
            fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
        }
        return fuelValue;
    }
}