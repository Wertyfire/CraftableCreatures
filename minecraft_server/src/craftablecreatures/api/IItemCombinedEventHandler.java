/**
 * File created on 20:50 09.09.2026 by Wertyfire
 */

package craftablecreatures.api;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface IItemCombinedEventHandler {
    void itemCombined(EntityPlayer player, ItemStack result);
}