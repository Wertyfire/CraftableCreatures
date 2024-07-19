package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import java.util.Random;

public class BlockCCOre extends BlockDefault {
    private final MapColor mapColor;

    public BlockCCOre(MapColor mapColor, Material material, String name, String textureName, CreativeTabs creativeTab,
                      String toolType, int level, float hardness, float resistance) {
        super(material, name, textureName, creativeTab, toolType, level, hardness, resistance);
        this.mapColor = mapColor;
    }

    public BlockCCOre(MapColor mapColor, Material material, String nameAll, CreativeTabs creativeTab,
                      String toolType, int level, float hardness, float resistance) {
        super(material, nameAll, creativeTab, toolType, level, hardness, resistance);
        this.mapColor = mapColor;
    }

    @Override
    public MapColor getMapColor(int p_149728_1_) {
        return this.mapColor;
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return this == CCBlocks.bluestone_ore ? CCItems.bluestone : super.getItemDropped(metadata, random, fortune);
    }

    @Override
    public int quantityDropped(Random random) {
        return this == CCBlocks.bluestone_ore ? random.nextInt(2) + 1 : super.quantityDropped(random);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        int quantity = this.quantityDropped(random);
        return quantity + random.nextInt(fortune + 1);
    }
}