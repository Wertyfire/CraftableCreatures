/**
 * File created on 13:25 13.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.mod_CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.api.CCRegistrar;

public class CCRegistrarImpl implements CCRegistrar {
    @Override
    public void info(String message) {
        mod_CraftableCreatures.info(message);
    }
    @Override
    public void info(String message, Object... format) {
        mod_CraftableCreatures.info(message, format);
    }
    @Override
    public void err(String message) {
        mod_CraftableCreatures.err(message);
    }
    @Override
    public void err(String message, Object... format) {
        mod_CraftableCreatures.err(message, format);
    }

    @Override
    public void addExtracting(Block input, ItemStack output) {
        SoulExtractorRecipes.get().addRecipe(input.blockID, output);
    }
    @Override
    public void addExtracting(Item input, ItemStack output) {
        SoulExtractorRecipes.get().addRecipe(input.shiftedIndex, output);
    }
    @Override
    public void addExtracting(ItemStack input, ItemStack output) {
        SoulExtractorRecipes.get().addRecipe(input.itemID, output);
    }

    @Override
    public void addCombining(Item firstInput, Item secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
    @Override
    public void addCombining(ItemStack firstInput, Item secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
    @Override
    public void addCombining(Item firstInput, ItemStack secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
    @Override
    public void addCombining(ItemStack firstInput, ItemStack secondInput, ItemStack output) {
        CombinerRecipes.get().addRecipe(firstInput, secondInput, output);
    }
}