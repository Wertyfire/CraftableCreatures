package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.block.*;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class CCBlocks {
    public static final Block powered_bluestone_block = new CompressedPoweredBlock(Material.iron,
            MapColor.blueColor, "poweredBluestoneBlock", CCCreativeTabs.tabCraftableCreatures,
            2, 5f, 10f);
    public static final Block bluestone_block = new BlockDefault(Material.iron, "bluestoneBlock",
            CCCreativeTabs.tabCraftableCreatures, "pickaxe", 2, 5f, 10f);
    public static final Block bluestone_ore = new BlockCCOre("bluestoneOre",
            CCCreativeTabs.tabCraftableCreatures, "pickaxe", 2, 3f, 5f);
    public static final Block soul_extractor = new BlockSoulExtractor(false);
    public static final Block lit_soul_extractor = new BlockSoulExtractor(true);
    public static final Block combiner = new BlockCombiner(false);
    public static final Block lit_combiner = new BlockCombiner(true);

    public static void register() {
        GameRegistry.registerBlock(powered_bluestone_block, "powered_bluestone_block");
        GameRegistry.registerBlock(bluestone_block, "bluestone_block");
        GameRegistry.registerBlock(bluestone_ore, "bluestone_ore");
        GameRegistry.registerBlock(soul_extractor, BlockSoulExtractor.SoulExtractorItemBlock.class, "soul_extractor");
        GameRegistry.registerBlock(lit_soul_extractor, "lit_soul_extractor");
        GameRegistry.registerBlock(combiner, BlockCombiner.CombinerItemBlock.class, "combiner");
        GameRegistry.registerBlock(lit_combiner, "lit_combiner");
        registerExperimental();
    }

    public static void registerRenders() {
        registerRender(powered_bluestone_block);
        registerRender(bluestone_block);
        registerRender(bluestone_ore);
        registerRender(soul_extractor);
        registerRender(lit_soul_extractor);
        registerRender(combiner);
        registerRender(lit_combiner);
    }

    public static void registerRender(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    private static void registerExperimental() {
        if (!CCConfig.enableExperimentalContent) return;
    }
}