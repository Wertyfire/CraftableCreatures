/**
 * File created on 13:23 23.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class CompressedPoweredBlock extends BlockCompressedPowered {
    public CompressedPoweredBlock(MapColor mapColor, String name, String textureName, CreativeTabs creativeTab, int level, float hardness, float resistance) {
        super(mapColor);

        setBlockName(name);
        setBlockTextureName(CraftableCreatures.getModId() + ":" + textureName);
        setCreativeTab(creativeTab);
        setHarvestLevel("pickaxe", level);
        setHardness(hardness);
        setResistance(resistance);
    }
    public CompressedPoweredBlock(MapColor mapColor, String nameAll, CreativeTabs creativeTab, int level, float hardness, float resistance) {
        this(mapColor, nameAll, nameAll, creativeTab, level, hardness, resistance);
    }
}