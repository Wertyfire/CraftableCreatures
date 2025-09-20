/**
 * File created on 20:05 18.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.compat.jei.combining.CombiningRecipeCategory;
import ru.wertyfiregames.craftablecreatures.compat.jei.combining.CombiningRecipeHandler;
import ru.wertyfiregames.craftablecreatures.compat.jei.combining.CombiningRecipeMaker;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerCombiner;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiCombiner;

import javax.annotation.Nonnull;

@JEIPlugin
public class CraftableCreaturesJEIPlugin implements IModPlugin {
    public static final String COMBINING_ID = CraftableCreatures.getModId() + "." + "combining";

    public void register(@Nonnull IModRegistry registry) {
        //Categories
        registry.addRecipeCategories(new CombiningRecipeCategory(registry.getJeiHelpers().getGuiHelper()));

        //Handlers
        registry.addRecipeHandlers(new CombiningRecipeHandler());

        //Recipe click areas
        registry.addRecipeClickArea(GuiCombiner.class, 60, 33, 55, 17, COMBINING_ID);

        //Transfers
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();

        transferRegistry.addRecipeTransferHandler(ContainerCombiner.class, COMBINING_ID, 0, 2, 3, 36);

        //Adding recipes
        registry.addRecipes(CombiningRecipeMaker.getCombinerRecipes(registry.getJeiHelpers()));

        //Blacklisting items
        registry.getJeiHelpers().getItemBlacklist().addItemToBlacklist(new ItemStack(CCBlocks.lit_soul_extractor));
        registry.getJeiHelpers().getItemBlacklist().addItemToBlacklist(new ItemStack(CCBlocks.lit_combiner));
    }

    public void onRuntimeAvailable(@Nonnull IJeiRuntime jeiRuntime) {}

    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {}
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {}
    public void onRecipeRegistryAvailable(@Nonnull IRecipeRegistry recipeRegistry) {}
}