/**
 * File created on 22:32 04.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.*;

import java.util.ArrayList;

public class BlockDefault extends Block {
    public BlockDefault(int id, int textureIndex, Material material, float hardness, float resistance, StepSound stepSound) {
        super(id, textureIndex, material);
        setTextureFile(mod_CraftableCreatures.blockAtlas);
        setHardness(hardness);
        setResistance(resistance);
        setStepSound(stepSound);
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        itemList.add(new ItemStack(this));
    }
}