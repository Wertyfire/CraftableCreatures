/**
 * File created on 17:35 27.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.recipe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import java.util.HashMap;
import java.util.Map;

public class CombinerRecipes {
    private static final CombinerRecipes combiningBase = new CombinerRecipes();
    private final Map<Map<ItemStack, ItemStack>, ItemStack> combiningRecipes = new HashMap<>();
    private final Map<ItemStack, Float> experienceList = new HashMap<>();

    private CombinerRecipes() {
        addRecipe(soul(1), CCItems.spawn_egg_template, spawnEgg(new EntityCreeper(null)), 0.2f);
        addRecipe(soul(2), CCItems.spawn_egg_template, spawnEgg(new EntitySkeleton(null)), 0.2f);
        addRecipe(soul(3), CCItems.spawn_egg_template, spawnEgg(new EntitySpider(null)), 0.2f);
        addRecipe(soul(4), CCItems.spawn_egg_template, spawnEgg(new EntityZombie(null)), 0.2f);
        addRecipe(soul(5), CCItems.spawn_egg_template, spawnEgg(new EntitySlime(null)), 0.2f);
        addRecipe(soul(6), CCItems.spawn_egg_template, spawnEgg(new EntityGhast(null)), 0.2f);
        addRecipe(soul(7), CCItems.spawn_egg_template, spawnEgg(new EntityPigZombie(null)), 0.2f);
        addRecipe(soul(8), CCItems.spawn_egg_template, spawnEgg(new EntityEnderman(null)), 0.2f);
        addRecipe(soul(9), CCItems.spawn_egg_template, spawnEgg(new EntityCaveSpider(null)), 0.2f);
        addRecipe(soul(10), CCItems.spawn_egg_template, spawnEgg(new EntitySilverfish(null)), 0.2f);
        addRecipe(soul(11), CCItems.spawn_egg_template, spawnEgg(new EntityBlaze(null)), 0.2f);
        addRecipe(soul(12), CCItems.spawn_egg_template, spawnEgg(new EntityMagmaCube(null)), 0.2f);
        addRecipe(soul(13), CCItems.spawn_egg_template, spawnEgg(new EntityBat(null)), 0.05f);
        addRecipe(soul(14), CCItems.spawn_egg_template, spawnEgg(new EntityWitch(null)), 0.2f);
        addRecipe(soul(15), CCItems.spawn_egg_template, spawnEgg(new EntityPig(null)), 0.1f);
        addRecipe(soul(16), CCItems.spawn_egg_template, spawnEgg(new EntitySheep(null)), 0.1f);
        addRecipe(soul(17), CCItems.spawn_egg_template, spawnEgg(new EntityCow(null)), 0.1f);
        addRecipe(soul(18), CCItems.spawn_egg_template, spawnEgg(new EntityChicken(null)), 0.1f);
        addRecipe(soul(19), CCItems.spawn_egg_template, spawnEgg(new EntitySquid(null)), 0.1f);
        addRecipe(soul(20), CCItems.spawn_egg_template, spawnEgg(new EntityWolf(null)), 0.15f);
        addRecipe(soul(21), CCItems.spawn_egg_template, spawnEgg(new EntityMooshroom(null)), 0.15f);
        addRecipe(soul(22), CCItems.spawn_egg_template, spawnEgg(new EntityOcelot(null)), 0.1f);
        addRecipe(soul(23), CCItems.spawn_egg_template, new ItemStack(Items.spawn_egg, 1, 100), 0.1f); //horse
        addRecipe(soul(24), CCItems.spawn_egg_template, spawnEgg(new EntityVillager(null)), 0.1f);
    }

    public static CombinerRecipes get() {
        return combiningBase;
    }
    public Map<Map<ItemStack, ItemStack>, ItemStack> getCombiningRecipes() {
        return combiningRecipes;
    }

    public void addRecipe(Item firstInput, Item secondInput, ItemStack output, float xp) {
        addRecipe(new ItemStack(firstInput), new ItemStack(secondInput), output, xp);
    }
    public void addRecipe(ItemStack firstInput, Item secondInput, ItemStack output, float xp) {
        addRecipe(firstInput, new ItemStack(secondInput), output, xp);
    }
    public void addRecipe(Item firstInput, ItemStack secondInput, ItemStack output, float xp) {
        addRecipe(new ItemStack(firstInput), secondInput, output, xp);
    }
    public void addRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack output, float xp) {
        Map<ItemStack, ItemStack> ingredients = new HashMap<>();
        ingredients.put(firstInput.copy(), secondInput.copy());
        combiningRecipes.put(ingredients, output);
        experienceList.put(output.copy(), xp * 5f);
    }

    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.isItemEqual(stack2) && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }

    private ItemStack soul(int id) {
        return new ItemStack(CCItems.soul_element, 1, id);
    }
    private ItemStack spawnEgg(Entity entity) {
        return new ItemStack(Items.spawn_egg, 1, EntityList.getEntityID(entity));
    }

    public boolean isIngredient(ItemStack item, int ingredientNumber) {
        if (ingredientNumber == 1 || ingredientNumber == 2) {
            for (Map<ItemStack, ItemStack> ingredientPair : combiningRecipes.keySet()) {
                if (ingredientNumber == 1)
                    for (ItemStack stack : ingredientPair.keySet())
                        if (areStacksEqual(item, stack)) return true;
                if (ingredientNumber == 2)
                    for (ItemStack stack : ingredientPair.values())
                        if (areStacksEqual(item, stack)) return true;
            }
        }
        return false;
    }

    public ItemStack getCombiningResult(ItemStack firstIngredient, ItemStack secondIngredient) {
        for (Map.Entry<Map<ItemStack, ItemStack>, ItemStack> entry : combiningRecipes.entrySet()) {
            Map<ItemStack, ItemStack> ingredients = entry.getKey();
            ItemStack output = entry.getValue();

            ItemStack first = ingredients.keySet().iterator().next();
            ItemStack second = ingredients.get(first);

            if (areStacksEqual(firstIngredient.copy(), first) && areStacksEqual(secondIngredient.copy(), second)) return output;
        }
        return null;
    }
    public float getCombiningExperience(ItemStack result) {
        for (Map.Entry<ItemStack, Float> entry : experienceList.entrySet()) {
            if (areStacksEqual(entry.getKey(), result)) return entry.getValue();
        }
        return 0f;
    }
}