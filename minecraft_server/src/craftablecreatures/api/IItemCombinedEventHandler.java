/**
 * File created on 20:50 09.09.2026 by Wertyfire
 */

package craftablecreatures.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

/**
 * Handler for item combined event called when player takes output item (usually spawnEgg/monsterPlacer) from combiner output slot.
 * Useful for addons that want to add some effects or achievements when player combines item.
 * @author Wertyfire
 * */
public interface IItemCombinedEventHandler {
    /**
     * Handler itself
     * */
    void itemCombined(EntityPlayer player, ItemStack result);
}