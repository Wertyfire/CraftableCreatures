package ru.wertyfiregames.craftablecreatures.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.item.CCItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CCRecipes
{
    public static void register()
    {
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.spawn_egg_template),
                new ItemStack(Items.egg), new ItemStack(CCItems.template));
        GameRegistry.addShapedRecipe(new ItemStack(CCItems.template),
                "SR", "RS", 'R', CCItems.bluestone, 'S', Items.paper);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.powered_bluestone_block),
                "SSS", "SUS", "SSS", 'S', CCItems.bluestone, 'U', Items.redstone);
        GameRegistry.addShapedRecipe(new ItemStack(CCBlocks.bluestone_block),
                "SSS", "SSS", "SSS", 'S', CCItems.bluestone);
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 9),
                new ItemStack(CCBlocks.bluestone_block));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.bluestone, 8),
                new ItemStack(CCBlocks.powered_bluestone_block));
        GameRegistry.addSmelting(new ItemStack(CCBlocks.bluestone_ore), new ItemStack(CCItems.bluestone), 10f);

        //Souls
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                1), new ItemStack(Items.gunpowder), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                2), new ItemStack(Items.bone), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                3), new ItemStack(Items.spider_eye), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                4), new ItemStack(Items.rotten_flesh), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                5), new ItemStack(Items.slime_ball), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                6), new ItemStack(Items.ghast_tear), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                7), new ItemStack(Items.golden_sword), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                8), new ItemStack(Items.ender_eye), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                9), new ItemStack(CCItems.soul_element, 1, 4), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                10), new ItemStack(Blocks.monster_egg), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                11), new ItemStack(Items.blaze_rod), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                12), new ItemStack(Items.magma_cream), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                13), new ItemStack(CCItems.bat_wing), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                14), new ItemStack(Items.sugar), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                14), new ItemStack(Items.stick), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                14), new ItemStack(Items.potionitem), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                15), new ItemStack(Items.porkchop), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                16), new ItemStack(Blocks.wool), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                17), new ItemStack(Items.beef), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                17), new ItemStack(Items.milk_bucket), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                18), new ItemStack(Items.chicken), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                18), new ItemStack(Items.egg), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                18), new ItemStack(Items.feather), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                19), new ItemStack(Items.dye, 1, 0), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                20), new ItemStack(CCItems.soul_element, 1, 2), new ItemStack(CCItems.soul_element, 1, 0));
        GameRegistry.addShapelessRecipe(new ItemStack(CCItems.soul_element, 1,
                21), new ItemStack(Blocks.red_mushroom), new ItemStack(CCItems.soul_element, 1, 0));

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