/**
 * File created on 14:13 22.09.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.inventory.InventoryTransmutator;
import ru.wertyfiregames.craftablecreatures.inventory.container.ContainerTransmutator;
import ru.wertyfiregames.craftablecreatures.network.client.MorphClientPacket;

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
        morphButton = new GuiButton(0, (width - xSize) / 2 + 7, (height - ySize) / 2 + 73, 80, 20, I18n.format("gui.transmutator.morphButton"));
        buttonList.add(morphButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        super.actionPerformed(button);
        if (button.id == morphButton.id) {
            CraftableCreatures.NETWORK.sendToServer(new MorphClientPacket());
        }
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
        drawPlayerModel(guiLeft + 142, guiTop + 85, 30, 0, 0, mc.thePlayer);
    }

    private void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity) {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 50.0F);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan(yaw / 40.0F) * 20.0F;
        entity.rotationYaw = (float) Math.atan(yaw / 40.0F) * 40.0F;
        entity.rotationPitch = -((float) Math.atan(pitch / 40.0F)) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
}