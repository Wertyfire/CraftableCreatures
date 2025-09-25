/**
 * File created on 20:33 19.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.init.CCItems;
import ru.wertyfiregames.craftablecreatures.init.CCParticles;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockSoulExtractor extends BlockContainer {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private final boolean enabled;
    private static boolean keepInventory;

    public BlockSoulExtractor(boolean isLit) {
        super(Material.ROCK);
        enabled = isLit;
        setUnlocalizedName("soulExtractor");
        if (!enabled) setCreativeTab(CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 1);
        setHardness(3.5f);
        setResistance(3.5f);
        if (enabled) setLightLevel(0.875f);
        setDefaultState(getBlockState().getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(CCBlocks.SOUL_EXTRACTOR);
    }

    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        setDefaultFacing(world, pos, state);
    }

    private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            IBlockState backward = world.getBlockState(pos.north());
            IBlockState forward = world.getBlockState(pos.south());
            IBlockState left = world.getBlockState(pos.west());
            IBlockState right = world.getBlockState(pos.east());
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

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing facing, float hitX, float hitY, float hitZ) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_SOUL_EXTRACTOR, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    public static void setState(World world, BlockPos pos, boolean working) {
        IBlockState state = world.getBlockState(pos);
        TileEntity tileEntity = world.getTileEntity(pos);
        keepInventory = true;

        if (working) {
            world.setBlockState(pos, CCBlocks.LIT_SOUL_EXTRACTOR.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
            world.setBlockState(pos, CCBlocks.LIT_SOUL_EXTRACTOR.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
        }
        else {
            world.setBlockState(pos, CCBlocks.SOUL_EXTRACTOR.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
            world.setBlockState(pos, CCBlocks.SOUL_EXTRACTOR.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
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
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (enabled) {
            EnumFacing facing = state.getValue(FACING);
            float xPos = pos.getX() + 0.5f;
            float yPos = pos.getY() + random.nextFloat() * 6f / 16f;
            float zPos = pos.getZ() + 0.5f;
            float verticalOffset = 0.52f;
            float horizontalOffset = random.nextFloat() * 0.6f - 0.3f;

            switch (facing) {
                case WEST:
                    Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(CCParticles.soul, xPos - verticalOffset, yPos, zPos + horizontalOffset, 0d, 0d, 0d);
                    break;
                case EAST:
                    Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(CCParticles.soul, xPos + verticalOffset, yPos, zPos + horizontalOffset, 0d, 0d, 0d);
                    break;
                case NORTH:
                    Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(CCParticles.soul, xPos + horizontalOffset, yPos, zPos - verticalOffset, 0d, 0d, 0d);
                    break;
                case SOUTH:
                    Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(CCParticles.soul, xPos + horizontalOffset, yPos, zPos + verticalOffset, 0d, 0d, 0d);
            }
        }
    }

    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        return Container.calcRedstone(world.getTileEntity(pos));
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(CCBlocks.SOUL_EXTRACTOR);
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
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

    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    public static class SoulExtractorItemBlock extends ItemBlock {
        public SoulExtractorItemBlock(Block block) {
            super(block);
        }

        @Override
        public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean flag) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                tooltip.add(I18n.format("tooltip.soulExtractor1"));
                tooltip.add(I18n.format("tooltip.soulExtractor2"));
            }
            else {
                tooltip.add(I18n.format("tooltip.pressLshift1"));
                tooltip.add(I18n.format("tooltip.pressLshift2"));
            }
        }
    }
}