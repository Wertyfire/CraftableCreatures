/**
 * File created on 18:33 15.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.common;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import java.util.Random;

public class CCTradeHandler implements IVillageTradeHandler {
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
        if (villager.getProfession() == 2)
            recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 15),
                    new ItemStack(CCItems.soul_element, 1, 0),
                    new ItemStack(CCItems.soul_element, 1, random.nextInt(25))));
    }
}