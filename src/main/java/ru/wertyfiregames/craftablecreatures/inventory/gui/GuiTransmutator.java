/**
 * File created on 14:13 22.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.InventoryTransmutator;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerTransmutator;

@SideOnly(Side.CLIENT)
@SuppressWarnings("unchecked")
public class GuiTransmutator extends GuiContainer {
    private static final ResourceLocation transmutatorGuiTextures = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/container/transmutator.png");
    private final InventoryTransmutator inventoryTransmutator;

    private GuiButton morphButton;

    public GuiTransmutator(InventoryPlayer player, InventoryTransmutator transmutator) {
        super(new ContainerTransmutator(player, transmutator));
        ySize = 194;
        inventoryTransmutator = transmutator;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();
        morphButton = new GuiButton(0, (width - xSize) / 2 + 7, (height - ySize) / 2 + 19, 80, 20, I18n.format("gui.transmutator.morphButton"));
        buttonList.add(morphButton);
    }

    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = inventoryTransmutator.hasCustomInventoryName() ? inventoryTransmutator.getInventoryName() : I18n.format(inventoryTransmutator.getInventoryName());
        fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.getTextureManager().bindTexture(transmutatorGuiTextures);
        int xPos = (width - xSize) / 2;
        int yPos = (height - ySize) / 2;
        drawTexturedModalRect(xPos, yPos, 0, 0, xSize, ySize);
    }
}