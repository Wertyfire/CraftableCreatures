/**
 * File created on 17:38 29.04.2026 by Wertyfire
 */

package craftablecreatures;

import craftablecreatures.api.CraftableCreaturesRegistry;
import net.minecraft.src.*;

public class SlotCombiner extends Slot {
    private EntityPlayer thePlayer;

    public SlotCombiner(EntityPlayer player, IInventory inv, int index, int x, int y) {
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
        thePlayer.triggerAchievement(mod_CraftableCreatures.combineItemAch);
        CraftableCreaturesRegistry.onItemCombinedEvent(thePlayer, stack);
        super.onPickupFromSlot(stack);
    }
}