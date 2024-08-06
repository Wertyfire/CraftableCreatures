package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class ItemDefault extends Item {
    public ItemDefault(String name, String textureName, CreativeTabs creativeTab) {
        setCreativeTab(creativeTab);
        setUnlocalizedName(name);
        if (textureName != null) {
            setTextureName(CraftableCreatures.getModId() + ":" + textureName);
        } else {
            setTextureName(CraftableCreatures.getModId() + ":unknown_item_texture");
        }
    }

    public ItemDefault(String nameAll, CreativeTabs creativeTab) {
        setCreativeTab(creativeTab);
        setUnlocalizedName(nameAll);
        setTextureName(CraftableCreatures.getModId() + ":" + nameAll);
    }
}