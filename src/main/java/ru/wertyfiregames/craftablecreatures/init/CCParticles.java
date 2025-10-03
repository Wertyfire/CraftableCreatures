/**
    File created on 13:42 20.08.2024 by Wertyfire
*/

package ru.wertyfiregames.craftablecreatures.init;

import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.particle.*;
import ru.wertyfiregames.craftablecreatures.util.ParticleUtils;

public class CCParticles {
    public static final String soul = CraftableCreatures.getModId() + ":soul";

    public static void register() {
        ParticleUtils.register(EntitySoulFX.class, soul);
    }
}