package ru.wertyfiregames.craftablecreatures.item;

import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDefault extends Item {
    public ItemDefault(String name, String textureName, CreativeTabs creativeTab) {
        this.setCreativeTab(creativeTab);
        this.setUnlocalizedName(name);
        if (textureName != null) {
            this.setTextureName(CraftableCreatures.getModId() + ":" + textureName);
        } else {
            this.setTextureName(CraftableCreatures.getModId() + ":unknown_item_texture");
        }
    }
    public ItemDefault(String nameAll, CreativeTabs creativeTab) {
        this.setCreativeTab(creativeTab);
        this.setUnlocalizedName(nameAll);
        this.setTextureName(CraftableCreatures.getModId() + ":" + nameAll);
    }
}