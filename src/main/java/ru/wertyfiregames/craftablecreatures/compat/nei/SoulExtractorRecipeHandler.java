/**
    File created on 21:17 06.08.2024 by Wertyfire
*/

package ru.wertyfiregames.craftablecreatures.compat.nei;

import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.SoulExtractorRecipes;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SoulExtractorRecipeHandler extends TemplateRecipeHandler {
    public static final String SE_FUEL_ID = CraftableCreatures.getModId() + ":se_fuel";
    public static final String EXTRACTING_ID = CraftableCreatures.getModId() + ":extracting";
    public static final String SE_EXTRACTING_OVERLAY_IDENTIFIER = CraftableCreatures.getModId() + ":soul_extractor";


    public static List<Fuel> listFuels;
    public static List<ExtractHelper> listExtractHelpers;

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(37, 25, 14, 14), SE_FUEL_ID));
        transferRects.add(new RecipeTransferRect(new Rectangle(55, 9, 15, 31), EXTRACTING_ID));
        transferRects.add(new RecipeTransferRect(new Rectangle(70, 25, 28, 16), EXTRACTING_ID));
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiSoulExtractor.class;
    }

    @Override
    public String getRecipeName() {
        return I18n.format("craftableCreatures.nei.recipe.soulExtracting");
    }

    @Override
    public TemplateRecipeHandler newInstance() {
        if (listFuels == null || listFuels.isEmpty()) findFuels();
        if (listExtractHelpers == null || listExtractHelpers.isEmpty()) findExtractHelpers();
        return super.newInstance();
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(EXTRACTING_ID) && getClass() == SoulExtractorRecipeHandler.class) {
            Map<ItemStack, ItemStack> recipes = SoulExtractorRecipes.get().getExtractingRecipes();
            for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
                arecipes.add(new CachedSoulExtractorRecipe(recipe.getKey(), recipe.getValue()));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<ItemStack, ItemStack> recipes = SoulExtractorRecipes.get().getExtractingRecipes();
        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
                arecipes.add(new CachedSoulExtractorRecipe(recipe.getKey(), recipe.getValue()));
        }
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
        if (inputId.equals(SE_FUEL_ID) && getClass() == SoulExtractorRecipeHandler.class)
            loadCraftingRecipes(EXTRACTING_ID);
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<ItemStack, ItemStack> recipes = SoulExtractorRecipes.get().getExtractingRecipes();
        boolean found = false;
        for (Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey(), ingredient)) {
                CachedSoulExtractorRecipe cachedRecipe = new CachedSoulExtractorRecipe(recipe.getKey(), recipe.getValue());
                cachedRecipe.setIngredientPermutation(Collections.singletonList(cachedRecipe.ingredient), ingredient);
                arecipes.add(cachedRecipe);
            } else {
                for (ExtractHelper potentialSoulBase : listExtractHelpers)
                    if (NEIServerUtils.areStacksSameTypeCrafting(potentialSoulBase.stack.item, ingredient)) {
                        found = true;
                        break;
                    }
            }
        }

        if (found) loadCraftingRecipes(EXTRACTING_ID);
    }

    @Override
    public String getGuiTexture() {
        return CraftableCreatures.getModId() + ":textures/gui/container/soul_extractor.png";
    }

    @Override
    public void drawExtras(int recipe) {
        drawProgressBar(37, 25, 176, 0, 14, 14, 48, 7);
        drawProgressBar(55, 9, 176, 14, 43, 31, 48, 0);
    }

    private static void findFuels() {
        listFuels = new ArrayList<>();
        for (ItemStack potentialFuel : ItemList.items) {
            int workTime = TileEntitySoulExtractor.getFuelWorkTime(potentialFuel);
            if (workTime > 0) listFuels.add(new Fuel(potentialFuel.copy(), workTime));
        }
    }

    private static void findExtractHelpers() {
        listExtractHelpers = new ArrayList<>();
        for (ItemStack potentialExtractHelper : ItemList.items)  {
            if (SoulExtractorRecipes.get().isItemExtractHelper(potentialExtractHelper))
                listExtractHelpers.add(new ExtractHelper(potentialExtractHelper.copy()));
        }
    }

    @Override
    public String getOverlayIdentifier() {
        return SE_EXTRACTING_OVERLAY_IDENTIFIER;
    }

    public class CachedSoulExtractorRecipe extends CachedRecipe {
        public PositionedStack ingredient;
        public PositionedStack output;

        public CachedSoulExtractorRecipe(ItemStack ingredient, ItemStack output) {
            ingredient.stackSize = 1;
            this.ingredient = new PositionedStack(ingredient, 36, 6);
            this.output = new PositionedStack(output, 111, 24);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 48, Arrays.asList(ingredient, listExtractHelpers.get((cycleticks / 48) % listExtractHelpers.size()).stack));
        }

        public PositionedStack getResult() {
            return output;
        }

        @Override
        public PositionedStack getOtherStack() {
            return listFuels.get((cycleticks / 48) % listFuels.size()).stack;
        }
    }

    public static class Fuel {
        public PositionedStack stack;
        public int workTime;

        public Fuel(ItemStack ingredient, int workTime) {
            stack = new PositionedStack(ingredient, 36, 42);
            this.workTime = workTime;
        }
    }

    public static class ExtractHelper {
        public PositionedStack stack;

        public ExtractHelper(ItemStack helper) {
            stack = new PositionedStack(helper, 73, 6);
        }
    }
}