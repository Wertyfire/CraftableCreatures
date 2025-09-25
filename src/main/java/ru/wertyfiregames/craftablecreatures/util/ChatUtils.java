/**
 * File created on 20:07 14.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

public class ChatUtils {
    private ChatUtils() {}

    public static void sendMessageWithLink(EntityPlayer player, String beforeLinkKey, String url, String urlKey, String afterLinkKey) {
        TextComponentTranslation beforeLinkText = new TextComponentTranslation(beforeLinkKey);
        beforeLinkText.appendText(" ");
        TextComponentBase urlText = urlKey.startsWith("http") ? new TextComponentString(url) : new TextComponentTranslation(urlKey);
        TextComponentTranslation afterLinkText = new TextComponentTranslation(afterLinkKey);

        Style linkStyle = new Style();
        linkStyle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("chat.wertyfireCore.clickToOpenUrl")));
        linkStyle.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        linkStyle.setColor(TextFormatting.AQUA);
        linkStyle.setUnderlined(true);

        urlText.setStyle(linkStyle);

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
                player.addChatMessage(new TextComponentString(change));
            }
        }
        else {
            for (String change : message[0].split("<n>")) {
                change = change.replace("<t>", "    ");
                player.addChatMessage(new TextComponentString(change));
            }
        }
    }
}