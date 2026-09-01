/**
 File created on 15:18 22.08.2024 by Wertyfire
 and then backported for mc 1.1 on 14:30 31.08.2026
*/

package craftablecreatures;

import net.minecraft.src.EntityFX;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;
import net.minecraft.src.mod_CraftableCreatures;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ParticleUtils {
    private ParticleUtils() {}

    private static final Map<String, Class<? extends EntityFX>> effects = new HashMap<>();

    public static void register(Class<? extends EntityFX> particle, String name) {
        effects.put(name, particle);
    }

    public static void spawnParticle(String name, double x, double y, double z, double motionX, double motionY, double motionZ) {
        Class<? extends EntityFX> clazz = effects.get(name);
        EntityFX particle = null;
        try {
            Constructor<? extends EntityFX> constructor = clazz.getDeclaredConstructor(World.class, double.class, double.class, double.class, double.class, double.class, double.class);
            particle = constructor.newInstance(ModLoader.getMinecraftInstance().theWorld, x, y, z, motionX, motionY, motionZ);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NullPointerException e) {
            mod_CraftableCreatures.err("Could not create particle: " + e.getMessage());
        }

        if (particle != null) ModLoader.getMinecraftInstance().effectRenderer.addEffect(particle);
    }
}