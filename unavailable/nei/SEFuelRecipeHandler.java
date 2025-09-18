/**
 * File created on 13:30 08.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.recipe.SoulExtractorRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SEFuelRecipeHandler extends SoulExtractorRecipeHandler {
    private final List<CachedSoulExtractorRecipe> seRecipes = new ArrayList<>();

    public SEFuelRecipeHandler() {
        super();
        loadAllExtracting();
    }

    @Override
    public String getRecipeName() {
        return I18n.format("nei.recipe.fuel");
    }

    public void loadAllExtracting() {
        Map<ItemStack, ItemStack> recipes = SoulExtractorRecipes.get().getExtractingRecipes();

        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            seRecipes.add(new CachedSoulExtractorRecipe(recipe.getKey(), recipe.getValue()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(SE_FUEL_ID) && getClass() == SEFuelRecipeHandler.class)
            for (Fuel fuel : listFuels)
                arecipes.add(new CachedSEFuelRecipe(fuel));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (Fuel fuel : listFuels)
            if (fuel.stack.contains(ingredient))
                arecipes.add(new CachedSEFuelRecipe(fuel));
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack stack, List<String> currentTip, int recipe) {
        CachedSEFuelRecipe cachedRecipe = (CachedSEFuelRecipe) arecipes.get(recipe);
        Fuel fuel = cachedRecipe.fuel;
        float workTime = fuel.workTime / 200f; //200 ticks for 1 soul

        if (gui.isMouseOver(fuel.stack, recipe)) {
            String wTime = Float.toString(workTime);
            if (workTime == Math.round(workTime)) wTime = Integer.toString((int) workTime);
            currentTip.add(I18n.format("craftableCreatures.nei.recipe.fuel.tooltip", wTime));
        }

        return currentTip;
    }

    public class CachedSEFuelRecipe extends CachedRecipe {
        public Fuel fuel;

        public CachedSEFuelRecipe(Fuel fuel) {
            this.fuel = fuel;
        }

        @Override
        public PositionedStack getIngredient() {
            return seRecipes.get(cycleticks / 48 % seRecipes.size()).ingredient;
        }

        @Override
        public PositionedStack getResult() {
            return seRecipes.get(cycleticks / 48 % seRecipes.size()).output;
        }

        @Override
        public List<PositionedStack> getOtherStacks() {
            List<PositionedStack> otherStacks = new ArrayList<>();
            otherStacks.add(listExtractHelpers.get((cycleticks / 48) % listExtractHelpers.size()).stack);
            otherStacks.add(fuel.stack);
            return otherStacks;
        }
    }
}