/**
 * File created on 14:24 21.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;

import javax.annotation.Nonnull;

public class SERecipeHandler implements IRecipeHandler<SERecipe> {
    @Nonnull
    @Override
    public Class<SERecipe> getRecipeClass() {
        return SERecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return CraftableCreaturesJEIPlugin.SOUL_EXTRACTING_ID;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(SERecipe recipe) {
        return CraftableCreaturesJEIPlugin.SOUL_EXTRACTING_ID;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull SERecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull SERecipe recipe) {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no inputs. {}", recipeInfo);
        }
        if (recipe.getOutputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no outputs. {}", recipeInfo);
        }
        return true;
    }
}