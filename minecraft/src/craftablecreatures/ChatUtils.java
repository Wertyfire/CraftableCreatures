/**
 * File created on 13:38 31.08.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.EntityPlayer;

public class ChatUtils {
    public static void sendUpdateChangelogMessage(EntityPlayer player, String s, String currentLanguage) {
//        Order: 0-en_US;1-ru_RU
        String[] message = s.split("<lang>");

        if (currentLanguage.equals("ru_RU")) {
            for (String change : message[1].split("<n>")) {
                change = change.replace("<t>", "    ");
                player.addChatMessage(change);
            }
        }
        else {
            for (String change : message[0].split("<n>")) {
                change = change.replace("<t>", "    ");
                player.addChatMessage(change);
            }
        }
    }
}