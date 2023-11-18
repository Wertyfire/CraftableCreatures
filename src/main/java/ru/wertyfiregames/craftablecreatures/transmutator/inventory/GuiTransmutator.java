/**
 * File created on 20:41 08.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.transmutator.inventory;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.transmutator.item.ItemTransmutatorInv;

public class GuiTransmutator extends GuiContainer
{
    private float xSize_lo, ySize_lo;

    private static final ResourceLocation guiTextures = new ResourceLocation(CraftableCreatures.getModId(),
            "textures/gui/container/transmutator.png");

    private final ItemTransmutatorInv inventory;

    public GuiTransmutator(ContainerTransmutator containerItem)
    {
        super(containerItem);
        this.inventory = containerItem.inventory;
    }

    @Override
    public void drawScreen(int par1, int par2, float par3)
    {
        super.drawScreen(par1, par2, par3);
        this.xSize_lo = (float) par1;
        this.ySize_lo = (float) par2;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.inventory.hasCustomInventoryName() ? this.inventory.getInventoryName() : I18n.format(this.inventory.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 7, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.hotbar"), 8, this.ySize - 36, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par3, int par1, int par2)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(guiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        drawPlayerModel(k + 51, l + 75, 27, (float) (k + 51) - this.xSize_lo, (float) (l + 75 - 50) - this.ySize_lo, this.mc.thePlayer);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(new GuiButton(1, 430, 270, 100, 20, I18n.format("gui.turn")));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 1)
        {
            System.out.println("[CC] Button 1 in transmutator clicked!!!!!");
        }
    }

    public static void drawPlayerModel(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity)
    {
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
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.9F);
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