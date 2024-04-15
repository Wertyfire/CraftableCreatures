/**
 * File created on 17:11 12.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDefault extends Block {
    public BlockDefault(Material material, String name, String textureName, CreativeTabs creativeTabs, String toolType,
                        int level, float hardness, float resistance) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(textureName);
        setCreativeTab(creativeTabs);
        setHarvestLevel(toolType, level);
        setHardness(hardness);
        setResistance(resistance);

        CCBlocks.BLOCKS.add(this);
    }

    public BlockDefault(Material material, String nameAll, CreativeTabs creativeTabs, String toolType, int level,
                        float hardness, float resistance) {
        super(material);
        setUnlocalizedName(nameAll);
        setRegistryName(nameAll);
        setCreativeTab(creativeTabs);
        setHarvestLevel(toolType, level);
        setHardness(hardness);
        setResistance(resistance);
    }
}