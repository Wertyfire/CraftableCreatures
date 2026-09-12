/**
 * File created on 13:29 12.09.2026 by Wertyfire
 */

package craftablecreatures.api;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

/**
 * Registering interface to avoid crashes when Craftable Creatures not installed or running in dev environment.
 * @see CCEmptyRegistrar
 * @author Wertyfire
 * */
public interface CCRegistrar {
    void info(String message);
    void info(String message, Object... format);
    void err(String message);
    void err(String message, Object... format);

    void addExtracting(Block input, ItemStack output);
    void addExtracting(Item input, ItemStack output);
    void addExtracting(ItemStack input, ItemStack output);

    void addCombining(Item firstInput, Item secondInput, ItemStack output);
    void addCombining(ItemStack firstInput, Item secondInput, ItemStack output);
    void addCombining(Item firstInput, ItemStack secondInput, ItemStack output);
    void addCombining(ItemStack firstInput, ItemStack secondInput, ItemStack output);
}