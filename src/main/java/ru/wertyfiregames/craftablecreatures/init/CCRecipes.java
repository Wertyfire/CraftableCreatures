package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CCRecipes {
    public static void register() {
        //Shapeless recipes
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.SPAWN_EGG_BLUEPRINT),
                new ItemStack(Items.EGG), new ItemStack(CCItems.BLUEPRINT));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.BLUESTONE, 9),
                new ItemStack(CCBlocks.BLUESTONE_BLOCK));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.BLUESTONE, 4),
                new ItemStack(CCBlocks.POWERED_BLUESTONE_BLOCK));

        //Shaped recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.BLUEPRINT), true,
                "SR", "RS", 'R', "dustBluestone", 'S', Items.PAPER));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCBlocks.POWERED_BLUESTONE_BLOCK),
                "SUS", "UUU", "SUS", 'S', "dustBluestone", 'U', "dustRedstone"));
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.BLUESTONE_BLOCK),
                "SSS", "SSS", "SSS", 'S', CCItems.BLUESTONE);
        GameRegistry.addShapedRecipe(new ItemStack(CCItems.GUIDE_BOOK),
                " S ", "SUS", " S ", 'S', CCItems.SOUL, 'U', Items.BOOK);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.SOUL_EXTRACTOR),
                "SSS", "SUS", "SAS", 'S', Blocks.COBBLESTONE, 'U', CCBlocks.POWERED_BLUESTONE_BLOCK, 'A', Blocks.FURNACE);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.COMBINER),
                "SSS", "SUS", "SAS", 'S', Blocks.COBBLESTONE, 'U', CCItems.BLUEPRINT, 'A', Blocks.REDSTONE_BLOCK);
        GameRegistry.addShapedRecipe(new ItemStack(CCItems.TRANSMUTATOR),
                "R  ", "BII", "III", 'R', Items.REDSTONE, 'B', CCItems.BLUESTONE, 'I', Items.IRON_INGOT);

        //Smelting recipes
        GameRegistry.addSmelting(CCBlocks.BLUESTONE_ORE, new ItemStack(CCItems.BLUESTONE), 0.7f);
    }
}