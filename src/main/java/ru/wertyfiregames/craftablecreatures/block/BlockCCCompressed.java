package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCCCompressed extends BlockDefault
{
    private final MapColor mapColor;

    public BlockCCCompressed(MapColor mapColor, Material material, String name, String textureName, CreativeTabs creativeTab,
                             String toolType, int level, float hardness, float resistance)
    {
        super(material, name, textureName, creativeTab, toolType, level, hardness, resistance);
        this.mapColor = mapColor;
    }
    public BlockCCCompressed(MapColor mapColor, Material material, String nameAll, CreativeTabs creativeTab,
                             String toolType, int level, float hardness, float resistance)
    {
        super(material, nameAll, creativeTab, toolType, level, hardness, resistance);
        this.mapColor = mapColor;
    }

    @Override
    public MapColor getMapColor(int p_149728_1_)
    {
        return this.mapColor;
    }
}