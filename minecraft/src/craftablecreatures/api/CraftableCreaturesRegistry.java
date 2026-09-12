/**
    File created on 20:22 09.09.2026 by Wertyfire
*/

package craftablecreatures.api;

import net.minecraft.src.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Main Craftable Creatures registry for addons.
 * Addons must register themselves using {@linkplain #registerAddon(BaseMod) registerAddon(BaseMod)} method.
 * @author Wertyfire*/
public class CraftableCreaturesRegistry {
    /**API version*/
    private static final String API_VERSION = "1.0";

    /**Registrar interface to avoid crashes when Craftable Creatures not installed (or in dev env)*/
    private static CCRegistrar registrar = new CCEmptyRegistrar();
    /**List of addons*/
    private static final Set<BaseMod> addons = new HashSet<>();
    /**List of souls so other addons can check if items is soul*/
    private static final Set<Item> souls = new HashSet<>();
    /**List of soul extractor fuel handlers*/
    private static final Set<ISEFuelHandler> seFuelHandlers = new HashSet<>();
    /**List of soul extracted event handlers*/
    private static final Set<ISoulExtractedEventHandler> soulExtractedHandlers = new HashSet<>();
    /**List of item combined event handlers*/
    private static final Set<IItemCombinedEventHandler> itemCombinedHandlers = new HashSet<>();

    /**
     * Set custom registrar implementation. Don't call that!
     * @param instance instance of Craftable Creatures so other mods can't set custom registrar.
     * @param registrarImpl custom registrar implementation
     * */
    public static void setRegistrar(mod_CraftableCreatures instance, CCRegistrar registrarImpl) {
        registrar = registrarImpl;
    }

    /**
     * Register Craftable Creatures addon. If base mod implements any handler from API
     * it will automatically be registered.
     * */
    public static void registerAddon(BaseMod addon) {
        addons.add(addon);
        if (addon instanceof IItemCombinedEventHandler)
            registerItemCombinedEventHandler((IItemCombinedEventHandler) addon);
        if (addon instanceof ISEFuelHandler)
            registerSEFuelHandler((ISEFuelHandler) addon);
        if (addon instanceof ISoulExtractedEventHandler)
            registerSoulExtractedEventHandler((ISoulExtractedEventHandler) addon);
    }

    /**
     * Get list of registered addons.
     * */
    public static Set<BaseMod> getAddons() {
        return addons;
    }

    /**
     * Get is Craftable Creatures installed.
     * */
    public static boolean craftableCreaturesLoaded() {
        return modLoaded("mod_CraftableCreatures");
    }

    /**
     * Get is mod loaded.
     * @param modId ID of mod to check
     * */
    public static boolean modLoaded(String modId) {
        return ModLoader.isModLoaded(modId) || ModLoader.isModLoaded("net.minecraft.src." + modId);
    }

    /**
     * Safe way to get item instance.
     * Packages and classes must be split with dot, classes and subclasses with dollar sign, class and item instance with dot.
     * If item stored in main class, don't include 'net.minecraft.src.'. Method will add it himself!
     * @param modId ID of target mod
     * @param itemPath path to item instance
     * @return item if found or null so don't forget to do null check!
     * */
    public static Item getItemSafe(String modId, String itemPath) {
        if (!modLoaded(modId)) {
            registrar.err("Mod %s not loaded! Can't get item %s", modId, itemPath);
            return null;
        } else {
            try {
                Item item;
                String className = itemPath.substring(0, itemPath.lastIndexOf("."));
                String itemName = itemPath.substring(itemPath.lastIndexOf(".") + 1);
                Class<?> clazz = Class.forName(className);
                Field fielt = clazz.getField(itemName);
                item = (Item) fielt.get(null);
                if (item == null)
                    registrar.err("Can't get item %s from mod %s! Item field not exist!", itemPath, modId);
                return item;
            } catch (Exception e) {
                try {
                    Item item;
                    String className = itemPath.substring(0, itemPath.lastIndexOf("."));
                    String itemName = itemPath.substring(itemPath.lastIndexOf(".") + 1);
                    Class<?> clazz = Class.forName("net.minecraft.src." + className);
                    Field fielt = clazz.getField(itemName);
                    item = (Item) fielt.get(null);
                    if (item == null)
                        registrar.err("Can't get item %s from mod %s! Item field not exist!", itemPath, modId);
                    return item;
                } catch (Exception e1) {
                    registrar.err("Can't get item %s from mod %s! Error: %s", itemPath, modId, e1.getMessage());
                    return null;
                }
            }
        }
    }

    /**
     * Safe way to get block instance.
     * Packages and classes must be split with dot, classes and subclasses with dollar sign, class and item instance with dot.
     * If item stored in main class, don't include 'net.minecraft.src.'. Method will add it himself!
     * @param modId ID of target mod
     * @param blockPath path to item instance
     * @return block if found or null so don't forget to do null check!
     * */
    public static Block getBlockSafe(String modId, String blockPath) {
        if (!modLoaded(modId)) {
            registrar.err("Mod %s not loaded! Can't get item %s", modId, blockPath);
            return null;
        } else {
            try {
                Block block;
                String className = blockPath.substring(0, blockPath.lastIndexOf("."));
                String itemName = blockPath.substring(blockPath.lastIndexOf(".") + 1);
                Class<?> clazz = Class.forName(className);
                Field fielt = clazz.getField(itemName);
                block = (Block) fielt.get(null);
                if (block == null)
                    registrar.err("Can't get block %s from mod %s! Item field not exist!", blockPath, modId);
                return block;
            } catch (Exception e) {
                try {
                    Block block;
                    String className = blockPath.substring(0, blockPath.lastIndexOf("."));
                    String itemName = blockPath.substring(blockPath.lastIndexOf(".") + 1);
                    Class<?> clazz = Class.forName("net.minecraft.src." + className);
                    Field fielt = clazz.getField(itemName);
                    block = (Block) fielt.get(null);
                    if (block == null)
                        registrar.err("Can't get block %s from mod %s! Item field not exist!", blockPath, modId);
                    return block;
                } catch (Exception e1) {
                    registrar.err("Can't get block %s from mod %s! Error: %s", blockPath, modId, e1.getMessage());
                    return null;
                }
            }
        }
    }

    /**
     * Safe way to get entity instance.
     * Uses {@link net.minecraft.src.EntityList#createEntityInWorld(String, World)}
     * @param modId ID of target mod
     * @param entityId ID of target entity
     * @return entity id if found or -1 otherwise so don't forget to do -1 check!
     */
    public static int getEntityIdSafe(String modId, String entityId) {
        if (!modLoaded(modId)) {
            registrar.err("Mod %s not loaded! Can't get entity %s", modId, entityId);
            return -1;
        } else {
            try {
                Entity entity = EntityList.createEntityInWorld(entityId, null);
                return EntityList.getEntityID(entity);
            } catch (Exception e) {
                registrar.err("Can't get entity %s from mod %s! Error: %s", entityId, modId, e.getMessage());
                return -1;
            }
        }
    }

    /**Add recipe to soul extractor*/
    public static void addExtracting(Block input, ItemStack output) {
        registrar.addExtracting(input, output);
    }
    /**Add recipe to soul extractor*/
    public static void addExtracting(Item input, ItemStack output) {
        registrar.addExtracting(input, output);
    }
    /**Add recipe to soul extractor*/
    public static void addExtracting(ItemStack input, ItemStack output) {
        registrar.addExtracting(input, output);
    }

    /**Add recipe to combiner*/
    public static void addCombining(Item firstInput, Item secondInput, ItemStack output) {
        registrar.addCombining(firstInput, secondInput, output);
    }
    /**Add recipe to combiner*/
    public static void addCombining(ItemStack firstInput, Item secondInput, ItemStack output) {
        registrar.addCombining(firstInput, secondInput, output);
    }
    /**Add recipe to combiner*/
    public static void addCombining(Item firstInput, ItemStack secondInput, ItemStack output) {
        registrar.addCombining(firstInput, secondInput, output);
    }
    /**Add recipe to combiner*/
    public static void addCombining(ItemStack firstInput, ItemStack secondInput, ItemStack output) {
        registrar.addCombining(firstInput, secondInput, output);
    }

    /**Register item as soul*/
    public static void registerItemAsSoul(Item soul) {
        souls.add(soul);
    }

    /**Check if given item registered as soul*/
    public static boolean isItemSoul(Item potentialSoul) {
        return souls.contains(potentialSoul);
    }

    /**Get list of souls*/
    public static Set<Item> listSouls() {
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

    public static boolean isAddonLoaded(String addonId) {
        return addons.stream().anyMatch(addon -> addon.getClass().getSimpleName().equals(addonId));
    }

    /**
     * Get API version to check for compatibility.
     * @return API version
     * */
    public static String apiVersion() {
        return API_VERSION;
    }
}