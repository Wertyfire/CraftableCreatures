/**
 * File created on 21:30 16.03.2026 by Wertyfire
 */

package craftablecreatures;

import forge.ITextureProvider;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockSoulExtractor extends BlockContainer implements ITextureProvider {
    private final Random random = new Random();

    private final boolean isActive;
    private static boolean keepInventory;

    private final int
            iconTop = 4,
            iconFrontOn = 3,
            iconFrontOff = 2;

    public static TileEntitySoulExtractor clientTile = null;

    public BlockSoulExtractor(int id, boolean lit) {
        super(id, 5, Material.rock);
        isActive = lit;
        setStepSound(soundStoneFootstep);
        setHardness(3.5f);
        setResistance(3.5f);
        if (isActive) setLightValue(0.875f);
        setBlockName("soulExtractor");
    }

    @Override
    public void addCreativeItems(ArrayList itemList) {
        if (!isActive)
            itemList.add(new ItemStack((this)));
    }

    @Override
    public String getTextureFile() {
        return mod_CraftableCreatures.blockAtlas;
    }

    @Override
    public String translateBlockName() {
        return TranslateUtils.translate(getBlockName() + ".name");
    }

    @Override
    public int idDropped(int meta, Random random, int fortune) {
        return mod_CraftableCreatures.soulExtractor.blockID;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        setDefaultDirection(world, x, y, z);
    }

    private void setDefaultDirection(World world, int x, int y, int z) {
        if (!world.multiplayerWorld) {
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
    }

    @Override
    public int getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        int metadata = blockAccess.getBlockMetadata(x, y, z);
        return (metadata == 0 && side == 3) ? (isActive ? iconFrontOn : iconFrontOff)
                : (side == 1 ? iconTop :
                (side == 0 ? iconTop : (side == metadata ? (isActive ? iconFrontOn : iconFrontOff) : blockIndexInTexture)));
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (isActive) {
            int meta = world.getBlockMetadata(x, y, z);
            float xPos = x + 0.5f;
            float yPos = y + 0f + random.nextFloat() * 6f / 16f;
            float zPos = z + 0.5f;
            float verticalOffset = 0.52f;
            float horizontalOffset = random.nextFloat() * 0.6f - 0.3f;

            if (meta == 4)
                ParticleUtils.spawnParticle(mod_CraftableCreatures.soul, xPos - verticalOffset, yPos, zPos + horizontalOffset, 0d, 0d, 0d);
            else if (meta == 5)
                ParticleUtils.spawnParticle(mod_CraftableCreatures.soul, xPos + verticalOffset, yPos, zPos + horizontalOffset, 0d, 0d, 0d);
            else if (meta == 2)
                ParticleUtils.spawnParticle(mod_CraftableCreatures.soul, xPos + horizontalOffset, yPos, zPos - verticalOffset, 0d, 0d, 0d);
            else if (meta == 3)
                ParticleUtils.spawnParticle(mod_CraftableCreatures.soul, xPos + horizontalOffset, yPos, zPos + verticalOffset, 0d, 0d, 0d);
        }
    }

    @Override
    public int getBlockTextureFromSide(int side) {
        return side == 1 ? iconTop : (side == 0 ? iconTop : (side == 3 ? iconFrontOff : blockIndexInTexture));
    }

    @Override
    public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
        TileEntitySoulExtractor tile = (TileEntitySoulExtractor) world.getBlockTileEntity(x, y, z);
        if (tile == null) return false;

        if (world.multiplayerWorld) {
            clientTile = tile;
            return true;
        }

        ModLoader.OpenGUI(player, new GuiSoulExtractor(player.inventory, tile));
        return true;
    }

    public static void updateSoulExtractorBlockState(World world, int x, int y, int z, boolean working) {
        int meta = world.getBlockMetadata(x, y, z);
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        keepInventory = true;

        if (working) world.setBlockWithNotify(x, y, z, mod_CraftableCreatures.soulExtractorLit.blockID);
        else world.setBlock(x, y, z, mod_CraftableCreatures.soulExtractor.blockID);

        keepInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, meta);

        if (tileEntity != null) {
            tileEntity.validate();
            world.setBlockTileEntity(x, y, z, tileEntity);
        }
    }

    @Override
    public TileEntity getBlockEntity() {
        return new TileEntitySoulExtractor();
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
            TileEntitySoulExtractor teSe = (TileEntitySoulExtractor) world.getBlockTileEntity(x, y, z);

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

    public static class SoulExtractorItemBlock extends ItemBlockDefault {
        public SoulExtractorItemBlock(int id) {
            super(id);
        }

        @Override
        public void addInformation(ItemStack stack, List tooltip) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                tooltip.add(TranslateUtils.translate("tooltip.soulExtractor1"));
                tooltip.add(TranslateUtils.translate("tooltip.soulExtractor2"));
            }
            else {
                tooltip.add(TranslateUtils.translate("tooltip.pressLshift1"));
                tooltip.add(TranslateUtils.translate("tooltip.pressLshift2"));
            }
        }
    }
}