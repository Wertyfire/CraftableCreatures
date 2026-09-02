/**
 * File created on 17:30 29.04.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiCombiner extends GuiContainer {
    private TileEntityCombiner combinerInventory;

    public GuiCombiner(InventoryPlayer playerInv, TileEntityCombiner combiner) {
        super(new ContainerCombiner(playerInv, combiner));
        combinerInventory = combiner;
    }

    @Override
    protected void drawGuiContainerForegroundLayer() {
        fontRenderer.drawString("Combiner", 73, 6, 4210752);
        fontRenderer.drawString("Inventory", 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float delta, int x, int y) {
        int texture = mc.renderEngine.getTexture("/craftable_creatures/gui/combiner.png");
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(texture);

        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        int scaled = combinerInventory.getCombineProgressScaled(17);
        drawTexturedModalRect(xPos + 60, yPos + 33, 176, 0, 55, scaled);
    }
}