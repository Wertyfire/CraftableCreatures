/**
 * File created on 15:16 14.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

public class CCRecipes {
    public static void register() {
        GameRegistry.addSmelting(new ItemStack(CCBlocks.BLUESTONE_ORE), new ItemStack(CCItems.BLUESTONE), 10f);
    }
}