/**
 * File created on 22:13 20.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SEFuelRecipe extends BlankRecipeWrapper {
    @Nonnull
    private final List<List<ItemStack>> inputs;
    @Nonnull
    private final String workTimeString;
    @Nonnull
    private final IDrawableAnimated flame;

    public SEFuelRecipe(@Nonnull IGuiHelper guiHelper, @Nonnull Collection<ItemStack> input, int workTime) {
        List<ItemStack> inputList = new ArrayList<>(input);
        inputs = Collections.singletonList(inputList);
        workTimeString = Translator.translateToLocalFormatted("craftableCreatures.jei.recipe.fuel.tooltip", workTime);

        ResourceLocation soulExtractorBackgroundLocation = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/soul_extractor.png");
        IDrawableStatic flameDrawable = guiHelper.createDrawable(soulExtractorBackgroundLocation, 176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(flameDrawable, workTime, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Nonnull
    @Override
    public List<List<ItemStack>> getInputs() {
        return inputs;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRendererObj.drawString(workTimeString, 24, 12, Color.gray.getRGB());
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight) {
        flame.draw(minecraft, 2, -2);
    }
}