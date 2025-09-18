/**
 * File created on 19:47 16.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.api;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IMetaItemModel {
    @SideOnly(Side.CLIENT)
    void register();
}