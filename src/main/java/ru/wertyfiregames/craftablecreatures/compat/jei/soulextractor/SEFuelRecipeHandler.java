/**
 * File created on 22:24 20.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;

import javax.annotation.Nonnull;

public class SEFuelRecipeHandler implements IRecipeHandler<SEFuelRecipe> {
    @Nonnull
    @Override
    public Class<SEFuelRecipe> getRecipeClass() {
        return SEFuelRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid() {
        return CraftableCreaturesJEIPlugin.SE_FUEL_ID;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid(SEFuelRecipe recipe) {
        return CraftableCreaturesJEIPlugin.SE_FUEL_ID;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull SEFuelRecipe recipe) {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull SEFuelRecipe recipe) {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no inputs. {}", recipeInfo);
        }
        if (!recipe.getOutputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Fuel Recipe should not have outputs. {}", recipeInfo);
        }
        return true;
    }
}