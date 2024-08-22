/**
 * File created on 18:27 05.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.compat.CraftableCreaturesRegistry;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.inventory.gui.GuiSoulExtractor;

public class NEICraftableCreaturesConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        CraftableCreatures.getModLogger().info("Injecting recipes data and other stuff into NEI");
        hideItems();
        loadSubsets();
        registerHandlers();
        CraftableCreatures.getModLogger().info(("NEI compatibility loaded"));
    }

// <!---------- Registering methods ------->
    private void hideItems() {
        API.hideItem(new ItemStack(CCBlocks.lit_soul_extractor));
    }

    private void loadSubsets() {
        addSoulsCategory();
    }
    private void addSoulsCategory() {
        API.addSubset(I18n.format("craftableCreatures.nei.subsets.souls"), item -> CraftableCreaturesRegistry.isItemSoul(item.getItem()));
    }

    private void registerHandlers() {
        //Soul Extractor
        registerRecipeAndUsageHandler(new SoulExtractorRecipeHandler());
        registerRecipeAndUsageHandler(new SEFuelRecipeHandler());
        API.registerGuiOverlay(GuiSoulExtractor.class, SoulExtractorRecipeHandler.SE_EXTRACTING_OVERLAY_IDENTIFIER);
    }

// <!------------- Utils Zone ------------->
    private void registerRecipeAndUsageHandler(TemplateRecipeHandler handler) {
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
    }

// <!------------- NEI Plugin Info -------->
    @Override
    public String getName() {
        return "Craftable Creatures NEI plugin";
    }

    @Override
    public String getVersion() {
        return CraftableCreatures.getBuildNum();
    }
}