/**
 * File created on 22:15 28.04.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.*;

import java.util.HashMap;
import java.util.Map;

public class CombinerRecipes {
    private static final CombinerRecipes combiningBase = new CombinerRecipes();
    private final Map<Pair<ItemStack, ItemStack>, ItemStack> combiningRecipes = new HashMap<>();

    public static CombinerRecipes get() {
        return combiningBase;
    }

    public CombinerRecipes() {
        addRecipe(soul(1), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityMooshroom(null)));
        addRecipe(soul(2), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityCreeper(null)));
        addRecipe(soul(3), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntitySkeleton(null)));
        addRecipe(soul(4), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntitySpider(null)));
        addRecipe(soul(5), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityZombie(null)));
        addRecipe(soul(6), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntitySlime(null)));
        addRecipe(soul(7), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityGhast(null)));
        addRecipe(soul(8), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityVillager(null)));
        addRecipe(soul(9), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityPigZombie(null)));
        addRecipe(soul(10), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityEnderman(null)));
        addRecipe(soul(11), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityPig(null)));
        addRecipe(soul(12), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityCaveSpider(null)));
        addRecipe(soul(13), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntitySheep(null)));
        addRecipe(soul(14), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntitySilverfish(null)));
        addRecipe(soul(15), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityCow(null)));
        addRecipe(soul(16), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityBlaze(null)));
        addRecipe(soul(17), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityChicken(null)));
        addRecipe(soul(18), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityMagmaCube(null)));
        addRecipe(soul(19), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntitySquid(null)));
        addRecipe(soul(20), mod_CraftableCreatures.spawnEggTemplate, spawnEgg(new EntityWolf(null)));
    }

    public void addRecipe(Item firstInput, Item secondInput, ItemStack output) {
        addRecipe(new ItemStack(firstInput), new ItemStack(secondInput), output);
    }
    public void addRecipe(ItemStack firstInput, Item secondInput, ItemStack output) {
        addRecipe(firstInput, new ItemStack(secondInput), output);
    }
    public void addRecipe(Item firstInput, ItemStack secondInput, ItemStack output) {
        addRecipe(new ItemStack(firstInput), secondInput, output);
    }
    public void addRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack output) {
        Pair<ItemStack, ItemStack> ingredients = new Pair<>(firstInput.copy(), secondInput.copy());
        combiningRecipes.put(ingredients, output);
    }

    private boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && (stack1.getItemDamage() == -1
                || stack2.getItemDamage() == stack1.getItemDamage());
    }

    private ItemStack soul(int id) {
        return new ItemStack(mod_CraftableCreatures.soulElement, 1, id);
    }
    private ItemStack spawnEgg(Entity entity) {
        return new ItemStack(Item.field_44008_bB, 1, EntityList.getEntityID(entity)); //field_44019_bC - spawnEgg
    }

    public boolean isIngredient(ItemStack item, int ingredientNumber) {
        if (ingredientNumber == 1 || ingredientNumber == 2) {
            for (Pair<ItemStack, ItemStack> ingredientPair : combiningRecipes.keySet()) {
                if (ingredientNumber == 1)
                    if (areStacksEqual(item, ingredientPair.getKey())) return true;
                if (ingredientNumber == 2)
                    if (areStacksEqual(item, ingredientPair.getValue())) return true;
            }
        }
        return false;
    }

    public ItemStack getCombiningResult(ItemStack firstIngredient, ItemStack secondIngredient) {
        for (Map.Entry<Pair<ItemStack, ItemStack>, ItemStack> entry : combiningRecipes.entrySet()) {
            ItemStack output = entry.getValue();

            ItemStack first = entry.getKey().getKey();
            ItemStack second = entry.getKey().getValue();

            if (areStacksEqual(firstIngredient.copy(), first) && areStacksEqual(secondIngredient.copy(), second)) return output;
        }
        return null;
    }

    private static final class Pair<L, R> {
        private final L key;
        private final R value;

        public Pair(L key, R value) {
            this.key = key;
            this.value = value;
        }

        public L getKey() {
            return key;
        }

        public R getValue() {
            return value;
        }
    }
}