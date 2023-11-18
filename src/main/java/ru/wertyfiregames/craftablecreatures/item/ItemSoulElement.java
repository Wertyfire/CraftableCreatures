package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

import java.util.List;

public class ItemSoulElement extends ItemDefault {
    public static String[] unlocalizedName = new String[] {"", ".creeper", ".skeleton", ".spider", ".zombie", ".slime", ".ghast", ".zombiePigman", ".enderman", ".caveSpider", ".silverfish", ".blaze", ".magmaCube", ".bat", ".witch", ".pig", ".sheep", ".cow", ".chicken", ".squid", ".wolf", ".mooshroom", ".ocelot", ".horse", ".villager"};
    public static String[] textureName = new String[] {"", "_creeper", "_skeleton", "_spider", "_zombie", "_slime", "_ghast", "_zombie_pigman", "_enderman", "_cave_spider", "_silverfish", "_blaze", "_magma_cube", "_bat", "_witch", "_pig", "_sheep", "_cow", "_chicken", "_squid", "_wolf", "_mooshroom", "_ocelot", "_horse", "_villager"};
    private IIcon[] icon;

    public ItemSoulElement() {
        super("soul", "souls/soul", CCCreativeTabs.tabCraftableCreatures);
        this.setHasSubtypes(true);
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        int j = MathHelper.clamp_int(damage, 0, 25);
        return icon[j];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 25);
        return super.getUnlocalizedName() + unlocalizedName[i];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < 25; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        icon = new IIcon[textureName.length];

        for (int i = 0; i < textureName.length; i++) {
            icon[i] = iconRegister.registerIcon(this.getIconString() + textureName[i]);
        }
    }
}