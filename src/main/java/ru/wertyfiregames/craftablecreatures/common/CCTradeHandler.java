/**
 * File created on 15:01 25.02.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.common;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.item.EnumSoulElement;

import java.util.Arrays;
import java.util.Random;

public class CCTradeHandler {
    public static void registerTrades() {
        EntityVillager.ITradeList[][][][] allTrades = ReflectionHelper.getPrivateValue(
                EntityVillager.class, null, "DEFAULT_TRADE_LIST_MAP", "field_175561_bA");

        EntityVillager.ITradeList[][][] priestCareers = allTrades[2];
        EntityVillager.ITradeList[][] clericLevels = priestCareers[0];
        EntityVillager.ITradeList[] level2 = clericLevels[1];

        EntityVillager.ITradeList[] newLevel2 = Arrays.copyOf(level2, level2.length + 1);

        newLevel2[level2.length] = new SoulTrade();

        clericLevels[1] = newLevel2;
    }

    public static class SoulTrade implements EntityVillager.ITradeList {
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random)
        {
            int meta = random.nextInt(EnumSoulElement.values().length);
            if (meta == 0) meta += 1 + random.nextInt(3);

            recipeList.add(new MerchantRecipe(new ItemStack(CCItems.soul), new ItemStack(Items.emerald, 15), new ItemStack(CCItems.soul, 1, meta)));
        }
    }
}