/**
 * File created on 16:28 17.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.recipe;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

import java.util.Map;
import java.util.Map.Entry;

public class SoulExtractorRecipes {
    private static final SoulExtractorRecipes instance = new SoulExtractorRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> extractingList = HashBasedTable.create();
    private final Map<ItemStack, Float> experienceList = Maps.newHashMap();

    public SoulExtractorRecipes() {
        addExtractingRecipe(new ItemStack(CCItems.SOUL_ELEMENT),
                new ItemStack(CCItems.SOUL_ELEMENT), new ItemStack(CCItems.BAT_WING), 10F);
    }

    public static SoulExtractorRecipes get() {
        return instance;
    }

    public void addExtractingRecipe(ItemStack output, ItemStack inputSoulType, ItemStack input, float exp) {
        if (getExtractingResult(inputSoulType, input) != net.minecraft.item.ItemStack.EMPTY) return;
        this.extractingList.put(inputSoulType, input, output);
        this.experienceList.put(output, exp);
    }

    public ItemStack getExtractingResult(ItemStack soul, ItemStack input) {
        for (Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.extractingList.columnMap().entrySet()) {
            if (this.compareItemStacks(soul, entry.getKey())) {
                for (Entry<ItemStack, ItemStack> entry1 : entry.getValue().entrySet()) {
                    if (this.compareItemStacks(input, entry1.getKey())) {
                        return entry1.getValue();
                    }
                }
            }
        }

        return net.minecraft.item.ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public Table<ItemStack, ItemStack, ItemStack> getDualExtractingList() {
        return this.extractingList;
    }

    public float getExtractingExp(ItemStack stack) {
        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
            if (this.compareItemStacks(stack, entry.getKey())) {
                return entry.getValue();
            }
        }

        return 0.0F;
    }
}