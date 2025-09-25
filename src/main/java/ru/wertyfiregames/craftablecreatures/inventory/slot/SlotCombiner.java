/**
 * File created on 20:27 27.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.slot;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import ru.wertyfiregames.craftablecreatures.api.event.CraftableCreaturesEventFactory;
import ru.wertyfiregames.craftablecreatures.init.CCAchievementList;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;

public class SlotCombiner extends Slot {
    private final EntityPlayer player;
    private int xpGained;

    public SlotCombiner(EntityPlayer player, IInventory inventory, int index, int x, int y) {
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
            float xps = CombinerRecipes.get().getCombiningExperience(stack);
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

        CraftableCreaturesEventFactory.itemCombinedEvent(player, stack);

        player.addStat(CCAchievementList.combineItem);
    }
}