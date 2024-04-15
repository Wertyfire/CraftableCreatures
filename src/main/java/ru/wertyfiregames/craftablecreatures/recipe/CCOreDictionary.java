/**
 * File created on 14:50 14.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.recipe;

import net.minecraftforge.oredict.OreDictionary;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

public class CCOreDictionary {
    public static void register() {
//        Ores
        OreDictionary.registerOre("oreBluestone", CCBlocks.BLUESTONE_ORE);

//        Blocks
        OreDictionary.registerOre("blockBluestone", CCBlocks.BLUESTONE_BLOCK);

//        Dusts
        OreDictionary.registerOre("dustBluestone", CCItems.BLUESTONE);
    }
}