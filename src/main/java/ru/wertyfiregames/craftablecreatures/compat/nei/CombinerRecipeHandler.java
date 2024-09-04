/**
 * File created on 19:29 30.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiCombiner;
import ru.wertyfiregames.craftablecreatures.recipe.CombinerRecipes;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CombinerRecipeHandler extends TemplateRecipeHandler {
    public static final String COMBINING_ID = CraftableCreatures.getModId() + ":combining";
    public static final String C_COMBINING_OVERLAY_IDENTIFIER = CraftableCreatures.getModId() + ":combiner";

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(55, 22, 55, 17), COMBINING_ID));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiCombiner.class;
    }

    @Override
    public String getRecipeName() {
        return I18n.format("craftableCreatures.nei.recipe.combining");
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(COMBINING_ID) && getClass() == CombinerRecipeHandler.class) {
            Map<Map<ItemStack, ItemStack>, ItemStack> recipes = CombinerRecipes.get().getCombiningRecipes();
            for (Map.Entry<Map<ItemStack, ItemStack>, ItemStack> recipe : recipes.entrySet()) {
                for (Map.Entry<ItemStack, ItemStack> entry : recipe.getKey().entrySet())
                    arecipes.add(new CachedCombinerRecipe(entry.getKey(), entry.getValue(), recipe.getValue()));
            }
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<Map<ItemStack, ItemStack>, ItemStack> recipes = CombinerRecipes.get().getCombiningRecipes();
        for (Map.Entry<Map<ItemStack, ItemStack>, ItemStack> recipe : recipes.entrySet()) {
            if (NEIServerUtils.areStacksSameType(recipe.getValue(), result)) {
                for (Map.Entry<ItemStack, ItemStack> entry : recipe.getKey().entrySet())
                    arecipes.add(new CachedCombinerRecipe(entry.getKey(), entry.getValue(), recipe.getValue()));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<Map<ItemStack, ItemStack>, ItemStack> recipes = CombinerRecipes.get().getCombiningRecipes();
        for (Map<ItemStack, ItemStack> recipe : recipes.keySet()) {
            for (Map.Entry<ItemStack, ItemStack> entry : recipe.entrySet()) {
                if (NEIServerUtils.areStacksSameTypeCrafting(entry.getKey(), ingredient)) {
                    CachedCombinerRecipe cachedRecipe =
                            new CachedCombinerRecipe(entry.getKey(), entry.getValue(), recipes.get(recipe));
                    List<PositionedStack> lp = new ArrayList<>();
                    lp.add(new PositionedStack(entry.getKey(), 49, 15));
                    lp.add(new PositionedStack(entry.getValue(), 111, 15));
                    cachedRecipe.setIngredientPermutation(lp, ingredient);
                    arecipes.add(cachedRecipe);
                } else if (NEIServerUtils.areStacksSameTypeCrafting(entry.getValue(), ingredient)) {
                    CachedCombinerRecipe cachedRecipe =
                            new CachedCombinerRecipe(entry.getKey(), entry.getValue(), recipes.get(recipe));
                    List<PositionedStack> lp = new ArrayList<>();
                    lp.add(new PositionedStack(entry.getKey(), 49, 15));
                    lp.add(new PositionedStack(entry.getValue(), 111, 15));
                    cachedRecipe.setIngredientPermutation(lp, ingredient);
                    arecipes.add(cachedRecipe);
                }
            }
        }
    }

    @Override
    public String getGuiTexture() {
        return CraftableCreatures.getModId() + ":textures/gui/combiner.png";
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(55, 22, 176, 0, 55, 17, 48, 1);
    }

    @Override
    public String getOverlayIdentifier() {
        return C_COMBINING_OVERLAY_IDENTIFIER;
    }

    public class CachedCombinerRecipe extends CachedRecipe {
        public PositionedStack firstIngredient;
        public PositionedStack secondIngredient;
        public PositionedStack output;

        public CachedCombinerRecipe(ItemStack firstIngredient, ItemStack secondIngredient, ItemStack output) {
            firstIngredient.stackSize = 1;
            secondIngredient.stackSize = 1;
            this.firstIngredient = new PositionedStack(firstIngredient, 44, 4);
            this.secondIngredient = new PositionedStack(secondIngredient, 106, 4);
            this.output = new PositionedStack(output, 75, 44);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(firstIngredient, secondIngredient));
        }

        public PositionedStack getResult() {
            return output;
        }
    }
}