/**
 * File created on 22:07 20.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;

import javax.annotation.Nonnull;

public class SoulExtractorFuelCategory extends SoulExtractorRecipeCategory {
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    public SoulExtractorFuelCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        background = guiHelper.createDrawable(backgroundLocation, 40, 38, 18, 32, 0, 0, 0, 80);
        localizedName = Translator.translateToLocal("gui.jei.category.fuel");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public String getUid() {
        return CraftableCreaturesJEIPlugin.SE_FUEL_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(fuelSlot, true, 0, 14);
        itemStacks.setFromRecipe(fuelSlot, recipeWrapper.getInputs());
    }
}