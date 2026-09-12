/**
 * File created on 20:28 12.09.2026 by Wertyfire
 */

package craftablecreatures.api;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

/**
 * Empty registrar set by default before changing to working implementation.
 * @author Wertyfire
 * */
public class CCEmptyRegistrar implements CCRegistrar {
    @Override
    public void info(String message) {}
    @Override
    public void info(String message, Object... format) {}
    @Override
    public void err(String message) {}
    @Override
    public void err(String message, Object... format) {}
    @Override
    public void addExtracting(Block input, ItemStack output) {}
    @Override
    public void addExtracting(Item input, ItemStack output) {}
    @Override
    public void addExtracting(ItemStack input, ItemStack output) {}
    @Override
    public void addCombining(Item firstInput, Item secondInput, ItemStack output) {}
    @Override
    public void addCombining(ItemStack firstInput, Item secondInput, ItemStack output) {}
    @Override
    public void addCombining(Item firstInput, ItemStack secondInput, ItemStack output) {}
    @Override
    public void addCombining(ItemStack firstInput, ItemStack secondInput, ItemStack output) {}
}