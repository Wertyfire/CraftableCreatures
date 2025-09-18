/**
 * File created on 20:33 19.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCParticles;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;
import ru.wertyfiregames.craftablecreatures.util.ParticleUtils;

import java.util.List;
import java.util.Random;

public class BlockSoulExtractor extends BlockContainer {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private final boolean enabled;
    private static boolean keepInventory;

    public BlockSoulExtractor(boolean isLit) {
        super(Material.rock);
        enabled = isLit;
        setUnlocalizedName("soulExtractor");
        if (!enabled) setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
        setStepSound(soundTypePiston);
        setHarvestLevel("pickaxe", 1);
        setHardness(3.5f);
        setResistance(3.5f);
        if (enabled) setLightLevel(0.875f);
    }

    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(CCBlocks.soul_extractor);
    }

    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        setDefaultFacing(world, pos, state);
    }

    private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            Block backward = world.getBlockState(pos.north()).getBlock();
            Block forward = world.getBlockState(pos.south()).getBlock();
            Block left = world.getBlockState(pos.west()).getBlock();
            Block right = world.getBlockState(pos.east()).getBlock();
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && forward.isFullBlock() && !backward.isFullBlock())
                facing = EnumFacing.SOUTH;

            if (facing == EnumFacing.SOUTH && !forward.isFullBlock() && backward.isFullBlock())
                facing = EnumFacing.NORTH;

            if (facing == EnumFacing.WEST && left.isFullBlock() && !right.isFullBlock())
                facing = EnumFacing.EAST;

            if (facing == EnumFacing.EAST && !left.isFullBlock() && right.isFullBlock())
                facing = EnumFacing.WEST;

            world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_SOUL_EXTRACTOR, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    public static void setState(World world, BlockPos pos, boolean working) {
        IBlockState state = world.getBlockState(pos);
        TileEntity tileEntity = world.getTileEntity(pos);
        keepInventory = true;

        if (working) {
            world.setBlockState(pos, CCBlocks.lit_soul_extractor.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
            world.setBlockState(pos, CCBlocks.lit_soul_extractor.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
        }
        else {
            world.setBlockState(pos, CCBlocks.soul_extractor.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
            world.setBlockState(pos, CCBlocks.soul_extractor.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(pos, tileEntity);
        }
    }

    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySoulExtractor();
    }

    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof  TileEntitySoulExtractor)
                ((TileEntitySoulExtractor) tileEntity).setCustomInventoryName(stack.getDisplayName());
        }
    }

    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntity tileEntity = world.getTileEntity(pos);

            if (tileEntity instanceof TileEntitySoulExtractor) {
                InventoryHelper.dropInventoryItems(world, pos, (TileEntitySoulExtractor) tileEntity);
                world.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(world, pos, state);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random) {
        if (enabled) {
            EnumFacing facing = state.getValue(FACING);
            float xPos = pos.getX() + 0.5f;
            float yPos = pos.getY() + random.nextFloat() * 6f / 16f;
            float zPos = pos.getZ() + 0.5f;
            float verticalOffset = 0.52f;
            float horizontalOffset = random.nextFloat() * 0.6f - 0.3f;

            switch (facing) {
                case WEST:
                    ParticleUtils.spawnParticle(CCParticles.soul, xPos - verticalOffset, yPos, zPos + horizontalOffset, 0d, 0d, 0d);
                    break;
                case EAST:
                    ParticleUtils.spawnParticle(CCParticles.soul, xPos + verticalOffset, yPos, zPos + horizontalOffset, 0d, 0d, 0d);
                    break;
                case NORTH:
                    ParticleUtils.spawnParticle(CCParticles.soul, xPos + horizontalOffset, yPos, zPos - verticalOffset, 0d, 0d, 0d);
                    break;
                case SOUTH:
                    ParticleUtils.spawnParticle(CCParticles.soul, xPos + horizontalOffset, yPos, zPos + verticalOffset, 0d, 0d, 0d);
            }
        }
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, BlockPos pos) {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos) {
        return Item.getItemFromBlock(CCBlocks.soul_extractor);
    }

    public int getRenderType() {
        return 3;
    }

    public IBlockState getStateForEntityRender(IBlockState state) {
        return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);

        if (facing.getAxis() == EnumFacing.Axis.Y)
            facing = EnumFacing.NORTH;

        return getDefaultState().withProperty(FACING, facing);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    public static class SoulExtractorItemBlock extends ItemBlock {
        public SoulExtractorItemBlock(Block block) {
            super(block);
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean flag) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) tooltip.add(I18n.format("tooltip.soulExtractor"));
            else tooltip.add(I18n.format("tooltip.pressLshift"));
        }
    }
}