/**
 * File created on 13:26 20.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ParticleUtils {
    private static final Map<String, Class<? extends EntityFX>> effects = new HashMap<>();

    public static void register(Class<? extends EntityFX> particle, String name) {
        effects.put(name, particle);
    }

    public static void spawnParticle(String name, double x, double y, double z, double motionX, double motionY, double motionZ) {
        Class<? extends EntityFX> clazz = effects.get(name);
        EntityFX particle = null;
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class);
            particle = (EntityFX) constructor.newInstance(Minecraft.getMinecraft().theWorld, x, y, z, motionX, motionY, motionZ);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            CraftableCreatures.getModLogger().error("Failed to create particle: {}", e.getMessage());
        }

        if (particle != null) Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}