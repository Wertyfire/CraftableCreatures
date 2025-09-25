/**
 * File created on 17:20 20.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.compat;

import net.minecraftforge.oredict.OreDictionary;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

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