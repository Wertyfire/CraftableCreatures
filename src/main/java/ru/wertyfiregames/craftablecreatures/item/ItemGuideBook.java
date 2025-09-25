package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGuideBook extends DefaultItem {
    public static final Map<String, Short> lastPageForPlayers = new HashMap<>();

    public ItemGuideBook() {
        super("guideBook", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("craftableCreatures.guide.page1.author") + " " + "Wertyfire");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_GUIDE_BOOK, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public static void setLastPageForPlayer(String uuid, short page) {
        lastPageForPlayers.put(uuid, page);
    }
    public static short getLastPageForPlayer(String uuid) {
        return lastPageForPlayers.get(uuid);
    }
}