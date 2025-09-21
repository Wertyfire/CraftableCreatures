/**
 * File created on 23:07 20.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat.jei.soulextractor;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.compat.jei.CraftableCreaturesJEIPlugin;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import javax.annotation.Nonnull;

public class SoulExtractorExtractingCategory extends SoulExtractorRecipeCategory {
    @Nonnull
    private final IDrawable background;
    @Nonnull
    private final String localizedName;

    public SoulExtractorExtractingCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        ResourceLocation location = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/soul_extractor.png");
        background = guiHelper.createDrawable(location, 40, 16, 97, 54);
        localizedName = Translator.translateToLocal("craftableCreatures.nei.recipe.soulExtracting");
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft) {
        flame.draw(minecraft, 2, 20);
        arrow.draw(minecraft, 20, 4);
    }

    @Nonnull
    @Override
    public String getTitle() {
        return localizedName;
    }

    @Nonnull
    @Override
    public String getUid() {
        return CraftableCreaturesJEIPlugin.SOUL_EXTRACTING_ID;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper) {
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

        itemStacks.init(inputSlot, true, 0, 0);
        itemStacks.init(extractHelperSlot, true, 37, 0);
        itemStacks.init(outputSlot, false, 75, 18);

        itemStacks.setFromRecipe(inputSlot, ((SERecipe) recipeWrapper).getInput());
        itemStacks.setFromRecipe(extractHelperSlot, new ItemStack(CCItems.soul, 1, 0));
        itemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
    }
}