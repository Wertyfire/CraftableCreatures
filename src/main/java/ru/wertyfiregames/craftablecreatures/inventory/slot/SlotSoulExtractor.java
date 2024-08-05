/**
 * File created on 16:39 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.slot;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import ru.wertyfiregames.craftablecreatures.compat.SoulExtractorRecipes;
import ru.wertyfiregames.craftablecreatures.compat.event.CraftableCreaturesEventFactory;

public class
SlotSoulExtractor extends Slot {
    private final EntityPlayer player;
    private int xpGained;

    public SlotSoulExtractor(EntityPlayer player, IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = player;
    }

    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    public ItemStack decrStackSize(int value) {
        if (getHasStack()) xpGained += Math.min(value, getStack().stackSize);

        return super.decrStackSize(value);
    }

    public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
        onCrafting(stack);
        super.onPickupFromSlot(player, stack);
    }

    protected void onCrafting(ItemStack stack, int value) {
        xpGained += value;
        onCrafting(stack);
    }

    protected void onCrafting(ItemStack stack) {
        stack.onCrafting(player.worldObj, player, xpGained);

        if (!player.worldObj.isRemote) {
            int remainingXp = xpGained;
            float xps = SoulExtractorRecipes.get().getExtractingExperience(stack);
            int orbs;

            if (xps == 0f) remainingXp = 0;
            else if (xps < 1f) {
                orbs = MathHelper.floor_float(remainingXp * xps);
                if (orbs < MathHelper.ceiling_float_int(remainingXp * xps) && Math.random() < remainingXp * xps - orbs) ++orbs;
                remainingXp = orbs;
            }

            while (remainingXp > 0) {
                orbs = EntityXPOrb.getXPSplit(remainingXp);
                remainingXp -= orbs;
                player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj, player.posX, player.posY + 0.5d, player.posZ + 0.5d, orbs));
            }
        }

        xpGained = 0;

        CraftableCreaturesEventFactory.soulExtractedEvent(player, stack);
    }
}