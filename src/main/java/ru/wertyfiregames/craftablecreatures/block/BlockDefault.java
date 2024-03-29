package ru.wertyfiregames.craftablecreatures.block;

import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDefault extends Block
{
    public BlockDefault(Material material, String name, String textureName, CreativeTabs creativeTab,
                        String toolType, int level, float hardness, float resistance)
    {
        super(material);
        this.setBlockName(name);
        if (!textureName.equals("custom")) this.setBlockTextureName(CraftableCreatures.getModId() + ":" + textureName);
        if (textureName.equals("") || textureName == null) this.setBlockTextureName(CraftableCreatures.getModId() + ":" + textureName);
        this.setCreativeTab(creativeTab);
        this.setHarvestLevel(toolType, level);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }
    public BlockDefault(Material material, String nameAll, CreativeTabs creativeTab,
                        String toolType, int level, float hardness, float resistance)
    {
        super(material);
        this.setBlockName(nameAll);
        this.setBlockTextureName(CraftableCreatures.getModId() + ":" + nameAll);
        this.setCreativeTab(creativeTab);
        this.setHarvestLevel(toolType, level);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }
}