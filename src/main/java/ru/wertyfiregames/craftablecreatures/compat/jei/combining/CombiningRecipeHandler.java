/**
 * File created on 17:21 19.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.combining;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;

import javax.annotation.Nonnull;

public class CombiningRecipeHandler implements IRecipeHandler<CombiningRecipeWrapper> {
    @Nonnull
    @Override
    public Class<CombiningRecipeWrapper> getRecipeClass() {
        return CombiningRecipeWrapper.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return CraftableCreaturesJEIPlugin.COMBINING_ID;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(CombiningRecipeWrapper recipe) {
        return CraftableCreaturesJEIPlugin.COMBINING_ID;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull CombiningRecipeWrapper recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull CombiningRecipeWrapper recipe) {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no inputs. ", recipeInfo);
        }
        if (recipe.getOutputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no outputs. {}", recipeInfo);
        }
        return true;
    }
}