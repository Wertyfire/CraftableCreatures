/**
 * File created on 14:11 21.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SERecipe extends BlankRecipeWrapper {
    @Nonnull
    private final List<List<ItemStack>> input;
    @Nonnull
    private final List<ItemStack> outputs;

    private final String experienceString;

    public SERecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output, float experience) {
        this.input = Collections.singletonList(input);
        this.outputs = Collections.singletonList(output);

        if (experience > 0.0f)
            experienceString = Translator.translateToLocalFormatted("craftableCreatures.jei.recipe.experience", experience);
        else experienceString = null;
    }

    @Nonnull
    public List<List<ItemStack>> getInputs() {
        List<List<ItemStack>> merged = new ArrayList<>(input);
        merged.add(new ArrayList<>(Collections.singleton(new ItemStack(CCItems.soul, 1, 0))));
        return merged;
    }

    @Nonnull
    public List<List<ItemStack>> getInput() {
        return input;
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
            fontRenderer.drawString(experienceString, recipeWidth - stringWidth, 46, Color.gray.getRGB());
        }
    }
}