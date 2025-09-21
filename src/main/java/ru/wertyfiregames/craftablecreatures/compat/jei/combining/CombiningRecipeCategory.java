/**
 * File created on 14:49 19.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.combining;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;

import javax.annotation.Nonnull;

public class CombiningRecipeCategory extends BlankRecipeCategory {
    private static final int firstInputSlot = 0;
    private static final int secondInputSlot = 1;
    private static final int outputSlot = 2;

    private static final int outputSlotX = 31;
    private static final int outputSlotY = 41;

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final IDrawableAnimated arrow;

    public CombiningRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/combiner.png");
        background = guiHelper.createDrawable(location, 48, 13, 80, 63, 0, 0, 0, 40);
        localizedName = Translator.translateToLocal("craftableCreatures.nei.recipe.combining");

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 177, 0, 54, 17);
        arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Nonnull
    @Override
    public String getUid() {
        return CraftableCreaturesJEIPlugin.COMBINING_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {
        arrow.draw(minecraft, 13, 20);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(firstInputSlot, true, 0, 1);
        itemStacks.init(secondInputSlot, true, 62, 1);
        itemStacks.init(outputSlot, false, outputSlotX, outputSlotY);

        CombiningRecipe wrapper = (CombiningRecipe) recipeWrapper;
        itemStacks.setFromRecipe(firstInputSlot, wrapper.getFirstInputs());
        itemStacks.setFromRecipe(secondInputSlot, wrapper.getSecondInputs());
        itemStacks.setFromRecipe(outputSlot, wrapper.getOutputs());
    }
}