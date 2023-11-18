package ru.wertyfiregames.craftablecreatures.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public abstract class CCEntityFX extends EntityFX {
    public CCEntityFX(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public CCEntityFX(World world, double x, double y, double z, double velX, double velY, double velZ)
    {
        super(world, x, y, z, velX, velY, velZ);
    }

    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float par3, float par4, float par5, float par6, float par7)
    {
        Minecraft.getMinecraft().renderEngine.bindTexture(getIconPath());
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
        GL11.glDisable(GL11.GL_CULL_FACE);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(getBrightnessForRender(partialTicks));
        float scale = 0.1F * particleScale;
        float x = (float)(prevPosX + (posX - prevPosX) * partialTicks - interpPosX);
        float y = (float)(prevPosY + (posY - prevPosY) * partialTicks - interpPosY);
        float z = (float)(prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ);
        tessellator.addVertexWithUV(x - par3 * scale - par6 * scale, y - par4 * scale, z - par5 * scale - par7 * scale, 0.0, 0.0);
        tessellator.addVertexWithUV(x - par3 * scale + par6 * scale, y + par4 * scale, z - par5 * scale + par7 * scale, 1.0, 0.0);
        tessellator.addVertexWithUV(x + par3 * scale + par6 * scale, y + par4 * scale, z + par5 * scale + par7 * scale,1.0, 1.0);
        tessellator.addVertexWithUV(x + par3 * scale - par6 * scale, y - par4 * scale, z + par5 * scale - par7 * scale, 0.0, 1.0);
        tessellator.draw();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
    }

    protected abstract ResourceLocation getIconPath();

    @Override
    public int getFXLayer()
    {
        return 3;
    }

    public CCEntityFX setMaxAge(int maxAge)
    {
        particleMaxAge = (int) (maxAge - (Math.random()*(maxAge - 1)));
        return this;
    }

    public CCEntityFX setGravity(double gravity)
    {
        particleGravity = (float)gravity;
        return this;
    }

    public CCEntityFX setScale(double scale)
    {
        particleScale = (float)scale;
        return this;
    }
}