/**
 * File created on 16:22 18.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

public class GuiSoulExtractor extends GuiContainer {
    private static final ResourceLocation TEXTURES = new ResourceLocation(CraftableCreatures.getModId() +
            ":textures/gui/container/soul_extractor.png");
    private final InventoryPlayer player;
    private final TileEntitySoulExtractor tile;

    public GuiSoulExtractor(InventoryPlayer player, TileEntitySoulExtractor soulExtractor) {
        super(new ContainerSoulExtractor(player, soulExtractor));
        this.player = player;
        tile = soulExtractor;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = tile.getDisplayName().getUnformattedText();
        fontRenderer.drawString(title, (xSize / 2 - fontRenderer.getStringWidth(title) / 2 + 3), 8, 4210752);
        fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 122, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(TEXTURES);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (TileEntitySoulExtractor.isBurning(tile)) {
            int k = getBurnLeftScaled(13);
            drawTexturedModalRect(guiLeft + 8, guiTop + 54 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = getCookProgressScaled(41);
        drawTexturedModalRect(guiLeft + 62, guiTop + 36, 176, 14, l + 1, 17);
    }

    private int getBurnLeftScaled(int pixels) {
        int i = tile.getField(1);
        if (i == 0) i = 200;
        return tile.getField(0) * pixels / i;
    }

    private int getCookProgressScaled(int pixels) {
        int i = tile.getField(2);
        int j = tile.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }
}