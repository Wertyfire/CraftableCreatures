package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockCCCompressedPowered extends BlockCCCompressed
{
    public BlockCCCompressedPowered(MapColor mapColor, Material material, String name, String textureName,
                                    CreativeTabs creativeTab, String toolType, int level, float hardness, float resistance)
    {
        super(mapColor, material, name, textureName, creativeTab, toolType, level, hardness, resistance);
    }

    public BlockCCCompressedPowered(MapColor mapColor, Material material, String nameAll,
                                    CreativeTabs creativeTab, String toolType, int level, float hardness, float resistance)
    {
        super(mapColor, material, nameAll, creativeTab, toolType, level, hardness, resistance);
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess blockAccess, int int1, int int2, int int3, int int4)
    {
        return 15;
    }
}