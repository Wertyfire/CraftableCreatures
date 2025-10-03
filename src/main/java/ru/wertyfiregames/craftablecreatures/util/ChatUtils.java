/**
 * File created on 23:19 22.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;

public class ChatUtils {
    private ChatUtils() {}

    public static void sendMessageWithLink(EntityPlayer player, String beforeLinkKey, String url, String urlKey, String afterLinkKey) {
        ChatComponentText beforeLinkText = new ChatComponentText(StatCollector.translateToLocal(beforeLinkKey) + " ");
        ChatComponentText urlText = urlKey.startsWith("http") ? new ChatComponentText(url) : new ChatComponentText(StatCollector.translateToLocal(urlKey));
        ChatComponentText afterLinkText = new ChatComponentText(StatCollector.translateToLocal(afterLinkKey));

        ChatStyle linkStyle = new ChatStyle();
        linkStyle.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentTranslation("chat.wertyfireCore.clickToOpenUrl")));
        linkStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        linkStyle.setColor(EnumChatFormatting.AQUA);
        linkStyle.setUnderlined(true);

        urlText.setChatStyle(linkStyle);

        beforeLinkText.appendSibling(urlText);
        beforeLinkText.appendSibling(afterLinkText);

        player.addChatMessage(beforeLinkText);
    }

    public static void sendUpdateChangelogMessage(EntityPlayer player, String s) {
//        Order: 0-en_US;1-ru_RU
        String[] message = s.split("<lang>");
        String currentLanguage = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        if (currentLanguage.equals("ru_RU")) {
            for (String change : message[1].split("<n>")) {
                change = change.replace("<t>", "    ");
                player.addChatMessage(new ChatComponentText(change));
            }
        }
        else {
            for (String change : message[0].split("<n>")) {
                change = change.replace("<t>", "    ");
                player.addChatMessage(new ChatComponentText(change));
            }
        }
    }
}