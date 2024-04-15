/**
 * File created on 14:12 14.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.world.gen;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OverworldOreGenerator implements IWorldGenerator {
    private final Block block, blockToReplace;
    private final int maxX, maxZ, minVeinSize, maxVeinSize, minVeinsPerChunk, maxVeinsPerChunk, chanceToSpawn, minY, maxY;

    public OverworldOreGenerator(Block ore, Block toReplace, int maxX, int maxZ, int minVeinSize, int maxVeinSize,
                                 int minVeinsPerChunk, int maxVeinsPerChunk, int chance, int minY, int maxY) {
        block = ore;
        blockToReplace = toReplace;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.minVeinSize = minVeinSize;
        this.maxVeinSize = maxVeinSize;
        this.minVeinsPerChunk = minVeinsPerChunk;
        this.maxVeinsPerChunk = maxVeinsPerChunk;
        this.chanceToSpawn = chance;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateOverworld(random, chunkX, chunkZ, world);
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world) {
        generateOverworld(world, random, chunkX * 16, chunkZ * 16);
    }

    public void generateOverworld(World world, Random random, int blockXPos, int blockZPos) {
        addOreSpawn(this.block, blockToReplace, world, random, blockXPos, blockZPos, maxX, maxZ, minVeinSize, maxVeinSize,
                minVeinsPerChunk, maxVeinsPerChunk, chanceToSpawn, minY, maxY);
    }

    public static void addOreSpawn(Block ore, Block toReplace, World world, Random random, int blockXPos, int blockZPos,
                                   int maxX, int maxZ,
                                   int minVS, int maxVS, int minPerC, int maxPerC, int chance, int minY, int maxY) {
        if (random.nextInt(101) < (100 - chance)) return;
        int veins = random.nextInt(maxPerC - minPerC + 1) + minPerC;
        for (int i = 0; i < veins; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);
            (new WorldGenMinable(ore.getDefaultState(), minVS + random.nextInt(maxVS - minVS + 1),
                    BlockMatcher.forBlock(Blocks.STONE))).generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }
}