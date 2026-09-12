/**
 * File created on 20:49 09.09.2026 by Wertyfire
 */

package craftablecreatures.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

/**
 * Handler for soul extracted event called when player takes soul from soul extractor output slot.
 * Useful for addons that want to add some effects or achievements when player extracts soul.
 * @author Wertyfire
 * */
public interface ISoulExtractedEventHandler {
    /**
     * Handler itself
     * */
    void soulExtracted(EntityPlayer player, ItemStack result);
}