/**
 * File created on 17:20 20.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.recipe;

import net.minecraftforge.oredict.OreDictionary;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

public class CCOreDictionary
{
    public static void register()
    {
//        Ores
        OreDictionary.registerOre("oreBluestone", CCBlocks.bluestone_ore);

//        Blocks
        OreDictionary.registerOre("blockBluestone", CCBlocks.bluestone_block);

//        Dusts
        OreDictionary.registerOre("dustBluestone", CCItems.bluestone);

    }
}