/**
 * File created on 21:46 17.03.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiSoulExtractor extends GuiContainer {
    private TileEntitySoulExtractor soulExtractorInventory;

    public GuiSoulExtractor(InventoryPlayer playerInv, TileEntitySoulExtractor soulExtractor) {
        super(new ContainerSoulExtractor(playerInv, soulExtractor));
        soulExtractorInventory = soulExtractor;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        fontRenderer.drawString("Soul Extractor", 60, 6, 4210752);
        fontRenderer.drawString("Inventory", 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float delta, int x, int y) {
        int texture = mc.renderEngine.getTexture("/craftable_creatures/gui/soul_extractor.png");
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(texture);

        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        if (soulExtractorInventory.hasFuel()) {
            int scaled = soulExtractorInventory.getFuelWorkTimeRemainingScaled(13);
            drawTexturedModalRect(xPos + 42, yPos + 36 + 12 - scaled, 176, 12 - scaled, 14, scaled + 1);
            scaled = soulExtractorInventory.getExtractProgressScaled(42);
            drawTexturedModalRect(xPos + 60, yPos + 20, 176, 14, scaled + 1, 31);
        }
    }
}