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

/**@author Wertyfire*/
public class CraftableCreaturesRegistry {
    /**List of souls which should be written to subsets in NEI*/
    private static final List<Item> souls = Lists.newArrayList();
    /**List of soul extractor fuel handlers*/
    private static final List<ISEFuelHandler> seFuelHandlers = Lists.newArrayList();

    /**Add block as base soul (so this block can be applied in slot for soul in soul extractor)*/
    public static void addBaseSoulItem(Block helper) {
        SoulExtractorRecipes.get().addBaseSoul(helper);
    }
    /**Add item as base soul (so this item can be applied in slot for soul in soul extractor)*/
    public static void addBaseSoulItem(Item helper) {
        SoulExtractorRecipes.get().addBaseSoul(helper);
    }
    /**Add every thing from ore dictionary as base soul
     * @apiNote not recommend to use, it needs bugfix*/
    public static void addBaseSoulItem(String nameInOreDict) {
        SoulExtractorRecipes.get().addBaseSoul(nameInOreDict);
    }
    /**Add item stack as base soul (so this item stack can e applied in slot for soul int soul extractor)*/
    public static void addBaseSoulItem(ItemStack helper) {
        SoulExtractorRecipes.get().addBaseSoul(helper);
    }

    /**Add recipe to soul extractor
     * @param xp experience from this extraction. Will be multiplied by 10*/
    public static void addSoulExtractorRecipe(Block input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }
    /**Add recipe to soul extractor
     * @param xp experience from this extraction. Will be multiplied by 10*/
    public static void addSoulExtractorRecipe(Item input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }
    /**Add recipe to soul extractor
     * @param xp experience from this extraction. Will be multiplied by 10*/
    public static void addSoulExtractorRecipe(String nameInOreDict, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(nameInOreDict, output, xp);
    }
    /**Add recipe to soul extractor
     * @param xp experience from this extraction. Will be multiplied by 10*/
    public static void addSoulExtractorRecipe(ItemStack input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }

    /**Add block as soul so this will be shown in subset "Souls" in NEI*/
    public static void registerItemAsSoul(Block soul) {
        registerItemAsSoul(Item.getItemFromBlock(soul));
    }
    /**Add block as soul so this will be shown in subset "Souls" in NEI*/
    public static void registerItemAsSoul(Item soul) {
        souls.add(soul);
    }

    /**Check if given block registered as soul*/
    public static boolean isItemSoul(Block potentialSoul) {
        return isItemSoul(Item.getItemFromBlock(potentialSoul));
    }
    /**Check if given item registered as soul*/
    public static boolean isItemSoul(Item potentialSoul) {
        return souls.contains(potentialSoul);
    }

    /**Get list of souls*/
    public static List<Item> listSouls() {
        return souls;
    }

    /**Register fuel handler for soul extractor*/
    public static void registerSEFuelHandler(ISEFuelHandler handler) {
        seFuelHandlers.add(handler);
    }

    /**Get modified item from se fuel handler. Used by {@linkplain ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor#getFuelWorkTime(ItemStack) TileEntitySoulExtractor#getFuelWorkTime(ItemStack)}
     * @see #registerSEFuelHandler(ISEFuelHandler)*/
    public static int getSEFuelValue(ItemStack itemStack) {
        int fuelValue = 0;
        for (ISEFuelHandler handler : seFuelHandlers) {
            fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
        }
        return fuelValue;
    }
}