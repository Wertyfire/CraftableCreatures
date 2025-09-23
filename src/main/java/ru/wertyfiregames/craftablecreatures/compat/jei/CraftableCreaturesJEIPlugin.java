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
import ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor.*;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerCombiner;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiCombiner;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiSoulExtractor;

import javax.annotation.Nonnull;

@JEIPlugin
public class CraftableCreaturesJEIPlugin extends BlankModPlugin {
    public static final String SOUL_EXTRACTING_ID = CraftableCreatures.getModId() + "." + "soul_extracting";
    public static final String SE_FUEL_ID = CraftableCreatures.getModId() + "." + "soul_extracting.fuel";
    public static final String COMBINING_ID = CraftableCreatures.getModId() + "." + "combining";

    public void register(@Nonnull IModRegistry registry) {
        //Categories
        registry.addRecipeCategories(new SoulExtractorExtractingCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SoulExtractorFuelCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new CombiningRecipeCategory(registry.getJeiHelpers().getGuiHelper()));

        //Handlers
        registry.addRecipeHandlers(new SERecipeHandler());
        registry.addRecipeHandlers(new SEFuelRecipeHandler());
        registry.addRecipeHandlers(new CombiningRecipeHandler());

        //Recipe click areas
        registry.addRecipeClickArea(GuiSoulExtractor.class, 60, 20, 14, 15, SOUL_EXTRACTING_ID, SE_FUEL_ID);
        registry.addRecipeClickArea(GuiSoulExtractor.class, 60, 36, 42, 15, SOUL_EXTRACTING_ID, SE_FUEL_ID);
        registry.addRecipeClickArea(GuiCombiner.class, 60, 33, 55, 17, COMBINING_ID);

        //Transfers
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();

        transferRegistry.addRecipeTransferHandler(new SERecipeTransferInfo());
        transferRegistry.addRecipeTransferHandler(ContainerSoulExtractor.class, SE_FUEL_ID, 1, 1, 4, 36);
        transferRegistry.addRecipeTransferHandler(ContainerCombiner.class, COMBINING_ID, 0, 2, 3, 36);

        //Adding recipes
        registry.addRecipes(SERecipeMaker.getSoulExtractorRecipes(registry.getJeiHelpers()));
        registry.addRecipes(SEFuelRecipeMaker.getSEFuelRecipes(registry.getItemRegistry(), registry.getJeiHelpers()));
        registry.addRecipes(CombiningRecipeMaker.getCombinerRecipes(registry.getJeiHelpers()));

        //Blacklisting items
        registry.getJeiHelpers().getItemBlacklist().addItemToBlacklist(new ItemStack(CCBlocks.lit_soul_extractor));
        registry.getJeiHelpers().getItemBlacklist().addItemToBlacklist(new ItemStack(CCBlocks.lit_combiner));
    }
}