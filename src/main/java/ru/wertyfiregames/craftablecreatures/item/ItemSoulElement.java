/**
 * File created on 14:44 10.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemSoulElement extends Item {
    public ItemSoulElement() {
        setUnlocalizedName("soul");
        setRegistryName("soul");
        setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        if (stack.getMetadata() == 0)
            return super.getUnlocalizedName();
        return super.getUnlocalizedName() + "." + SoulElementLocS.getByMeta(i);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab == CCCreativeTabs.tabCraftableCreatures) {
            for (SoulElementTypes type : SoulElementTypes.values()) {
                items.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }

    public enum SoulElementTypes {
        default_, creeper, skeleton, spider,
        zombie, slime, ghast, zombie_pigman,
        enderman, cave_spider, silverfish, blaze,
        magma_cube, bat, witch, pig,
        sheep, cow, chicken, squid,
        wolf, mooshroom, ocelot, horse,
        villager, donkey, llama, mule,
        parrot, rabbit, skeleton_horse, zombie_horse,
        elder_guardian, endermite, evoker, iron_golem,
        guardian, husk, illusion_illager, polar_bear,
        shulker, snowman, spellcaster_illager, vex,
        vindicator, wither_skeleton, zombie_villager;
    }

    private enum SoulElementLocS {
        default_, creeper, skeleton, spider,
        zombie, slime, ghast, zombiePigman,
        enderman, caveSpider, silverfish, blaze,
        magmaCube, bat, witch, pig,
        sheep, cow, chicken, squid,
        wolf, mooshroom, ocelot, horse,
        villager, donkey, llama, mule,
        parrot, rabbit, skeletonHorse, zombieHorse,
        elderGuardian, endermite, evoker, ironGolem,
        guardian, husk, illusionIllager, polarBear,
        shulker, snowman, spellcasterIllager, vex,
        vindicator, witherSkeleton, zombieVillager;

        private static SoulElementLocS getByMeta(int index) {
            return values()[index % values().length];
        }
    }
}