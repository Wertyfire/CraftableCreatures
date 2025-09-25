/**
 * File created on 17:26 19.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.combining;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CombiningRecipeMaker {
    @Nonnull
    public static List<CombiningRecipeWrapper> getCombinerRecipes(IJeiHelpers helpers) {
        IStackHelper stackHelper = helpers.getStackHelper();
        CombinerRecipes combinerRecipes = CombinerRecipes.get();
        Map<Pair<ItemStack, ItemStack>, ItemStack> combiningMap = combinerRecipes.getCombiningRecipes();

        List<CombiningRecipeWrapper> recipes = new ArrayList<>();

        for (Map.Entry<Pair<ItemStack, ItemStack>, ItemStack> entry : combiningMap.entrySet()) {
            ItemStack firstInput = entry.getKey().getKey();
            ItemStack secondInput = entry.getKey().getValue();
            ItemStack output = entry.getValue();

            float experience = combinerRecipes.getCombiningExperience(output);

            List<ItemStack> firstInputs = stackHelper.getSubtypes(firstInput);
            List<ItemStack> secondInputs = stackHelper.getSubtypes(secondInput);
            CombiningRecipeWrapper recipe = new CombiningRecipeWrapper(firstInputs, secondInputs, output, experience);
            recipes.add(recipe);
        }

        return recipes;
    }
}