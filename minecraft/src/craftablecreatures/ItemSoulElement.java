/**
 * File created on 15:17 13.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.mod_CraftableCreatures;

import java.util.ArrayList;

public class ItemSoulElement extends ItemDefault {
    public static final String[] soulNames = { "", "mooshroom", "creeper", "skeleton", "spider", "zombie", "slime", "ghast", "villager", "zombiePigman", "enderman", "pig", "caveSpider", "sheep", "silverfish", "cow", "blaze", "chicken", "magmaCube", "squid", "wolf" };
    public static final String[] soulTextureNames = { "", "mooshroom", "creeper", "skeleton", "spider", "zombie", "slime", "ghast", "villager", "zombie_pigman", "enderman", "pig", "cave_spider", "sheep", "silverfish", "cow", "blaze", "chicken", "magma_cube", "squid", "wolf" };
    public static final int[] soulTextures = new int[21];

    public ItemSoulElement() {
        super(mod_CraftableCreatures.CraftableCreaturesIDs.soulElementID);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getIconFromDamage(int i) {
        int texId = MathHelper.clamp_int(i, 0, 20);
        return soulTextures[texId];
    }

    @Override
    public String getItemNameIS(ItemStack itemStack) {
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, 20);
        if (meta == 0) return getItemName();
        return getItemName() + "." + soulNames[meta];
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        for (int x = 0; x < 21; x++) {
            itemList.add(new ItemStack(this, 1, x));
        }
    }
}