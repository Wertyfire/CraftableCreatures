/**
 * File created on 15:54 22.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.*;

public class SlotSoulExtractor extends Slot {
    private EntityPlayer thePlayer;

    public SlotSoulExtractor(EntityPlayer player, IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
        thePlayer = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public void onPickupFromSlot(ItemStack stack) {
        stack.onCrafting(thePlayer.worldObj, thePlayer);
        thePlayer.triggerAchievement(mod_CraftableCreatures.extractSoulAch);
        super.onPickupFromSlot(stack);
    }
}