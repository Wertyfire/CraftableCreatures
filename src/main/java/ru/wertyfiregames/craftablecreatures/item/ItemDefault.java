package ru.wertyfiregames.craftablecreatures.item;

import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.stats.CCAchievementList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDefault extends Item {
    public ItemDefault(String name, String textureName, CreativeTabs creativeTab) {
        this.setCreativeTab(creativeTab);
        this.setUnlocalizedName(name);
        if (textureName != null) {
            this.setTextureName(CraftableCreatures.modId + ":" + textureName);
        } else {
            this.setTextureName(CraftableCreatures.modId + ":unknown_item_texture");
        }
    }
    public ItemDefault(String nameAll, CreativeTabs creativeTab) {
        this.setCreativeTab(creativeTab);
        this.setUnlocalizedName(nameAll);
        this.setTextureName(CraftableCreatures.modId + ":" + nameAll);
    }
}