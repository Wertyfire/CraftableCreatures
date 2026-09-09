/**
 * File created on 20:49 09.09.2026 by Wertyfire
 */

package craftablecreatures.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface ISoulExtractedEventHandler {
    void soulExtracted(EntityPlayer player, ItemStack result);
}