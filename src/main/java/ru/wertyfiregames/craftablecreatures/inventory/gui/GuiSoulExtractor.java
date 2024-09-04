/**
 * File created on 12:25 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

@SideOnly(Side.CLIENT)
public class GuiSoulExtractor extends GuiContainer {
    private static final ResourceLocation soulExtractorGuiTextures = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/soul_extractor.png");
    private final TileEntitySoulExtractor tileSoulExtractor;

    public GuiSoulExtractor(InventoryPlayer player, TileEntitySoulExtractor soulExtractor) {
        super(new ContainerSoulExtractor(player, soulExtractor));
        tileSoulExtractor = soulExtractor;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = tileSoulExtractor.hasCustomInventoryName() ? tileSoulExtractor.getInventoryName() : I18n.format(tileSoulExtractor.getInventoryName());
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float delta, int x, int y) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(soulExtractorGuiTextures);
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        if (tileSoulExtractor.hasFuel()) {
            int scaled = tileSoulExtractor.getFuelWorkTimeRemainingScaled(13);
            drawTexturedModalRect(xPos + 42, yPos + 36 + 12 - scaled, 176, 12 - scaled, 14, scaled + 1);
            scaled = tileSoulExtractor.getExtractProgressScaled(42);
            drawTexturedModalRect(xPos + 60, yPos + 20, 176, 14, scaled + 1, 31);
        }
    }
}