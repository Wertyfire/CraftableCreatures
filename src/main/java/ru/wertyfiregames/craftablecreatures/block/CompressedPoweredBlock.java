/**
    File created on 20:03 14.09.2025 by Wertyfire
*/

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class CompressedPoweredBlock extends BlockCompressedPowered {
    public CompressedPoweredBlock(Material material, MapColor mapColor, String unlocalizedName,
                                  CreativeTabs creativeTab, int toolLevel, float hardness, float resistance) {
        super(material, mapColor);
        setUnlocalizedName(unlocalizedName);
        setCreativeTab(creativeTab);
        setHarvestLevel("pickaxe", toolLevel);
        setHardness(hardness);
        setResistance(resistance);
    }
}