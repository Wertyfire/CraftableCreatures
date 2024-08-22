/**
 * File created on 13:17 20.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntitySoulFX extends EntityFX {
    private final float soulScale;

    public EntitySoulFX(World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.009999999776482582D + motionX;
        this.motionY = this.motionY * 0.009999999776482582D + motionY;
        this.motionZ = this.motionZ * 0.009999999776482582D + motionZ;
        soulScale = particleScale;
        particleRed = 0f;
        particleGreen = 0.933f;
        particleBlue = 1f;
        particleMaxAge = (int) (8d / (Math.random() * 0.8d + 0.2d) + 4);
        noClip = true;
    }

    public void renderParticle(Tessellator tessellator, float partialTicks, float x, float y, float z, float rotationX, float rotationZ) {
        float factor = (particleAge + partialTicks) / particleMaxAge;
        particleScale = soulScale * (1f - factor * factor);
        super.renderParticle(tessellator, partialTicks, x, y, z, rotationX, rotationZ);
    }

    public int getBrightnessForRender(float partialTicks) {
        float factor = (particleAge + partialTicks) / particleMaxAge;

        if (factor < 0f) factor = 0f;
        if (factor > 1f) factor = 1f;

        int brightness = super.getBrightnessForRender(partialTicks);
        int finalBrightness = brightness & 255 + (int) (factor * 15f * 16f);
        int highBits = brightness >> 16 & 255;

        if (finalBrightness > 240) finalBrightness = 240;

        return finalBrightness | highBits << 16;
    }

    public float getBrightness(float partialTicks) {
        float factor = (particleAge + partialTicks) / particleMaxAge;

        if (factor < 0f) factor = 0f;
        if (factor > 1f) factor = 1f;

        float baseBrightness = super.getBrightness(partialTicks);
        return baseBrightness * factor + (1f - factor);
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) setDead();

        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9599999785423279d;
        motionY *= 0.9599999785423279d;
        motionZ *= 0.9599999785423279d;

        if (onGround) {
            motionX *= 0.699999988079071d;
            motionZ *= 0.699999988079071d;
        }
    }
}