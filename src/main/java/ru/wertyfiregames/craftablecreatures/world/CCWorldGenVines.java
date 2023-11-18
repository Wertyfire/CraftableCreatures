package ru.wertyfiregames.craftablecreatures.world;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.block.CCBlocks;
import ru.wertyfiregames.craftablecreatures.world.gen.OverworldOreGenerator;
import net.minecraft.init.Blocks;

import static ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler.*;

public class CCWorldGenVines
{
    public static final OverworldOreGenerator bluestone_generator = new OverworldOreGenerator(CCBlocks.bluestone_ore, Blocks.stone,
            16, 16, 3, 8, 3, 5,
            1000, 25, 55);

    public static void register()
    {
        GameRegistry.registerWorldGenerator(bluestone_generator, 1);
    }
}