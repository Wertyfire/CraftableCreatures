/**
 * File created on 15:26 15.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.util.IStringSerializable;

public enum EnumSoulElement implements IStringSerializable {
    PLAYER(0, "player", "player"),
    CREEPER(1, "creeper", "creeper"),
    SKELETON(2, "skeleton", "skeleton"),
    SPIDER(3, "spider", "spider"),
    ZOMBIE(4, "zombie", "zombie"),
    SLIME(5, "slime", "slime"),
    GHAST(6, "ghast", "ghast"),
    ZOMBIE_PIGMAN(7, "zombie_pigman", "zombiePigman"),
    ENDERMAN(8, "enderman", "enderman"),
    CAVE_SPIDER(9, "cave_spider", "caveSpider"),
    SILVERFISH(10, "silverfish", "silverfish"),
    BLAZE(11, "blaze", "blaze"),
    MAGMA_CUBE(12, "magma_cube", "magmaCube"),
    BAT(13, "bat", "bat"),
    WITCH(14, "witch", "witch"),
    PIG(15, "pig", "pig"),
    SHEEP(16, "sheep", "sheep"),
    COW(17, "cow", "cow"),
    CHICKEN(18, "chicken", "chicken"),
    SQUID(19, "squid", "squid"),
    WOLF(20, "wolf", "wolf"),
    MOOSHROOM(21, "mooshroom", "mooshroom"),
    OCELOT(22, "ocelot", "ocelot"),
    HORSE(23, "horse", "horse"),
    VILLAGER(24, "villager", "villager"),
    ENDERMITE(25, "endermite", "endermite"),
    GUARDIAN(26, "guardian", "guardian"),
    RABBIT(27, "rabbit", "rabbit");

    private static final EnumSoulElement[] META_LOOKUP = new EnumSoulElement[values().length];
    private final int meta;
    private final String name;
    private final String unlocalizedName;

    EnumSoulElement(int meta, String name, String unlocalizedName) {
        this.meta = meta;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
    }

    public int getMetadata() {
        return meta;
    }

    public String getUnlocalizedName() {
        return "." + unlocalizedName;
    }

    public static EnumSoulElement byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;

        return META_LOOKUP[meta];
    }

    public String toString() {
        return unlocalizedName;
    }

    public String getName() {
        return name;
    }

    static {
        for (EnumSoulElement _enum : values()) {
            META_LOOKUP[_enum.getMetadata()] = _enum;
        }
    }
}