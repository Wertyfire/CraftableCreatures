/**
 * File created on 21:20 02.09.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemBlockDefault extends ItemBlock {
    public ItemBlockDefault(int id) {
        super(id);
    }

    @Override
    public String getItemDisplayName(ItemStack itemstack) {
        return TranslateUtils.translate(getItemNameIS(itemstack) + ".name");
    }

    @Override
    public String getStatName() {
        return TranslateUtils.translate(getItemName() + ".name");
    }
}