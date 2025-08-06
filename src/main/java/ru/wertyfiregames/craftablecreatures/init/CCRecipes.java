package ru.wertyfiregames.craftablecreatures.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;

public class CCRecipes {
    public static void register() {
        //Shapeless recipes
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.spawn_egg_template),
                new ItemStack(Items.egg), new ItemStack(CCItems.template));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 9),
                new ItemStack(CCBlocks.bluestone_block));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 4),
                new ItemStack(CCBlocks.powered_bluestone_block));

        //Shaped recipes
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.template), true,
                "SR", "RS", 'R', "dustBluestone", 'S', Items.paper));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCBlocks.powered_bluestone_block),
                "SUS", "UUU", "SUS", 'S', "dustBluestone", 'U', "dustRedstone"));
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.bluestone_block),
                "SSS", "SSS", "SSS", 'S', CCItems.bluestone);
        GameRegistry.addShapedRecipe(new ItemStack(CCItems.guide_book),
                "SSS", "SUS", "SSS", 'S', CCItems.soul_element, 'U', Items.book);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.soul_extractor),
                "SSS", "SUS", "SAS", 'S', Blocks.cobblestone, 'U', CCBlocks.powered_bluestone_block, 'A', Blocks.furnace);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.combiner),
                "SSS", "SUS", "SAS", 'S', Blocks.cobblestone, 'U', CCItems.template, 'A', Blocks.redstone_block);

        //Smelting recipes
        GameRegistry.addSmelting(CCBlocks.bluestone_ore, new ItemStack(CCItems.bluestone), 0.7f);
    }
}