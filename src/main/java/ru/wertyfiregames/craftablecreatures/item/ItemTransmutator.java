/**
 * File created on 18:54 09.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;

public class ItemTransmutator extends ItemDefault {
    public ItemTransmutator() {
        super("transmutator", CCCreativeTabs.tabCraftableCreatures);
        setMaxStackSize(1);
    }
}