/**
 * File created on 16:44 13.03.2026 by Wertyfire
 */

package craftablecreatures;

import forge.ITextureProvider;
import net.minecraft.src.*;

import java.util.ArrayList;

public class BlockDefault extends Block implements ITextureProvider {
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

    @Override
    public String getTextureFile() {
        return mod_CraftableCreatures.blockAtlas;
    }

    public Block setTextureIndex(int index) {
        this.blockIndexInTexture = index;
        return this;
    }

    @Override
    public String translateBlockName() {
        return TranslateUtils.translate(getBlockName() + ".name");
    }
}