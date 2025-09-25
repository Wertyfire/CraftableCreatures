package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import java.util.Random;

public class BlockCCOre extends BlockDefault {
    public BlockCCOre(String name, CreativeTabs creativeTab,
                      String toolType, int level, float hardness, float resistance) {
        super(Material.ROCK, name, creativeTab, toolType, level, hardness, resistance);
    }

    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return this == CCBlocks.BLUESTONE_ORE ? CCItems.BLUESTONE : Item.getItemFromBlock(this);
    }

    public int quantityDropped(Random random) {
        return this == CCBlocks.BLUESTONE_ORE ? 1 + random.nextInt(2) : 1;
    }

    public int quantityDroppedWithBonus(int fortune, Random random) {
        if (fortune > 0 && Item.getItemFromBlock(this) != getItemDropped(getBlockState().getValidStates().iterator().next(), random, fortune)) {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0) i = 0;
            return quantityDropped(random) * (i + 1);
        } else return quantityDropped(random);
    }

    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if (getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
            int i = 0;

            if (this == CCBlocks.BLUESTONE_ORE)
                i = MathHelper.getRandomIntegerInRange(rand, 2, 5);

            return i;
        }
        return 0;
    }
}