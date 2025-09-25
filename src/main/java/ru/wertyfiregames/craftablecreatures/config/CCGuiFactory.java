package ru.wertyfiregames.craftablecreatures.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

import java.util.Set;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.getConfig;

public class CCGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return CCGuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class CCGuiConfig extends GuiConfig {
        public CCGuiConfig(GuiScreen guiScreen) {
            super(guiScreen, new ConfigElement(getConfig().getCategory(CCConfig.CATEGORY_CRAFTABLE_CREATURES)).getChildElements(),
                    CraftableCreatures.getModId(), false, false,
                    GuiConfig.getAbridgedConfigPath(getConfig().toString()));
        }
    }
}