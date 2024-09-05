/**
 * File created on 13:01 09.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.init;

import ru.wertyfiregames.craftablecreatures.api.CraftableCreaturesRegistry;

public class CCAPIInit {
    public static void register() {
        CraftableCreaturesRegistry.registerItemAsSoul(CCItems.soul_element);
    }
}