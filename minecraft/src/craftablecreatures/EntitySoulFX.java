/**
 * File created on 13:17 20.08.2024 by Wertyfire
 * and then backported for mc 1.1 on 14:28 31.08.2026
 */

package craftablecreatures;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

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

    public void onUpdate() {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;

        if (particleAge++ >= particleMaxAge) setEntityDead();

        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.96d;
        motionY *= 0.96d;
        motionZ *= 0.96d;

        if (onGround) {
            motionX *= 0.7d;
            motionZ *= 0.7f;
        }
    }
}