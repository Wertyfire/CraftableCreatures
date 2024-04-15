/**
 * File created on 16:58 09.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.guidebook.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.ItemDefault;

public class ItemGuideBook extends ItemDefault {
    public ItemGuideBook() {
        super("guideBook", "guide_book", false, CCCreativeTabs.tabCraftableCreatures);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handEnum) {
        return super.onItemRightClick(world, player, handEnum);
    }
}