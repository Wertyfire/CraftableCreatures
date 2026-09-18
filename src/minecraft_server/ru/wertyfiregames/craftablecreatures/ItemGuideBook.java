/**
 * File created on 21:04 04.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.*;

import java.util.HashMap;
import java.util.Map;

public class ItemGuideBook extends ItemDefault {
    public static final Map<String, Short> lastPageForPlayers = new HashMap();

    public ItemGuideBook(int id) {
        super(id);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(mod_CraftableCreatures.instance, mod_CraftableCreatures.guiGuideBook, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    public static void setLastPageForPlayer(String username, short page) {
        lastPageForPlayers.put(username, page);
    }
    public static short getLastPageForPlayer(String username) {
        return lastPageForPlayers.get(username);
    }
}