/**
 * File created on 22:24 08.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.*;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;

public class SlotCombiner extends Slot {
    private EntityPlayer thePlayer;
    private int removeCount;

    public SlotCombiner(EntityPlayer player, IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
        thePlayer = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        if (getHasStack()) removeCount += Math.min(amount, getStack().stackSize);
        return super.decrStackSize(amount);
    }

    @Override
    public void onPickupFromSlot(ItemStack stack) {
        func_48434_c(stack);
        super.onPickupFromSlot(stack);
    }

    @Override
    protected void func_48435_a(ItemStack stack, int amount) {
        removeCount += amount;
        func_48434_c(stack);
    }

    @Override
    protected void func_48434_c(ItemStack stack) {
        stack.onCrafting(thePlayer.worldObj, thePlayer, removeCount);
        removeCount = 0;
        thePlayer.triggerAchievement(mod_CraftableCreatures.combineItem);
        CraftableCreaturesRegistry.onItemCombinedEvent(thePlayer, stack);
    }
}