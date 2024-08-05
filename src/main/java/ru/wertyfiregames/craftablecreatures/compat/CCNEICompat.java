/**
 * File created on 18:27 05.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat;

import codechicken.nei.api.API;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;

public class CCNEICompat {
    public static void initNEICompatibility() {
        if (Loader.isModLoaded("NotEnoughItems")) {
            CraftableCreatures.getModLogger().info("Found NEI. Injecting recipes data and other stuff");
            hideItems();
            CraftableCreatures.getModLogger().info(("CC NEI compatibility loaded"));
        }
    }

    private static void hideItems() {
        API.hideItem(new ItemStack(CCBlocks.lit_soul_extractor));
    }
}