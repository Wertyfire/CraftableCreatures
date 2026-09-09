/**
    File created on 20:36 09.09.2026 by Wertyfire
*/

package craftablecreatures.api;

import net.minecraft.src.ItemStack;

public interface ISEFuelHandler {
    int getBurnTime(ItemStack fuel);
}