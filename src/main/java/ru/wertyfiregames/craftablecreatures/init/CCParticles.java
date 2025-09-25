/**
    File created on 13:42 20.08.2024 by Wertyfire
*/

package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.client.Minecraft;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.particle.ParticleSoul;

public class CCParticles {
    public static final int soul = 1094312;

    public static void register() {
        Minecraft.getMinecraft().effectRenderer.registerParticle(soul, new ParticleSoul.Factory());
    }
}