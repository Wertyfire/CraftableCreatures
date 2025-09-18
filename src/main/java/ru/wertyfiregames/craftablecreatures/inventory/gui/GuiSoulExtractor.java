/**
 * File created on 12:25 01.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerSoulExtractor;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;

@SideOnly(Side.CLIENT)
public class GuiSoulExtractor extends GuiContainer {
    private static final ResourceLocation soulExtractorGuiTextures = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/soul_extractor.png");
    private final InventoryPlayer playerInv;
    private final IInventory tileSoulExtractor;

    public GuiSoulExtractor(InventoryPlayer player, IInventory soulExtractor) {
        super(new ContainerSoulExtractor(player, soulExtractor));
        playerInv = player;
        tileSoulExtractor = soulExtractor;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = tileSoulExtractor.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float delta, int x, int y) {
        GlStateManager.color(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(soulExtractorGuiTextures);
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        if (TileEntitySoulExtractor.isWorking(tileSoulExtractor)) {
            int scaled = getWorkLeftScaled(13);
            drawTexturedModalRect(xPos + 42, yPos + 36 + 12 - scaled, 176, 12 - scaled, 14, scaled + 1);
        }
        int scaled = getExtractProgressScaled(42);
        drawTexturedModalRect(xPos + 60, yPos + 20, 176, 14, scaled + 1, 31);
    }

    private int getExtractProgressScaled(int pixels) {
        int current = tileSoulExtractor.getField(2);
        int total = tileSoulExtractor.getField(3);
        return total != 0 && current != 0 ? current * pixels / total : 0;
    }

    private int getWorkLeftScaled(int pixels) {
        int i = tileSoulExtractor.getField(1);
        if (i == 0) i = 200;
        return tileSoulExtractor.getField(0) * pixels / i;
    }
}