/**
 * File created on 20:33 19.07.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.tileentity.TileEntitySoulExtractor;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.init.CCBlocks;

import java.util.Random;

public class BlockSoulExtractor extends BlockContainer {
    private final Random random = new Random();

    private final boolean enabled;
    private static boolean isWorking;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    public BlockSoulExtractor(boolean isLit) {
        super(Material.rock);
        enabled = isLit;
        setBlockName(CraftableCreatures.getModId() + ":soul_extractor");
        if (!enabled) setCreativeTab(CCCreativeTabs.tabCraftableCreatures);
        setHarvestLevel("pickaxe", 0);
        setHardness(3.5f);
        setResistance(3.5f);
        if (enabled) setLightLevel(0.875f);
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemFromBlock(CCBlocks.soul_extractor);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        updateBlockForNeighborChange(world, x, y, z);
    }

    private void updateBlockForNeighborChange(World world, int x, int y, int z) {
        if (!world.isRemote) {
            Block left = world.getBlock(x, y, z - 1);
            Block right = world.getBlock(x, y, z + 1);
            Block backward = world.getBlock(x - 1, y, z);
            Block forward = world.getBlock(x + 1, y, z);
            byte meta = 3;

            if (right.func_149730_j() && !left.func_149730_j())
                meta = 2;

            if (left.func_149730_j() && !right.func_149730_j())
                meta = 3;

            if (forward.func_149730_j() && !backward.func_149730_j())
                meta = 4;

            if (backward.func_149730_j() && !forward.func_149730_j())
                meta = 5;

            world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        }
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        return (metadata == 0 && side == 3) ? iconFront
                : (side == 1 ? this.iconTop :
                (side == 0 ? this.iconTop : (side == metadata ? iconFront : this.blockIcon)));
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        String modId = CraftableCreatures.getModId();
        blockIcon = iconRegister.registerIcon(modId + ":se_side");
        iconFront = iconRegister.registerIcon(enabled ? modId + ":se_front_on" : modId + ":se_front_off");
        iconTop = iconRegister.registerIcon(modId + ":se_top");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        player.openGui(CraftableCreatures.INSTANCE, CraftableCreatures.GUI_SOUL_EXTRACTOR, world, x, y, z);
        return true;
    }

    public static void updateSoulExtractorBlockState(boolean working, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        isWorking = true;

        if (working) world.setBlock(x, y, z, CCBlocks.lit_soul_extractor);
        else world.setBlock(x, y, z, CCBlocks.soul_extractor);

        isWorking = false;
        world.setBlockMetadataWithNotify(x, y, z, meta, 2);

        if (tileEntity != null) {
            tileEntity.validate();
            world.setTileEntity(x, y, z, tileEntity);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySoulExtractor();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int direction = MathHelper.floor_double((placer.rotationYaw * 4f / 360f) + 0.5d) & 3;

        if (direction == 0)
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);

        if (direction == 1)
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);

        if (direction == 2)
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);

        if (direction == 3)
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);

        if (stack.hasDisplayName()) {
            ((TileEntitySoulExtractor) world.getTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        if (!isWorking) {
            TileEntitySoulExtractor teSe = (TileEntitySoulExtractor) world.getTileEntity(x, y, z);

            if (teSe != null) {
                for (int i = 0; i < teSe.getSizeInventory(); ++i) {
                    ItemStack stack = teSe.getStackInSlot(i);

                    if (stack != null) {
                        float deltaX = random.nextFloat() * 0.8f + 0.1f;
                        float deltaY = random.nextFloat() * 0.8f + 0.1f;
                        float deltaZ = random.nextFloat() * 0.8f + 0.1f;

                        while (stack.stackSize > 0) {
                            int dropAmount = random.nextInt(21) + 10;

                            if (dropAmount > stack.stackSize) dropAmount = stack.stackSize;

                            stack.stackSize -= dropAmount;
                            EntityItem dropItem = new EntityItem(world, x + deltaX, y + deltaY, z + deltaZ, new ItemStack(stack.getItem(), dropAmount, stack.getItemDamage()));

                            if (stack.hasTagCompound())
                                dropItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());

                            dropItem.motionX = random.nextGaussian() * 0.05d;
                            dropItem.motionY = random.nextGaussian() * 0.05d + 0.2f;
                            dropItem.motionZ = random.nextGaussian() * 0.05d;
                            world.spawnEntityInWorld(dropItem);
                        }
                    }
                }

                world.func_147453_f(x, y, z, block);
            }
        }

        super.breakBlock(world, x, y, z, block, metadata);
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
        //TODO: create particles
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int metadata) {
        return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
    }

    @Override
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(CCBlocks.soul_extractor);
    }
}