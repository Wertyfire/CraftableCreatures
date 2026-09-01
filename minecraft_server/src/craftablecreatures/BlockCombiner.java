/**
 * File created on 21:52 28.04.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.Random;

public class BlockCombiner extends BlockContainer {
    private final Random random = new Random();

    private final boolean isActive;
    private static boolean keepInventory;

    public BlockCombiner(int id, boolean lit) {
        super(id, Material.rock);
        isActive = lit;
        setStepSound(soundStoneFootstep);
        setHardness(3.5f);
        setResistance(3.5f);
        if (isActive) setLightValue(0.875f);
        setBlockName("spawnEggCombiner");
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        if (!isActive)
            itemList.add(new ItemStack((this)));
    }

    @Override
    public int idDropped(int meta, Random random, int fortune) {
        return mod_CraftableCreatures.combiner.blockID;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        setDefaultDirection(world, x, y, z);
    }

    private void setDefaultDirection(World world, int x, int y, int z) {
        int backward = world.getBlockId(x, y, z - 1);
        int forward = world.getBlockId(x, y, z + 1);
        int left = world.getBlockId(x - 1, y, z);
        int right = world.getBlockId(x + 1, y, z);
        byte meta = 3;

        if (Block.opaqueCubeLookup[forward] && !Block.opaqueCubeLookup[backward])
            meta = 3;

        if (Block.opaqueCubeLookup[backward] && !Block.opaqueCubeLookup[forward])
            meta = 2;

        if (Block.opaqueCubeLookup[right] && !Block.opaqueCubeLookup[left])
            meta = 4;

        if (Block.opaqueCubeLookup[left] && !Block.opaqueCubeLookup[right])
            meta = 5;

        world.setBlockMetadataWithNotify(x, y, z, meta);
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        TileEntityCombiner tile = (TileEntityCombiner) world.getBlockTileEntity(x, y, z);
        if (tile == null) return false;

        ModLoader.OpenGUI(player, mod_CraftableCreatures.CraftableCreaturesIDs.guiCombinerID, tile, new ContainerCombiner(player.inventory, tile));
        return true;
    }

    public static void updateCombinerBlockState(World world, int x, int y, int z, boolean working) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        keepInventory = true;

        if (working) world.setBlockWithNotify(x, y, z, mod_CraftableCreatures.combinerLit.blockID);
        else world.setBlock(x, y, z, mod_CraftableCreatures.combiner.blockID);

        keepInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta);

        if (tileEntity != null) {
            tileEntity.validate();
            world.setBlockTileEntity(x, y, z, tileEntity);
        }
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntityCombiner();
    }


    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving placer) {
        int direction = MathHelper.floor_double((placer.rotationYaw * 4f / 360f) + 0.5d) & 3;

        if (direction == 0)
            world.setBlockMetadataWithNotify(x, y, z, 2);

        if (direction == 1)
            world.setBlockMetadataWithNotify(x, y, z, 5);

        if (direction == 2)
            world.setBlockMetadataWithNotify(x, y, z, 3);

        if (direction == 3)
            world.setBlockMetadataWithNotify(x, y, z, 4);
    }

    @Override
    public void onBlockRemoval(World world, int x, int y, int z) {
        if (!keepInventory) {
            TileEntityCombiner teCombiner = (TileEntityCombiner) world.getBlockTileEntity(x, y, z);

            if (teCombiner != null) {
                for (int i = 0; i < teCombiner.getSizeInventory(); ++i) {
                    ItemStack stack = teCombiner.getStackInSlot(i);

                    if (stack != null) {
                        float deltaX = random.nextFloat() * 0.8f + 0.1f;
                        float deltaY = random.nextFloat() * 0.8f + 0.1f;
                        float deltaZ = random.nextFloat() * 0.8f + 0.1f;

                        while (stack.stackSize > 0) {
                            int dropAmount = random.nextInt(21) + 10;

                            if (dropAmount > stack.stackSize) dropAmount = stack.stackSize;

                            stack.stackSize -= dropAmount;
                            EntityItem dropItem = new EntityItem(world, x + deltaX, y + deltaY, z + deltaZ, new ItemStack(stack.getItem(), dropAmount, stack.getItemDamage()));
                            dropItem.motionX = random.nextGaussian() * 0.05d;
                            dropItem.motionY = random.nextGaussian() * 0.05d + 0.2f;
                            dropItem.motionZ = random.nextGaussian() * 0.05d;
                            world.spawnEntityInWorld(dropItem);
                        }
                    }
                }
            }
        }

        super.onBlockRemoval(world, x, y, z);
    }
}