/**
 * File created on 15:01 25.02.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.common;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.item.EnumSoulElement;

import java.util.Random;

public class CCTradeHandler {
    public static void registerTrades() {
        VillagerRegistry.VillagerProfession priest =
                VillagerRegistry.instance().getRegistry().getValue(new ResourceLocation("priest"));

        if (priest == null) return;

        VillagerRegistry.VillagerCareer cleric = priest.getCareer(0);
        cleric.addTrade(2, new SoulTrade());
    }

    public static class SoulTrade implements EntityVillager.ITradeList {
        public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random)
        {
            int meta = random.nextInt(EnumSoulElement.values().length);
            if (meta == 0) meta += 1 + random.nextInt(3);

            recipeList.add(new MerchantRecipe(new ItemStack(CCItems.SOUL),
                    new ItemStack(Items.EMERALD, 15),
                    new ItemStack(CCItems.SOUL, 1, meta)));
        }
    }
}