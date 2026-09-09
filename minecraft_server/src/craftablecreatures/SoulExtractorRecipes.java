/**
 * File created on 22:32 17.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.mod_CraftableCreatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoulExtractorRecipes {
    private static final SoulExtractorRecipes extractingBase = new SoulExtractorRecipes();
    private final Map<Integer, ItemStack> extractingList = new HashMap<>();
    private final List<ItemStack> extractHelpers = new ArrayList<>();

    public static SoulExtractorRecipes get() {
        return extractingBase;
    }

    private SoulExtractorRecipes() {
        addBaseSoul(new ItemStack(mod_CraftableCreatures.soulElement, 1, 0));

        extractingList.put(Block.slowSand.blockID, new ItemStack(mod_CraftableCreatures.soulElement, 2, 0));
        addRecipe(Block.mushroomRed, 1);
        addRecipe(Item.gunpowder, 2);
        addRecipe(Item.bone, 3);
        addRecipe(Item.spiderEye, 4);
        addRecipe(Item.rottenFlesh, 5);
        addRecipe(Item.slimeBall, 6);
        addRecipe(Item.ghastTear, 7);
        addRecipe(Item.swordGold, 9);
        addRecipe(Item.enderPearl, 10);
        addRecipe(Item.porkRaw, 11);
        addRecipe(Item.fermentedSpiderEye, 12);
        addRecipe(Block.cloth, 13);
        addRecipe(Item.beefRaw, 15);
        addRecipe(Item.bucketMilk, 15);
        addRecipe(Item.leather, 15);
        addRecipe(Item.blazeRod, 16);
        addRecipe(Item.chickenRaw, 17);
        addRecipe(Item.egg, 17);
        addRecipe(Item.feather, 17);
        addRecipe(Item.magmaCream, 18);
    }

    public void addRecipe(Item item, int soulIndex) {
        extractingList.put(item.shiftedIndex, new ItemStack(mod_CraftableCreatures.soulElement, 1, soulIndex));
    }
    public void addRecipe(Block block, int soulIndex) {
        extractingList.put(block.blockID, new ItemStack(mod_CraftableCreatures.soulElement, 1, soulIndex));
    }
    public void addRecipe(int input, ItemStack output) {
        extractingList.put(input, output);
    }

    public void addBaseSoul(ItemStack helper) {
        extractHelpers.add(helper);
    }

    public ItemStack getExtractingResult(ItemStack ingredient) {
        if (ingredient == null) return null;
        if (ingredient.isItemEqual(new ItemStack(Item.dyePowder)))
            return new ItemStack(mod_CraftableCreatures.soulElement, 1, 19);
        else if (ingredient.isItemEqual(new ItemStack(mod_CraftableCreatures.soulElement, 1, 3)))
            return new ItemStack(mod_CraftableCreatures.soulElement, 1, 20);
        return extractingList.get(ingredient.itemID);
    }

    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == -1
                || stack2.getItemDamage() == stack1.getItemDamage());
    }

    public boolean isItemExtractHelper(ItemStack stack) {
        return extractHelpers.stream().anyMatch(helperStack -> areStacksEqual(helperStack, stack));
    }

    public Map<Integer, ItemStack> getExtractingList() {
        return extractingList;
    }
}