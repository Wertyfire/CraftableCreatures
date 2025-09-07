/**
 * File created on 15:26 29.08.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.init;

import cpw.mods.fml.relauncher.Side;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.network.client.MorphClientPacket;

public class CCPackets {
    public static void register() {
        CraftableCreatures.NETWORK.registerMessage(new MorphClientPacket.Handler(), MorphClientPacket.class, 0, Side.SERVER);
    }
}