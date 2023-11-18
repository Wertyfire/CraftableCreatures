package ru.wertyfiregames.craftablecreatures.block;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class CCBlocks
{
    public static final Block powered_bluestone_block = new BlockCCCompressedPowered(MapColor.blueColor, Material.iron,
            "poweredBluestoneBlock", "bluestone_block", CCCreativeTabs.tabCraftableCreatures,
            "pickaxe", 2, 5f, 10f);
    public static final Block bluestone_block = new BlockDefault(Material.iron, "bluestoneBlock", "bluestone_block",
            CCCreativeTabs.tabCraftableCreatures, "pickaxe", 2, 5f, 10f);
    public static final Block bluestone_ore = new BlockCCCompressed(MapColor.stoneColor, Material.rock, "bluestoneOre", "bluestone_ore",
            CCCreativeTabs.tabCraftableCreatures, "pickaxe", 2, 3f, 5f);

    public static void register()
    {
        GameRegistry.registerBlock(powered_bluestone_block, "powered_bluestone_block");
        GameRegistry.registerBlock(bluestone_block, "bluestone_block");
        GameRegistry.registerBlock(bluestone_ore, "bluestone_ore");
        if (CCConfigHandler.enableExperimentalContent)
        {
        }
    }
}