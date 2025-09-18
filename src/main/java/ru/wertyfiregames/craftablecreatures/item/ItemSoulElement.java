package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.api.IMetaItemModel;
import ru.wertyfiregames.craftablecreatures.api.ISoulElement;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.init.CCItems;

import java.util.List;

public class ItemSoulElement extends DefaultItem implements IMetaItemModel, ISoulElement {
    public ItemSoulElement() {
        super("soul", CCCreativeTabs.tabCraftableCreatures);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + EnumSoulElement.byMetadata(i).getUnlocalizedName();
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> subItems) {
        for (int i = 0; i < EnumSoulElement.values().length; i++) {
            subItems.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void register() {
        ModelResourceLocation[] mrls = new ModelResourceLocation[EnumSoulElement.values().length];
        for (int i = 0; i < EnumSoulElement.values().length; i++) {
            mrls[i] = new ModelResourceLocation(CraftableCreatures.getModId() + ":" + "souls/soul_" + EnumSoulElement.values()[i].getName(), "inventory");
            CCItems.registerMetaVariant(this, i, "souls/soul_" + EnumSoulElement.values()[i].getName());
        }
        ModelBakery.registerItemVariants(this, mrls);
    }
}