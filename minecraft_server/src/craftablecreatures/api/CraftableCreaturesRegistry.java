/**
    File created on 20:22 09.09.2026 by Wertyfire
*/

package craftablecreatures.api;

import craftablecreatures.CombinerRecipes;
import craftablecreatures.SoulExtractorRecipes;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**@author Wertyfire*/
public class CraftableCreaturesRegistry {
    /**List of souls so other addons can check if items is soul*/
    private static final List<Item> souls = new ArrayList<>();
    /**List of soul extractor fuel handlers*/
    private static final List<ISEFuelHandler> seFuelHandlers = new ArrayList<>();
    /**List of soul extracted event handlers*/
    private static final List<ISoulExtractedEventHandler> soulExtractedHandlers = new ArrayList<>();
    /**List of item combined event handlers*/
    private static final List<IItemCombinedEventHandler> itemCombinedHandlers = new ArrayList<>();

    /**Add recipe to soul extractor*/
    public static void addExtracting(Block input, ItemStack output) {
        SoulExtractorRecipes.get().addRecipe(input.blockID, output);
    }
    /**Add recipe to soul extractor*/
    public static void addExtracting(Item input, ItemStack output) {
        SoulExtractorRecipes.get().addRecipe(input.shiftedIndex, output);
    }
    /**Add recipe to soul extractor*/
    public static void addExtracting(ItemStack input, ItemStack output) {
        SoulExtractorRecipes.get().addRecipe(input.itemID, output);
    }

    /**Add recipe to combiner*/
    public static void addCombining(Item firstInput, Item secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
    /**Add recipe to combiner*/
    public static void addCombining(ItemStack firstInput, Item secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
    /**Add recipe to combiner*/
    public static void addCombining(Item firstInput, ItemStack secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
    /**Add recipe to combiner*/
    public static void addCombining(ItemStack firstInput, ItemStack secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }

    /**Add item as soul*/
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

    /**Register soul extracted event handler*/
    public static void registerSoulExtractedEventHandler(ISoulExtractedEventHandler handler) {
        soulExtractedHandlers.add(handler);
    }

    /**Register item combined event handler*/
    public static void registerItemCombinedEventHandler(IItemCombinedEventHandler handler) {
        itemCombinedHandlers.add(handler);
    }

    /**Get modified item from se fuel handler. Used by {@linkplain craftablecreatures.TileEntitySoulExtractor#getFuelWorkTime(ItemStack) TileEntitySoulExtractor#getFuelWorkTime(ItemStack)}
     * @see #registerSEFuelHandler(ISEFuelHandler)*/
    public static int getSEFuelValue(ItemStack itemStack) {
        int fuelValue = 0;
        for (ISEFuelHandler handler : seFuelHandlers) {
            fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
        }
        return fuelValue;
    }

    /**Trigger soul extracted event. Used by {@linkplain craftablecreatures.SlotSoulExtractor#onPickupFromSlot(ItemStack) SlotSoulExtractor#onPickupFromSlot(ItemStack)}
     * @see #registerSoulExtractedEventHandler(ISoulExtractedEventHandler)*/
    public static void onSoulExtractedEvent(EntityPlayer player, ItemStack result) {
        for (ISoulExtractedEventHandler handler : soulExtractedHandlers) {
            handler.soulExtracted(player, result);
        }
    }

    /**Trigger item combined event. Used by {@linkplain craftablecreatures.SlotCombiner#onPickupFromSlot(ItemStack) SlotCombiner#onPickupFromSlot(ItemStack)}
     * @see #registerItemCombinedEventHandler(IItemCombinedEventHandler)*/
    public static void onItemCombinedEvent(EntityPlayer player, ItemStack result) {
        for (IItemCombinedEventHandler handler : itemCombinedHandlers) {
            handler.itemCombined(player, result);
        }
    }
}