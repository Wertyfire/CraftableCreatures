package ru.wertyfiregames.craftablecreatures.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CCRecipes {
    public static void register() {
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.spawn_egg_template),
                new ItemStack(Items.egg), new ItemStack(CCItems.template));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 9),
                new ItemStack(CCBlocks.bluestone_block));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 8),
                new ItemStack(CCBlocks.powered_bluestone_block));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.template),
                "SR", "RS", 'R', "dustBluestone", 'S', Items.paper));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCItems.template),
                "RS", "SR", 'R', "dustBluestone", 'S', Items.paper));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CCBlocks.powered_bluestone_block),
                "SSS", "SUS", "SSS", 'S', "dustBluestone", 'U', "dustRedstone"));
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.bluestone_block),
                "SSS", "SSS", "SSS", 'S', CCItems.bluestone);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.soul_extractor),
                "SSS", "SUS", "SSS", 'S', Blocks.cobblestone, 'U', CCItems.bluestone);

        //Spawn eggs
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityCreeper(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 1));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntitySkeleton(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 2));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntitySpider(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 3));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityZombie(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 4));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntitySlime(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 5));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityGhast(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 6));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityPigZombie(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 7));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityEnderman(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 8));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityCaveSpider(null))), new ItemStack(CCItems.spawn_egg_template, 1, 4), new ItemStack(CCItems.soul_element, 1, 9));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntitySilverfish(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 10));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityBlaze(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 11));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityMagmaCube(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 12));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityBat(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 13));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityWitch(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 14));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityPig(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 15));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntitySheep(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 16));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityCow(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 17));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityChicken(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 18));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntitySquid(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 19));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityWolf(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 20));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityMooshroom(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 21));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityOcelot(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 22));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1, 100), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 23));
        GameRegistry.addShapelessRecipe(new ItemStack(Items.spawn_egg, 1,
                EntityList.getEntityID(new EntityVillager(null))), new ItemStack(CCItems.spawn_egg_template), new ItemStack(CCItems.soul_element, 1, 24));
    }
}