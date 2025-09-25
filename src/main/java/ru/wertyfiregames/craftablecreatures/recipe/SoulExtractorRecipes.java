/**
 * File created on 19:08 31.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.recipe;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import java.util.*;

public class SoulExtractorRecipes {
    private static final SoulExtractorRecipes extractingBase = new SoulExtractorRecipes();
    private final Map<ItemStack, ItemStack> extractingRecipes = new HashMap<>();
    private final List<ItemStack> extractHelpers = new ArrayList<>();
    private final Map<ItemStack, Float> experienceList = new HashMap<>();

    private SoulExtractorRecipes() {
        addBaseSoul(new ItemStack(CCItems.SOUL, 1, 0));

        addRecipe(Items.GUNPOWDER, new ItemStack(CCItems.SOUL, 1, 1), 0.2f);
        addRecipe(Items.BONE, new ItemStack(CCItems.SOUL, 1, 2), 0.2f);
        addRecipe(Items.SPIDER_EYE, new ItemStack(CCItems.SOUL, 1, 3), 0.2f);
        addRecipe(Items.FERMENTED_SPIDER_EYE, new ItemStack(CCItems.SOUL, 1, 3), 0.2f);
        addRecipe(Items.ROTTEN_FLESH, new ItemStack(CCItems.SOUL, 1, 4), 0.2f);
        addRecipe(Items.SLIME_BALL, new ItemStack(CCItems.SOUL, 1, 5), 0.2f);
        addRecipe(Items.GHAST_TEAR, new ItemStack(CCItems.SOUL, 1, 6), 0.25f);
        addRecipe(Items.GOLDEN_SWORD, new ItemStack(CCItems.SOUL, 1, 7), 0.25f);
        addRecipe(Items.ENDER_PEARL, new ItemStack(CCItems.SOUL, 1, 8), 0.2f);
        ItemStack stack1 = new ItemStack(CCItems.SOUL);
        stack1.setItemDamage(3);
        addRecipe(stack1, new ItemStack(CCItems.SOUL, 1, 9), 0.25f);
        addRecipe(Blocks.MONSTER_EGG, new ItemStack(CCItems.SOUL, 1, 10), 0.3f);
        addRecipe(Items.BLAZE_ROD, new ItemStack(CCItems.SOUL, 1, 11), 0.25f);
        addRecipe(Items.MAGMA_CREAM, new ItemStack(CCItems.SOUL, 1, 12), 0.25f);
        addRecipe(CCItems.BAT_WING, new ItemStack(CCItems.SOUL, 1, 13), 0.1f);
        addRecipe(Items.POTIONITEM, new ItemStack(CCItems.SOUL, 1, 14), 0.25f);
        addRecipe(Items.PORKCHOP, new ItemStack(CCItems.SOUL, 1, 15), 0.1f);
        ItemStack stack2 = new ItemStack(Blocks.WOOL);
        stack2.setItemDamage(OreDictionary.WILDCARD_VALUE);
        addRecipe(stack2, new ItemStack(CCItems.SOUL, 1, 16), 0.1f);
        addRecipe(Items.MUTTON, new ItemStack(CCItems.SOUL, 1, 16), 0.1f);
        addRecipe(Items.BEEF, new ItemStack(CCItems.SOUL, 1, 17), 0.1f);
        addRecipe(Items.MILK_BUCKET, new ItemStack(CCItems.SOUL, 1, 17), 0.1f);
        addRecipe(Items.CHICKEN, new ItemStack(CCItems.SOUL, 1, 18), 0.1f);
        addRecipe(Items.EGG, new ItemStack(CCItems.SOUL, 1, 18), 0.1f);
        addRecipe(Items.FEATHER, new ItemStack(CCItems.SOUL, 1, 18), 0.1f);
        addRecipe(Items.DYE, new ItemStack(CCItems.SOUL, 1, 19), 0.1f);
        ItemStack stack3 = new ItemStack(CCItems.SOUL);
        stack3.setItemDamage(2);
        addRecipe(stack3, new ItemStack(CCItems.SOUL, 1, 20), 0.15f);
        addRecipe(Blocks.RED_MUSHROOM, new ItemStack(CCItems.SOUL, 1, 21), 0.15f);
        addRecipe(CCItems.OCELOT_TAIL, new ItemStack(CCItems.SOUL, 1, 22), 0.1f);
        ItemStack stack4 = new ItemStack(CCItems.SOUL);
        stack4.setItemDamage(8);
        addRecipe(stack4, new ItemStack(CCItems.SOUL, 1, 25), 0.15f);
        addRecipe(Items.PRISMARINE_SHARD, new ItemStack(CCItems.SOUL, 1, 26), 0.25f);
        addRecipe(Items.PRISMARINE_CRYSTALS, new ItemStack(CCItems.SOUL, 1, 26), 0.25f);
        addRecipe(Items.RABBIT, new ItemStack(CCItems.SOUL, 1, 27), 0.1f);
        addRecipe(Items.RABBIT_FOOT, new ItemStack(CCItems.SOUL, 1, 27), 0.1f);
        addRecipe(Items.RABBIT_HIDE, new ItemStack(CCItems.SOUL, 1, 27), 0.1f);
    }

    public static SoulExtractorRecipes get() {
        return extractingBase;
    }
    public Map<ItemStack, ItemStack> getExtractingRecipes() {
        return extractingRecipes;
    }

    public void addRecipe(Block input, ItemStack output, float xp) {
        addRecipe(Item.getItemFromBlock(input), output, xp);
    }
    public void addRecipe(Item input, ItemStack output, float xp) {
        addRecipe(new ItemStack(input, 1), output, xp);
    }
    public void addRecipe(String oreDictName, ItemStack output, float xp) {
        for (ItemStack stack : OreDictionary.getOres(oreDictName)) {
            addRecipe(stack, output, xp);
        }
    }
    public void addRecipe(ItemStack input, ItemStack output, float xp) {
        extractingRecipes.put(input.copy(), output);
        experienceList.put(output.copy(), xp);
    }
    private void addBaseSoul(ItemStack helper) {
        extractHelpers.add(helper.copy());
    }

    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE
                || stack2.getItemDamage() == stack1.getItemDamage());
    }

    public boolean isItemExtractHelper(ItemStack stack) {
        return extractHelpers.stream().anyMatch(helperStack -> areStacksEqual(helperStack, stack));
    }

    public ItemStack getExtractingResult(ItemStack ingredient) {
        for (Map.Entry<ItemStack, ItemStack> entry : extractingRecipes.entrySet()) {
            if (areStacksEqual(entry.getKey(), ingredient)) return entry.getValue();
        }
        return null;
    }
    public float getExtractingExperience(ItemStack result) {
        for (Map.Entry<ItemStack, Float> entry : experienceList.entrySet()) {
            if (areStacksEqual(entry.getKey(), result)) return entry.getValue();
        }
        return 0f;
    }
}