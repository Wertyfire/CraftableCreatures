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
        addBaseSoul(new ItemStack(CCItems.soul, 1, 0));

        addRecipe(Items.gunpowder, new ItemStack(CCItems.soul, 1, 1), 0.15f);
        addRecipe(Items.bone, new ItemStack(CCItems.soul, 1, 2), 0.25f);
        addRecipe(Items.spider_eye, new ItemStack(CCItems.soul, 1, 3), 0.25f);
        addRecipe(Items.fermented_spider_eye, new ItemStack(CCItems.soul, 1, 3), 0.25f);
        addRecipe(Items.rotten_flesh, new ItemStack(CCItems.soul, 1, 4), 0.1f);
        addRecipe(Items.slime_ball, new ItemStack(CCItems.soul, 1, 5), 0.1f);
        addRecipe(Items.ghast_tear, new ItemStack(CCItems.soul, 1, 6), 0.175f);
        addRecipe(Items.golden_sword, new ItemStack(CCItems.soul, 1, 7), 0.25f);
        addRecipe(Items.ender_pearl, new ItemStack(CCItems.soul, 1, 8), 0.175f);
        ItemStack stack1 = new ItemStack(CCItems.soul);
        stack1.setItemDamage(3);
        addRecipe(stack1, new ItemStack(CCItems.soul, 1, 9), 0.25f);
        addRecipe(Blocks.monster_egg, new ItemStack(CCItems.soul, 1, 10), 0.25f);
        addRecipe(Items.blaze_rod, new ItemStack(CCItems.soul, 1, 11), 0.2f);
        addRecipe(Items.magma_cream, new ItemStack(CCItems.soul, 1, 12), 0.15f);
        addRecipe(CCItems.bat_wing, new ItemStack(CCItems.soul, 1, 13), 0.05f);
        addRecipe(Items.potionitem, new ItemStack(CCItems.soul, 1, 14), 0.25f);
        addRecipe(Items.porkchop, new ItemStack(CCItems.soul, 1, 15), 0.05f);
        ItemStack stack2 = new ItemStack(Blocks.wool);
        stack2.setItemDamage(OreDictionary.WILDCARD_VALUE);
        addRecipe(stack2, new ItemStack(CCItems.soul, 1, 16), 0.05f);
        addRecipe(Items.beef, new ItemStack(CCItems.soul, 1, 17), 0.05f);
        addRecipe(Items.milk_bucket, new ItemStack(CCItems.soul, 1, 17), 0.05f);
        addRecipe(Items.chicken, new ItemStack(CCItems.soul, 1, 18), 0.05f);
        addRecipe(Items.egg, new ItemStack(CCItems.soul, 1, 18), 0.05f);
        addRecipe(Items.feather, new ItemStack(CCItems.soul, 1, 18), 0.05f);
        addRecipe(Items.dye, new ItemStack(CCItems.soul, 1, 19), 0.025f);
        ItemStack stack3 = new ItemStack(CCItems.soul);
        stack3.setItemDamage(2);
        addRecipe(stack3, new ItemStack(CCItems.soul, 1, 20), 0.25f);
        addRecipe(Blocks.red_mushroom, new ItemStack(CCItems.soul, 1, 21), 0.25f);
        addRecipe(CCItems.ocelot_tail, new ItemStack(CCItems.soul, 1, 22), 0.15f);
        ItemStack stack4 = new ItemStack(CCItems.soul);
        stack4.setItemDamage(8);
        addRecipe(stack4, new ItemStack(CCItems.soul, 1, 25), 0.15f);
        addRecipe(Items.prismarine_shard, new ItemStack(CCItems.soul, 1, 26), 0.2f);
        addRecipe(Items.prismarine_crystals, new ItemStack(CCItems.soul, 1, 26), 0.2f);
        addRecipe(Items.rabbit, new ItemStack(CCItems.soul, 1, 27), 0.05f);
        addRecipe(Items.rabbit_foot, new ItemStack(CCItems.soul, 1, 27), 0.05f);
        addRecipe(Items.rabbit_hide, new ItemStack(CCItems.soul, 1, 27), 0.05f);
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
        experienceList.put(output.copy(), xp * 5f);
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