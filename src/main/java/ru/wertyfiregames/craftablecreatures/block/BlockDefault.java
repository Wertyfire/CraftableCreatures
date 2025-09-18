package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDefault extends Block {
    public BlockDefault(Material material, String name, CreativeTabs creativeTab,
                        String toolType, int level, float hardness, float resistance) {
        super(material);
        setUnlocalizedName(name);
        setCreativeTab(creativeTab);
        setHarvestLevel(toolType, level);
        setHardness(hardness);
        setResistance(resistance);
    }
}