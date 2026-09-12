/**
    File created on 20:36 09.09.2026 by Wertyfire
*/

package craftablecreatures.api;

import net.minecraft.src.ItemStack;

/**
 * Handler for soul extractor fuel. Used when calculating fuel work time.
 * @author Wertyfire
 * */
public interface ISEFuelHandler {
    /**
     * Get fuel burn time
     * @param fuel item in soul extractor fuel slot
     * */
    int getBurnTime(ItemStack fuel);
}