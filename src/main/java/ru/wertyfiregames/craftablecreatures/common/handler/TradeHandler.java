/**
 * File created on 18:33 15.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.common.handler;

import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

import java.util.Random;

public class TradeHandler implements IVillageTradeHandler
{
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        switch (villager.getProfession())
        {
            case 0:
                break;
            case 1:
                break;
            case 2:
                recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 15),
                        new ItemStack(CCItems.soul_element, 1, 0),
                        new ItemStack(CCItems.soul_element, 1, random.nextInt(25))));
                recipeList.add(new MerchantRecipe(new ItemStack(Items.emerald, 8),
                        new ItemStack(Items.redstone, 4),
                        new ItemStack(CCItems.portative_body_changer, 1)));
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }
}