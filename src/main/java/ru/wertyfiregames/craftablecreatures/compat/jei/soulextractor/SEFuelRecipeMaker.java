/**
 * File created on 22:26 20.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SEFuelRecipeMaker {
    @Nonnull
    public static List<SEFuelRecipe> getSEFuelRecipes(@Nonnull IItemRegistry itemRegistry, @Nonnull IJeiHelpers helpers) {
        IGuiHelper guiHelper = helpers.getGuiHelper();
        IStackHelper stackHelper = helpers.getStackHelper();
        List<ItemStack> itemList = itemRegistry.getItemList();
        List<SEFuelRecipe> fuelRecipes = new ArrayList<>();
        for (ItemStack stack : itemList) {
            if (stack == null) continue;

            if (TileEntitySoulExtractor.isItemFuel(stack) && TileEntitySoulExtractor.getFuelWorkTime(stack) > 0) {
                List<ItemStack> fuels = stackHelper.getSubtypes(stack);
                int workTime = getWorkTime(fuels.get(0));
                fuelRecipes.add(new SEFuelRecipe(guiHelper, fuels, workTime));
            }
        }

        return fuelRecipes;
    }

    private static int getWorkTime(ItemStack itemStack) {
        return TileEntitySoulExtractor.getFuelWorkTime(itemStack);
    }
}