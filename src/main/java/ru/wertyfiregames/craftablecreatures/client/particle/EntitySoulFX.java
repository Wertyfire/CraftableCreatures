package ru.wertyfiregames.craftablecreatures.client.particle;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

public class EntitySoulFX extends CCEntityFX
{
    public EntitySoulFX(World world, double x, double y, double z) {
        super(world, x, y, z);
        this.setMaxAge(40);
        this.setGravity(-0.2);
    }

    public static ResourceLocation particle = new ResourceLocation(CraftableCreatures.modId, "textures/particles/soul.png");

    @Override
    protected ResourceLocation getIconPath()
    {
        return particle;
    }
}