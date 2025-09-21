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
import ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor.SEFuelRecipeHandler;
import ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor.SEFuelRecipeMaker;
import ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor.SoulExtractorFuelCategory;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerCombiner;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiCombiner;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiSoulExtractor;

import javax.annotation.Nonnull;

@JEIPlugin
public class CraftableCreaturesJEIPlugin implements IModPlugin {
    public static final String SOUL_EXTRACTING_ID = CraftableCreatures.getModId() + "." + "soul_extracting";
    public static final String SE_FUEL_ID = CraftableCreatures.getModId() + "." + "soul_extracting.fuel";
    public static final String COMBINING_ID = CraftableCreatures.getModId() + "." + "combining";

    public void register(@Nonnull IModRegistry registry) {
        //Categories
        registry.addRecipeCategories(new SoulExtractorFuelCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CombiningRecipeCategory(registry.getJeiHelpers().getGuiHelper()));

        //Handlers
        registry.addRecipeHandlers(new SEFuelRecipeHandler());
        registry.addRecipeHandlers(new CombiningRecipeHandler());

        //Recipe click areas
        registry.addRecipeClickArea(GuiSoulExtractor.class, 78, 32, 28, 23, SE_FUEL_ID);
        registry.addRecipeClickArea(GuiCombiner.class, 60, 33, 55, 17, COMBINING_ID);

        //Transfers
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();

        transferRegistry.addRecipeTransferHandler(ContainerSoulExtractor.class, SE_FUEL_ID, 1, 1, 4, 36);
        transferRegistry.addRecipeTransferHandler(ContainerCombiner.class, COMBINING_ID, 0, 2, 3, 36);

        //Adding recipes
        registry.addRecipes(SEFuelRecipeMaker.getSEFuelRecipes(registry.getItemRegistry(), registry.getJeiHelpers()));
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