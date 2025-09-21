/**
 * File created on 17:06 21.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SERecipeTransferInfo implements IRecipeTransferInfo {
    @Override
    public Class<? extends Container> getContainerClass() {
        return ContainerSoulExtractor.class;
    }

    @Override
    public String getRecipeCategoryUid() {
        return CraftableCreaturesJEIPlugin.SOUL_EXTRACTING_ID;
    }

    @Override
    public List<Slot> getRecipeSlots(Container container) {
        return Arrays.asList(container.getSlot(0), container.getSlot(2));
    }

    @Override
    public List<Slot> getInventorySlots(Container container) {
        List<Slot> slots = new ArrayList<>();
        for (int i = 4; i < 40; i++) {
            Slot slot = container.getSlot(i);
            slots.add(slot);
        }
        return slots;
    }
}