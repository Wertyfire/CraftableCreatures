/**
 * File created on 19:04 04.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import net.minecraft.src.Item;
import net.minecraft.src.mod_CraftableCreatures;

public class ItemDefault extends Item {
    public ItemDefault(int id) {
        super(id);
        setTextureFile(mod_CraftableCreatures.itemAtlas);
    }
}