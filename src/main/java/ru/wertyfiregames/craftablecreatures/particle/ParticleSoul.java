/**
 * File created on 13:17 20.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.particle;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleSoul extends Particle {
    private final float soulScale;

    public ParticleSoul(World world, double x, double y, double z, double speedX, double speedY, double speedZ) {
        super(world, x, y, z, speedX, speedY, speedZ);
        this.motionX = this.motionX * 0.009999999776482582D + speedX;
        this.motionY = this.motionY * 0.009999999776482582D + speedY;
        this.motionZ = this.motionZ * 0.009999999776482582D + speedZ;
        posX += (rand.nextFloat() - rand.nextFloat()) * 0.05f;
        posY += (rand.nextFloat() - rand.nextFloat()) * 0.05f;
        posZ += (rand.nextFloat() - rand.nextFloat()) * 0.05f;
        soulScale = particleScale;
        particleRed = 0f;
        particleGreen = 0.933f;
        particleBlue = 1f;
        particleMaxAge = (int) (8d / (Math.random() * 0.8d + 0.2d) + 4);
    }

    public void moveEntity(double x, double y, double z) {
        setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
        resetPositionToBB();
    }

    public void renderParticle(VertexBuffer worldRenderer, Entity entity, float partialTicks, float rotX, float rotZ, float rotYZ, float rotXY, float rotXZ) {
        float factor = (particleAge + partialTicks) / particleMaxAge;
        particleScale = soulScale * (1f - factor * factor * 0.5f);
        super.renderParticle(worldRenderer, entity, partialTicks, rotX, rotZ, rotYZ, rotXY, rotXZ);
    }

    public int getBrightnessForRender(float partialTicks) {
        float factor = (particleAge + partialTicks) / particleMaxAge;
        factor = MathHelper.clamp_float(factor, 0f, 1f);

        int brightness = super.getBrightnessForRender(partialTicks);
        int finalBrightness = brightness & 255 + (int) (factor * 15f * 16f);
        int highBits = brightness >> 16 & 255;

        if (finalBrightness > 240) finalBrightness = 240;

        return finalBrightness | highBits << 16;
    }

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) setExpired();

        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.9599999785423279d;
        motionY *= 0.9599999785423279d;
        motionZ *= 0.9599999785423279d;

        if (isCollided) {
            motionX *= 0.699999988079071d;
            motionZ *= 0.699999988079071d;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory {
        @Override
        public Particle getEntityFX(int particleID, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... params) {
            return new ParticleSoul(world, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}