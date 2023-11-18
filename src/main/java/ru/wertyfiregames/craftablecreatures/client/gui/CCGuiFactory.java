package ru.wertyfiregames.craftablecreatures.client.gui;

import cpw.mods.fml.client.IModGuiFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ru.wertyfiregames.craftablecreatures.item.CCItems;

import java.util.Set;

public class CCGuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance)
    {

    }
    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return CCGuiConfig.class;
    }
    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        return null;
    }
    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    {
        return null;
    }
}