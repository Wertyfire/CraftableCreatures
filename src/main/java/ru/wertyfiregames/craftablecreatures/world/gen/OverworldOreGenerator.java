package ru.wertyfiregames.craftablecreatures.world.gen;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class OverworldOreGenerator implements IWorldGenerator
{
    private final Block block;
    private final Block blockToReplace;
    private final int maxX;
    private final int maxZ;
    private final int minVeinSize;
    private final int maxVeinSize;
    private final int minVeinsPerChunk;
    private final int maxVeinsPerChunk;
    private final int chanceToSpawn;
    private final int minY;
    private final int maxY;
    public OverworldOreGenerator(Block block, Block blockToReplace, int maxX, int maxZ, int minVeinSize, int maxVeinSize,
                                 int minVeinsPerChunk, int maxVeinsPerChunk, int chanceToSpawn, int minY, int maxY)
    {
        this.block = block;
        this.blockToReplace = blockToReplace;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minVeinSize = minVeinSize;
        this.maxVeinSize = maxVeinSize;
        this.minVeinsPerChunk = minVeinsPerChunk;
        this.maxVeinsPerChunk = maxVeinsPerChunk;
        this.chanceToSpawn = chanceToSpawn * 10000;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
                         IChunkProvider chunkProvider)
    {
        generateOverworld(random, chunkX, chunkZ, world);
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world)
    {
        generateOverworld(world, random, chunkX * 16, chunkZ * 16);
    }
    public void generateOverworld(World world, Random random, int blockXPos, int blockZPos)
    {
        addOreSpawn(this.block, blockToReplace, world, random, blockXPos, blockZPos, this.maxX, this.maxZ, this.minVeinSize, this.maxVeinSize, this.minVeinsPerChunk, this.maxVeinsPerChunk, this.chanceToSpawn, this.minY, this.maxY);
    }

    public static void addOreSpawn(Block ore, Block blockToReplace, World world, Random random,
                                   int blockXPos, int blockZPos, int maxX, int maxZ, int minVeinSize, int maxVeinSize,
                                   int minVeinsPerChunk, int maxVeinsPerChunk, int chanceToSpawn, int minY, int maxY)
    {
        if (random.nextInt(101) < (100 - chanceToSpawn)) return;
        int veins = random.nextInt(maxVeinsPerChunk - minVeinsPerChunk + 1) + minVeinsPerChunk;
        for (int i = 0; i < veins; i++)
        {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);
            (new WorldGenMinable(ore, minVeinSize + random.nextInt(maxVeinSize - minVeinSize + 1),
                    blockToReplace)).generate(world, random, posX, posY, posZ);
        }
    }
}