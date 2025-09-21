/**
 * File created on 21:57 20.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.minecraft.util.ResourceLocation;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

import javax.annotation.Nonnull;

public abstract class SoulExtractorRecipeCategory extends BlankRecipeCategory {
    protected static final int inputSlot = 0;
    protected static final int fuelSlot = 1;
    protected static final int extractHelperSlot = 2;
    protected static final int outputSlot = 3;

    protected final ResourceLocation backgroundLocation;
    @Nonnull
    protected final IDrawableAnimated flame;
    @Nonnull
    protected final IDrawableAnimated arrow;

    public SoulExtractorRecipeCategory(IGuiHelper guiHelper) {
        backgroundLocation = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/soul_extractor.png");

        IDrawableStatic flameDrawable = guiHelper.createDrawable(backgroundLocation, 176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 176, 0, 14, 14);
        arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }
}