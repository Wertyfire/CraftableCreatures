/**
 * File created on 14:15 14.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.world;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.world.gen.OverworldOreGenerator;

public class CCWorldGenVines {
    public static final OverworldOreGenerator BLUESTONE_GENERATOR = new OverworldOreGenerator(CCBlocks.BLUESTONE_ORE, Blocks.STONE,
            16, 16, 3, 8, 3, 5, 1000, 25, 55);

    public static void register() {
        GameRegistry.registerWorldGenerator(BLUESTONE_GENERATOR, 1);
    }
}