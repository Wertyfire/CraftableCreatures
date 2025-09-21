/**
 * File created on 14:30 21.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SERecipeMaker {
    @Nonnull
    public static List<SERecipe> getSoulExtractorRecipes(IJeiHelpers jeiHelpers) {
        IStackHelper stackHelper = jeiHelpers.getStackHelper();
        SoulExtractorRecipes soulExtractorRecipes = SoulExtractorRecipes.get();
        Map<ItemStack, ItemStack> extractingMap = soulExtractorRecipes.getExtractingRecipes();

        List<SERecipe> recipes = new ArrayList<>();

        for (Map.Entry<ItemStack, ItemStack> entry : extractingMap.entrySet()) {
            ItemStack input = entry.getKey();
            ItemStack output = entry.getValue();

            float experience = soulExtractorRecipes.getExtractingExperience(output);

            List<ItemStack> inputs = stackHelper.getSubtypes(input);
            SERecipe recipe = new SERecipe(inputs, output, experience);
            recipes.add(recipe);
        }

        return recipes;
    }
}