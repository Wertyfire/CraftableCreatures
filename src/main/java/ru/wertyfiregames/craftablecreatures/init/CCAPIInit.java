/**
 * File created on 13:01 09.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;

public class CCAPIInit {
    public static void register() {
        CraftableCreaturesRegistry.registerItemAsSoul(CCItems.soul);

        link(1, new EntityCreeper(null));
        link(2, new EntitySkeleton(null));
        link(3, new EntitySpider(null));
        link(4, new EntityZombie(null));
        link(5, new EntitySlime(null));
        link(6, new EntityGhast(null));
        link(7, new EntityPigZombie(null));
        link(8, new EntityEnderman(null));
        link(9, new EntityCaveSpider(null));
        link(10, new EntitySilverfish(null));
        link(11, new EntityBlaze(null));
        link(12, new EntityMagmaCube(null));
        link(13, new EntityBat(null));
        link(14, new EntityWitch(null));
        link(15, new EntityPig(null));
        link(16, new EntitySheep(null));
        link(17, new EntityCow(null));
        link(18, new EntityChicken(null));
        link(19, new EntitySquid(null));
        link(20, new EntityWolf(null));
        link(21, new EntityMooshroom(null));
        link(22, new EntityOcelot(null));
//        link(23, new EntityHorse(Minecraft.getMinecraft().theWorld)); //This shit throws null pointer exception
        link(24, new EntityVillager(null));
    }

    private static void link(int meta, EntityLivingBase entity) {
        CraftableCreaturesRegistry.linkSoul(CCItems.soul, meta, entity);
    }
}