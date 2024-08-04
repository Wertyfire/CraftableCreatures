package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class BlockCCCompressedPowered extends BlockCCCompressed {
    public BlockCCCompressedPowered(MapColor mapColor, String name, String textureName,
                                    CreativeTabs creativeTab, int level, float hardness, float resistance) {
        super(mapColor, name, textureName, creativeTab, level, hardness, resistance);
    }

    public BlockCCCompressedPowered(MapColor mapColor, String nameAll,
                                    CreativeTabs creativeTab, int level, float hardness, float resistance) {
        super(mapColor, nameAll, creativeTab, level, hardness, resistance);
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return 15;
    }
}