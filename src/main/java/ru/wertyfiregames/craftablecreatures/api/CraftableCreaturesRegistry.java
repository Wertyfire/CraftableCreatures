/**
 * File created on 15:07 31.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**@author Wertyfire*/
public class CraftableCreaturesRegistry {
    /**List of souls which should be written to subset in NEI*/
    private static final List<Item> souls = Lists.newArrayList();
    private static final Map<ItemStack, EntityLivingBase> morphList = new HashMap<>();
    /**List of soul extractor fuel handlers*/
    private static final List<ISEFuelHandler> seFuelHandlers = Lists.newArrayList();

    /**Link soul to mob so transmutator will send instance of this mob to Morph
     * @param metaOptional if soul is item with meta, specify it here. if it's not - write 0
     * @param entityToMorph instance of mob*/
    public static void linkSoul(Item item, int metaOptional, EntityLivingBase entityToMorph) {
        morphList.put(new ItemStack(item, 1, metaOptional), entityToMorph);
    }

    /**Get instance of entity from item stack which will be sent to Morph*/
    public static EntityLivingBase getMorphEntity(ItemStack stack) {
        EntityLivingBase entity = null;
        for (ItemStack stacks : morphList.keySet()) {
            if (stacks.getItem() == stack.getItem() && stacks.getItemDamage() == stack.getItemDamage())
                entity = morphList.get(stacks);
        }
        return entity;
    }

    /**Add recipe to soul extractor
     * @param xp experience from this extraction.*/
    public static void addExtracting(Block input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }
    /**Add recipe to soul extractor
     * @param xp experience from this extraction.*/
    public static void addExtracting(Item input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }
    /**Add recipe to soul extractor
     * @param xp experience from this extraction.*/
    public static void addExtracting(String nameInOreDict, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(nameInOreDict, output, xp);
    }
    /**Add recipe to soul extractor
     * @param xp experience from this extraction.*/
    public static void addExtracting(ItemStack input, ItemStack output, float xp) {
        SoulExtractorRecipes.get().addRecipe(input, output, xp);
    }

    /**Add recipe to combiner
     * @param xp experience from this extraction.*/
    public static void addCombining(Item firstInput, Item secondInput, ItemStack output, float xp) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output, xp);
    }
    /**Add recipe to combiner
     * @param xp experience from this extraction.*/
    public static void addCombining(ItemStack firstInput, Item secondInput, ItemStack output, float xp) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output, xp);
    }
    /**Add recipe to combiner
     * @param xp experience from this extraction.*/
    public static void addCombining(Item firstInput, ItemStack secondInput, ItemStack output, float xp) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output, xp);
    }
    /**Add recipe to combiner
     * @param xp experience from this extraction.*/
    public static void addCombining(ItemStack firstInput, ItemStack secondInput, ItemStack output, float xp) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output, xp);
    }

    /**Add item as soul so this will be shown in subset "Souls" in NEI*/
    public static void registerItemAsSoul(Item soul) {
        souls.add(soul);
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