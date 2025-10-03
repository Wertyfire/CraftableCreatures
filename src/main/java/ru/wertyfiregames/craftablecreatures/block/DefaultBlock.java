/**
 * File created on 16:19 22.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class DefaultBlock extends Block {
    public DefaultBlock(Material material, String name, String textureName, CreativeTabs creativeTab, String tooltype, int level, float hardness, float resistance) {
        super(material);

        setBlockName(name);
        setBlockTextureName(CraftableCreatures.getModId() + ":" + textureName);
        setCreativeTab(creativeTab);
        setHarvestLevel(tooltype, level);
        setHardness(hardness);
        setResistance(resistance);
    }
    public DefaultBlock(Material material, String nameAll, CreativeTabs creativeTab, String tooltype, int level, float hardness, float resistance) {
        this(material, nameAll, nameAll, creativeTab, tooltype, level, hardness, resistance);
    }
}