/**
 * File created on 17:21 12.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CCBlocks {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block POWERED_BLUESTONE_BLOCK = new BlockCCCompressedPowered(Material.IRON, MapColor.BLUE,
            "poweredBluestoneBlock", "powered_bluestone_block", CCCreativeTabs.tabCraftableCreatures,
            "pickaxe", 2, 5f, 10f);
    public static final Block BLUESTONE_BLOCK = new BlockDefault(Material.IRON, "bluestoneBlock", "bluestone_block",
            CCCreativeTabs.tabCraftableCreatures, "pickaxe", 2, 5f, 10f);
    public static final Block BLUESTONE_ORE = new BlockDefault(Material.ROCK, "bluestoneOre",
            "bluestone_ore", CCCreativeTabs.tabCraftableCreatures, "pickaxe", 2,
            5f, 10f);
    public static final Block SOUL_EXTRACTOR = new BlockSoulExtractor();

    public static void register() {
        setRegister(POWERED_BLUESTONE_BLOCK);
        setRegister(BLUESTONE_BLOCK);
        setRegister(BLUESTONE_ORE);
        setRegister(SOUL_EXTRACTOR);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender() {
        setRender(POWERED_BLUESTONE_BLOCK);
        setRender(BLUESTONE_BLOCK);
        setRender(BLUESTONE_ORE);
        setRender(SOUL_EXTRACTOR);
    }

    private static void setRegister(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(Objects.requireNonNull(block.getRegistryName())));
    }

    private static void setRender(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block),
                0, new ModelResourceLocation(Objects.requireNonNull(block.getRegistryName()), "inventory"));
    }
}