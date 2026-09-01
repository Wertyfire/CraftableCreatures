/**
 * File created on 20:44 30.08.2026 by Wertyfire
 */

package craftablecreatures;

import forge.ITextureProvider;
import net.minecraft.src.Item;
import net.minecraft.src.mod_CraftableCreatures;

public class ItemDefault extends Item implements ITextureProvider {
    public ItemDefault(int id) {
        super(id);
    }

    @Override
    public String getTextureFile() {
        return mod_CraftableCreatures.itemAtlas;
    }
}