package ru.wertyfiregames.craftablecreatures.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import static ru.wertyfiregames.craftablecreatures.common.config.CCConfigHandler.*;

public class CCGuiConfig extends GuiConfig
{
    public CCGuiConfig(GuiScreen guiScreen)
    {
        super(guiScreen, new ConfigElement(getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                CraftableCreatures.getModId(), false, false,
                GuiConfig.getAbridgedConfigPath(getConfig().toString()));
    }
}