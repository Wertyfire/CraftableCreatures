package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.block.*;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class CCBlocks {
    public static final Block POWERED_BLUESTONE_BLOCK = new CompressedPoweredBlock(Material.IRON,
            MapColor.BLUE, "poweredBluestoneBlock", CCCreativeTabs.TAB_CRAFTABLE_CREATURES,
            2, 5f, 10f);
    public static final Block BLUESTONE_BLOCK = new BlockDefault(Material.IRON, "bluestoneBlock",
            CCCreativeTabs.TAB_CRAFTABLE_CREATURES, "pickaxe", 2, 5f, 10f);
    public static final Block BLUESTONE_ORE = new BlockCCOre("bluestoneOre",
            CCCreativeTabs.TAB_CRAFTABLE_CREATURES, "pickaxe", 2, 3f, 5f);
    public static final Block SOUL_EXTRACTOR = new BlockSoulExtractor(false);
    public static final Block LIT_SOUL_EXTRACTOR = new BlockSoulExtractor(true);
    public static final Block COMBINER = new BlockCombiner(false);
    public static final Block LIT_COMBINER = new BlockCombiner(true);

    public static void register() {
        register(POWERED_BLUESTONE_BLOCK, "powered_bluestone_block");
        register(BLUESTONE_BLOCK, "bluestone_block");
        register(BLUESTONE_ORE, "bluestone_ore");
        register(SOUL_EXTRACTOR, BlockSoulExtractor.SoulExtractorItemBlock.class, "soul_extractor");
        register(LIT_SOUL_EXTRACTOR, "lit_soul_extractor");
        register(COMBINER, BlockCombiner.CombinerItemBlock.class, "combiner");
        register(LIT_COMBINER, "lit_combiner");
        registerExperimental();
    }

    public static void registerRenders() {
        registerRender(POWERED_BLUESTONE_BLOCK);
        registerRender(BLUESTONE_BLOCK);
        registerRender(BLUESTONE_ORE);
        registerRender(SOUL_EXTRACTOR);
        registerRender(LIT_SOUL_EXTRACTOR);
        registerRender(COMBINER);
        registerRender(LIT_COMBINER);
    }


    private static void register(Block block, String id) {
        GameRegistry.register(block.setRegistryName(CraftableCreatures.getModId(), id));
        GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
    private static void register(Block block, Class<? extends ItemBlock> itemBlock, String id) {
        GameRegistry.register(block.setRegistryName(CraftableCreatures.getModId(), id));
        try {
            ItemBlock ib = itemBlock.getConstructor(Block.class).newInstance(block);
            GameRegistry.register(ib.setRegistryName(block.getRegistryName()));
        } catch (Exception ignored) {}
    }

    public static void registerRender(Block block) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
    }

    private static void registerExperimental() {
        if (!CCConfig.enableExperimentalContent) return;
    }
}