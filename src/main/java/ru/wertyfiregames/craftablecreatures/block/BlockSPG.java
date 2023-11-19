/**
 * File created on 17:26 19.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.Material;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class BlockSPG extends BlockDefault
{
    public BlockSPG()
    {
        super(Material.iron, "spawnEggGenerator", "spawn_egg_generator", CCCreativeTabs.tabCraftableCreatures,
                "pickaxe", 2, 5f, 10f);
    }
}