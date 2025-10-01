/**
 * File created on 15:26 15.09.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.util.IStringSerializable;

public enum EnumSoulElement implements IStringSerializable {
    PLAYER(0, "player", "player", 0),
    CREEPER(1, "creeper", "creeper", 1),
    SKELETON(2, "skeleton", "skeleton", 2),
    SPIDER(3, "spider", "spider", 3),
    ZOMBIE(4, "zombie", "zombie", 4),
    SLIME(5, "slime", "slime", 5),
    GHAST(6, "ghast", "ghast", 6),
    ZOMBIE_PIGMAN(7, "zombie_pigman", "zombiePigman", 7),
    ENDERMAN(8, "enderman", "enderman", 8),
    CAVE_SPIDER(9, "cave_spider", "caveSpider", 9),
    SILVERFISH(10, "silverfish", "silverfish", 10),
    BLAZE(11, "blaze", "blaze", 11),
    MAGMA_CUBE(12, "magma_cube", "magmaCube", 12),
    BAT(13, "bat", "bat", 13),
    WITCH(14, "witch", "witch", 14),
    PIG(15, "pig", "pig", 18),
    SHEEP(16, "sheep", "sheep", 19),
    COW(17, "cow", "cow", 20),
    CHICKEN(18, "chicken", "chicken", 21),
    SQUID(19, "squid", "squid", 22),
    WOLF(20, "wolf", "wolf", 23),
    MOOSHROOM(21, "mooshroom", "mooshroom", 24),
    OCELOT(22, "ocelot", "ocelot", 25),
    HORSE(23, "horse", "horse", 26),
    VILLAGER(24, "villager", "villager", 28),
    ENDERMITE(25, "endermite", "endermite", 15),
    GUARDIAN(26, "guardian", "guardian", 16),
    RABBIT(27, "rabbit", "rabbit", 27),
    SHULKER(28, "shulker", "shulker", 17),;

    private static final EnumSoulElement[] META_LOOKUP = new EnumSoulElement[values().length];
    private static final EnumSoulElement[] CTAB_LOOKUP = new EnumSoulElement[values().length];
    private final int meta;
    private final String name;
    private final String unlocalizedName;
    private final int order;

    EnumSoulElement(int meta, String name, String unlocalizedName, int creativeTabOrder) {
        this.meta = meta;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
        order = creativeTabOrder;
    }

    public int getMetadata() {
        return meta;
    }
    public int getOrder() {
        return order;
    }

    public String getUnlocalizedName() {
        return "." + unlocalizedName;
    }

    public static EnumSoulElement byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) meta = 0;

        return META_LOOKUP[meta];
    }

    public static EnumSoulElement byOrder(int order) {
        if (order < 0 || order >= CTAB_LOOKUP.length) order = 0;

        return CTAB_LOOKUP[order];
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
            CTAB_LOOKUP[_enum.getOrder()] = _enum;
        }
    }
}