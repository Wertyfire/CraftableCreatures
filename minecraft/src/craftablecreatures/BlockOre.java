/**
 * File created on 21:35 14.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.Material;
import net.minecraft.src.mod_CraftableCreatures;

import java.util.Random;

public class BlockOre extends BlockDefault {
    public BlockOre(int id, float hardness, float resistance) {
        super(id, Material.rock, hardness, resistance, soundStoneFootstep);
    }

    @Override
    public int idDropped(int metadata, Random random, int fortune) {
        return blockID == mod_CraftableCreatures.CraftableCreaturesIDs.bluestoneOreID ? mod_CraftableCreatures.bluestone.shiftedIndex : blockID;
    }

    @Override
    public int quantityDropped(Random random) {
        return this.blockID == mod_CraftableCreatures.bluestoneOre.blockID ? random.nextInt(2) + 1 : super.quantityDropped(random);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        int quantity = this.quantityDropped(random);
        return quantity + random.nextInt(fortune + 1);
    }
}