/**
 * File created on 19:07 27.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerCombiner;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntityCombiner;

public class GuiCombiner extends GuiContainer {
    private static final ResourceLocation combinerGuiTextures = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/combiner/combiner.png");
    private final TileEntityCombiner tileCombiner;

    public GuiCombiner(InventoryPlayer player, TileEntityCombiner combiner) {
        super(new ContainerCombiner(player, combiner));
        tileCombiner = combiner;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = tileCombiner.hasCustomInventoryName() ? tileCombiner.getInventoryName() : I18n.format("container.combiner");
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float delta, int x, int y) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(combinerGuiTextures);
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        int scaled = tileCombiner.getCombineProgressScaled(17);
        drawTexturedModalRect(xPos + 60, yPos + 33, 176, 0, 55, scaled);
    }
}