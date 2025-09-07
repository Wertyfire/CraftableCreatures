/**
 * File created on 13:01 09.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;

public class CCAPIInit {
    public static void register() {
        CraftableCreaturesRegistry.registerItemAsSoul(CCItems.soul_element);

        link(1, new EntityCreeper(Minecraft.getMinecraft().theWorld));
        link(2, new EntitySkeleton(Minecraft.getMinecraft().theWorld));
        link(3, new EntitySpider(Minecraft.getMinecraft().theWorld));
        link(4, new EntityZombie(Minecraft.getMinecraft().theWorld));
        link(5, new EntitySlime(Minecraft.getMinecraft().theWorld));
        link(6, new EntityGhast(Minecraft.getMinecraft().theWorld));
        link(7, new EntityPigZombie(Minecraft.getMinecraft().theWorld));
        link(8, new EntityEnderman(Minecraft.getMinecraft().theWorld));
        link(9, new EntityCaveSpider(Minecraft.getMinecraft().theWorld));
        link(10, new EntitySilverfish(Minecraft.getMinecraft().theWorld));
        link(11, new EntityBlaze(Minecraft.getMinecraft().theWorld));
        link(12, new EntityMagmaCube(Minecraft.getMinecraft().theWorld));
        link(13, new EntityBat(Minecraft.getMinecraft().theWorld));
        link(14, new EntityWitch(Minecraft.getMinecraft().theWorld));
        link(15, new EntityPig(Minecraft.getMinecraft().theWorld));
        link(16, new EntitySheep(Minecraft.getMinecraft().theWorld));
        link(17, new EntityCow(Minecraft.getMinecraft().theWorld));
        link(18, new EntityChicken(Minecraft.getMinecraft().theWorld));
        link(19, new EntitySquid(Minecraft.getMinecraft().theWorld));
        link(20, new EntityWolf(Minecraft.getMinecraft().theWorld));
        link(21, new EntityMooshroom(Minecraft.getMinecraft().theWorld));
        link(22, new EntityOcelot(Minecraft.getMinecraft().theWorld));
//        link(23, new EntityHorse(Minecraft.getMinecraft().theWorld)); //This shit throws null pointer exception
        link(24, new EntityVillager(Minecraft.getMinecraft().theWorld));
    }

    private static void link(int meta, EntityLivingBase entity) {
        CraftableCreaturesRegistry.linkSoul(CCItems.soul_element, meta, entity);
    }
}