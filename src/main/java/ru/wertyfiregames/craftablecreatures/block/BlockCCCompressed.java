/**
 * File created on 15:58 20.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCCCompressed extends BlockDefault {
    private final MapColor mapColor;

    public BlockCCCompressed(MapColor mapColor, String name, String textureName, CreativeTabs creativeTabs,
                             int level, float hardness, float resistance) {
        super(Material.iron, name, textureName, creativeTabs, "pickaxe", level, hardness, resistance);
        this.mapColor = mapColor;
    }

    public BlockCCCompressed(MapColor mapColor, String nameAll, CreativeTabs creativeTabs, int level,
                             float hardness, float resistance) {
        super(Material.iron, nameAll, nameAll, creativeTabs, "pickaxe", level, hardness, resistance);
        this.mapColor = mapColor;
    }

    @Override
    public MapColor getMapColor(int metadata) {
        return mapColor;
    }
}