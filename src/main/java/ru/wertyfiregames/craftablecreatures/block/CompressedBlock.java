/**
 * File created on 13:15 23.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class CompressedBlock extends BlockCompressed {
    public CompressedBlock(MapColor mapColor, String name, String textureName, CreativeTabs creativeTab, int level, float hardness, float resistance) {
        super(mapColor);

        setBlockName(name);
        setBlockTextureName(CraftableCreatures.getModId() + ":" + textureName);
        setCreativeTab(creativeTab);
        setHarvestLevel("pickaxe", level);
        setHardness(hardness);
        setResistance(resistance);
    }
    public CompressedBlock(MapColor mapColor, String nameAll, CreativeTabs creativeTab, int level, float hardness, float resistance) {
        this(mapColor, nameAll, nameAll, creativeTab, level, hardness, resistance);
    }
}