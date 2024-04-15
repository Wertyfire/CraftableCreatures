/**
 * File created on 17:41 13.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCCCompressedPowered extends Block {
    public BlockCCCompressedPowered(Material material, MapColor color, String name, String textureName,
                                    CreativeTabs creativeTabs, String toolType, int level, float hardness, float resistance) {
        super(material, color);
        this.setUnlocalizedName(name);
        this.setRegistryName(textureName);
        this.setCreativeTab(creativeTabs);
        this.setHarvestLevel(toolType, level);
        this.setHardness(hardness);
        this.setResistance(resistance);

        CCBlocks.BLOCKS.add(this);
    }

    @Override
    @SuppressWarnings("all")
    public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("all")
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return 15;
    }
}