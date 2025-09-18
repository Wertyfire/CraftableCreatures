package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CCRecipes {
    public static void register() {
        //Shapeless recipes
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.spawn_egg_blueprint),
                new ItemStack(Items.egg), new ItemStack(CCItems.blueprint));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 9),
                new ItemStack(CCBlocks.bluestone_block));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 4),
                new ItemStack(CCBlocks.powered_bluestone_block));

        //Shaped recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.blueprint), true,
                "SR", "RS", 'R', "dustBluestone", 'S', Items.paper));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCBlocks.powered_bluestone_block),
                "SUS", "UUU", "SUS", 'S', "dustBluestone", 'U', "dustRedstone"));
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.bluestone_block),
                "SSS", "SSS", "SSS", 'S', CCItems.bluestone);
        GameRegistry.addShapedRecipe(new ItemStack(CCItems.guide_book),
                "SSS", "SUS", "SSS", 'S', CCItems.soul, 'U', Items.book);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.soul_extractor),
                "SSS", "SUS", "SAS", 'S', Blocks.cobblestone, 'U', CCBlocks.powered_bluestone_block, 'A', Blocks.furnace);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.combiner),
                "SSS", "SUS", "SAS", 'S', Blocks.cobblestone, 'U', CCItems.blueprint, 'A', Blocks.redstone_block);
        GameRegistry.addShapedRecipe(new ItemStack(CCItems.transmutator),
                "R  ", "BII", "III", 'R', Items.redstone, 'B', CCItems.bluestone, 'I', Items.iron_ingot);

        //Smelting recipes
        GameRegistry.addSmelting(CCBlocks.bluestone_ore, new ItemStack(CCItems.bluestone), 0.7f);
    }
}