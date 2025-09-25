/**
 * File created on 14:30 23.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.proxy;

import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.init.CCParticles;

public class ClientProxy extends CommonProxy {
    public void registerRenders() {
        CCItems.registerRenders();
        CCBlocks.registerRenders();
        CraftableCreatures.getModLogger().debug("CC Renders loaded");
    }

    public void registerParticles() {
        CCParticles.register();
        CraftableCreatures.getModLogger().debug("CC Particles loaded");
    }
}