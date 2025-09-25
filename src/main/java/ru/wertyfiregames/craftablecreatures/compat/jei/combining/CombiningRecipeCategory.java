/**
 * File created on 14:49 19.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.combining;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;

import javax.annotation.Nonnull;

public class CombiningRecipeCategory extends BlankRecipeCategory<CombiningRecipeWrapper> {
    private static final int firstInputSlot = 0;
    private static final int secondInputSlot = 1;
    private static final int outputSlot = 2;

    private static final int outputSlotX = 69;
    private static final int outputSlotY = 18;

    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;
    @Nonnull
    private final IDrawableAnimated arrow;

    public CombiningRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/combiner.png");
        background = guiHelper.createDrawable(location, 0, 166, 91, 54, 0, 0, 0, 40);
        localizedName = Translator.translateToLocal("craftableCreatures.jei.recipe.combining");

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, 176, 17, 40, 30);
        arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
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
        arrow.draw(minecraft, 21, 12);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CombiningRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(firstInputSlot, true, 0, 36);
        itemStacks.init(secondInputSlot, true, 0, 0);
        itemStacks.init(outputSlot, false, outputSlotX, outputSlotY);

        itemStacks.setFromRecipe(firstInputSlot, recipeWrapper.getFirstInputs());
        itemStacks.setFromRecipe(secondInputSlot, recipeWrapper.getSecondInputs());
        itemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
    }
}