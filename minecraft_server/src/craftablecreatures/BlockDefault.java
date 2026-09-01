/**
 * File created on 16:44 13.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;

import java.util.ArrayList;

public class BlockDefault extends Block {
    public BlockDefault(int id, Material material, float hardness, float resistance, StepSound stepSound) {
        super(id, material);
        setHardness(hardness);
        setResistance(resistance);
        setStepSound(stepSound);
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }
}