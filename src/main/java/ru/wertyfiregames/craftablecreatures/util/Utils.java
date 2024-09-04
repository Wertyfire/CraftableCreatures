/**
 * File created on 11:02 18.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class Utils {
//    Credits goes to chatgpt. Why no ChatComponentLink?
    public static void sendClickableLink(EntityPlayer player, String beforeLinkKey, String url, String urlKey, String afterLinkKey) {
        ChatComponentText beforeLinkText = new ChatComponentText(I18n.format(beforeLinkKey) + " ");
        ChatComponentText urlText = new ChatComponentText(I18n.format(urlKey));
        ChatComponentText afterLinkText = new ChatComponentText(I18n.format(afterLinkKey));

        ChatStyle linkStyle = new ChatStyle();
        linkStyle.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentTranslation("chat.craftableCreatures.clickToOpenUrl")));
        linkStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        linkStyle.setColor(EnumChatFormatting.AQUA);
        linkStyle.setUnderlined(true);

        urlText.setChatStyle(linkStyle);

        beforeLinkText.appendSibling(urlText);
        beforeLinkText.appendSibling(afterLinkText);

        player.addChatMessage(beforeLinkText);
    }

    public static class RGBA {
        private final float red, green, blue, alpha;

        private RGBA() {red=0;green=0;blue=0;alpha=0;}

        public RGBA(float red, float green, float blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = 1f;
        }

        public RGBA(float red, float green, float blue, float alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public float r() {
            return red;
        }
        public float g() {
            return green;
        }
        public float b() {
            return blue;
        }
        public float a() {
            return alpha;
        }
    }
}