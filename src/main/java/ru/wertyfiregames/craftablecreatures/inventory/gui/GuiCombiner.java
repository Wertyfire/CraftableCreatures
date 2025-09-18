/**
 * File created on 19:07 27.08.2024 by Wertyfire
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
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerCombiner;

@SideOnly(Side.CLIENT)
public class GuiCombiner extends GuiContainer {
    private static final ResourceLocation combinerGuiTextures = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/combiner.png");
    private final InventoryPlayer playerInv;
    private IInventory tileSoulExtractor;

    public GuiCombiner(InventoryPlayer player, IInventory combiner) {
        super(new ContainerCombiner(player, combiner));
        playerInv = player;
        tileSoulExtractor = combiner;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = tileSoulExtractor.getDisplayName().getUnformattedText();
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
        GlStateManager.color(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(combinerGuiTextures);
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);

        int scaled = getCombineProgressScaled(17);
        System.out.println("scaled=" + scaled);
        System.out.println("field0=" + tileSoulExtractor.getField(0));
        drawTexturedModalRect(xPos + 60, yPos + 33, 176, 0, 55, scaled);
    }

    private int getCombineProgressScaled(int pixels) {
        int current = tileSoulExtractor.getField(0);
        int total = tileSoulExtractor.getField(1);
        return total != 0  && current != 0 ? current * pixels / total : 0;
    }
}