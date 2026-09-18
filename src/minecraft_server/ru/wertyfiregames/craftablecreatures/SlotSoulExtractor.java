/**
 * File created on 12:50 05.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.*;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;

public class SlotSoulExtractor extends Slot {
    private EntityPlayer thePlayer;
    private int removeCount;

    public SlotSoulExtractor(EntityPlayer player, IInventory inv, int index, int x, int y) {
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
        func_48416_b(stack);
        super.onPickupFromSlot(stack);
    }

    @Override
    protected void func_48415_a(ItemStack stack, int amount) {
        removeCount += amount;
        func_48416_b(stack);
    }

    @Override
    protected void func_48416_b(ItemStack stack) {
        stack.onCrafting(thePlayer.worldObj, thePlayer, removeCount);
        removeCount = 0;
        thePlayer.triggerAchievement(mod_CraftableCreatures.extractSoul);
        CraftableCreaturesRegistry.onSoulExtractedEvent(thePlayer, stack);
    }
}