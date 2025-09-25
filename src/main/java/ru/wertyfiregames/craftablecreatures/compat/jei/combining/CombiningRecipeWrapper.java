/**
 * File created on 17:04 19.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.combining;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CombiningRecipeWrapper extends BlankRecipeWrapper {
    @Nonnull
    private final List<List<ItemStack>> input;
    @Nonnull
    private final List<List<ItemStack>> secondInput;
    @Nonnull
    private final List<ItemStack> outputs;

    @Nullable
    private final String experienceString;

    public CombiningRecipeWrapper(@Nonnull List<ItemStack> input, @Nonnull List<ItemStack> secInput, @Nonnull ItemStack output, float experience) {
        this.input = Collections.singletonList(input);
        secondInput = Collections.singletonList(secInput);
        outputs = Collections.singletonList(output);

        if (experience > 0.0f)
            experienceString = Translator.translateToLocalFormatted("craftableCreatures.jei.recipe.experience", experience);
        else experienceString = null;
    }

    @Nonnull
    public List<List<ItemStack>> getInputs() {
        List<List<ItemStack>> merged = new ArrayList<>();
        merged.addAll(input);
        merged.addAll(secondInput);
        return merged;
    }

    @Nonnull
    public List<List<ItemStack>> getFirstInputs() {
        return input;
    }

    @Nonnull
    public List<List<ItemStack>> getSecondInputs() {
        return secondInput;
    }

    @Nonnull
    @Override
    public List<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        if (experienceString != null) {
            FontRenderer fontRenderer = minecraft.fontRendererObj;
            int stringWidth = fontRenderer.getStringWidth(experienceString);
            fontRenderer.drawString(experienceString, recipeWidth - stringWidth - 40, 0, Color.GRAY.getRGB());
        }
    }
}