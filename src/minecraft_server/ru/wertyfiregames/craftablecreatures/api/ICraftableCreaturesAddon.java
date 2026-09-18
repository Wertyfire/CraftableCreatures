/**
 * File created on 20:19 17.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api;

import java.util.HashSet;
import java.util.Set;

/**
 * Interface implemented by addon's main class.
 * @since 2.0
 * @author Wertyfire
 * */
public interface ICraftableCreaturesAddon {
    /**
     * Load all stuff that interacts with {@linkplain CraftableCreaturesRegistry}
     * @return true, if loaded successfully, otherwise - false (for example if target mod is missing)
     * */
    boolean loadCompatibility();

    /**
     * Get list of addon's dependencies. If they are not present, addon will not load.
     * All dependencies are compared using {@linkplain String#equalsIgnoreCase(String)}
     * */
    default Set<String> getDependencies() {
        return new HashSet<>();
    }

    /**
     * Get addon name. By default, returns class's simple name.
     * */
    default String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get addon version
     * */
    String getVersion();
}