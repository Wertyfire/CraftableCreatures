package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.wertyfirecore.item.DefaultItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGuideBook extends DefaultItem {
    public static final Map<String, Short> lastPageForPlayers = new HashMap<>();

    public ItemGuideBook() {
        super("guideBook", "guide_book", CCCreativeTabs.tabCraftableCreatures);
        setMaxStackSize(1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean flag) {
        tooltip.add(I18n.format("craftableCreatures.guide.page1.author") + " " + "Wertyfire");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_GUIDE_BOOK, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return stack;
    }

    public static void setLastPageForPlayer(String uuid, short page) {
        lastPageForPlayers.put(uuid, page);
    }
    public static short getLastPageForPlayer(String uuid) {
        return lastPageForPlayers.get(uuid);
    }
}