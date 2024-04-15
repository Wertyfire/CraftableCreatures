/**
 * File created on 22:41 07.01.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.guidebook.item.ItemGuideBook;
import ru.wertyfiregames.craftablecreatures.util.config.CCConfig;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class CCItems {
    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item BLUESTONE = new ItemDefault("bluestone", CCCreativeTabs.tabCraftableCreatures);
    public static final Item BLUEPRINT = new ItemDefault("blueprint", CCCreativeTabs.tabCraftableCreatures);
    public static final Item SPAWN_EGG_BLUEPRINT = new ItemDefault("spawnEggBlueprint", "spawn_egg_blueprint",
            CCCreativeTabs.tabCraftableCreatures);
    public static final Item BAT_WING = new ItemDefault("batWing", "bat_wing", CCCreativeTabs.tabCraftableCreatures);
    public static final Item SOUL_ELEMENT = new ItemSoulElement();
    public static final Item GUIDE_BOOK = new ItemGuideBook();
    public static final Item BLUESTONE_SWORD = new ItemBluestoneSword();
    public static final Item BLUESTONE_PICKAXE = new ItemBluestonePickaxe();
    public static final Item BLUESTONE_AXE = new ItemBluestoneAxe();
    public static final Item BLUESTONE_SPADE = new ItemBluestoneSpade();
    public static final Item BLUESTONE_HOE = new ItemBluestoneHoe();

    @SubscribeEvent
    public static void onRegistryItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));

        if (CCConfig.enableExperimentalContent)
            event.getRegistry().register(GUIDE_BOOK);

        event.getRegistry().register(SOUL_ELEMENT);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegistryModel(ModelRegistryEvent event) {
        registryModel(BLUESTONE);
        registryModel(BLUEPRINT);
        registryModel(SPAWN_EGG_BLUEPRINT);
        registryModel(BAT_WING);
        registryModel(BLUESTONE_SWORD);
        registryModel(BLUESTONE_PICKAXE);
        registryModel(BLUESTONE_AXE);
        registryModel(BLUESTONE_SPADE);
        registryModel(BLUESTONE_HOE);

        registryModel(GUIDE_BOOK);

        for (ItemSoulElement.SoulElementTypes type : ItemSoulElement.SoulElementTypes.values()) {
            ModelLoader.setCustomModelResourceLocation(SOUL_ELEMENT, type.ordinal(), new ModelResourceLocation(SOUL_ELEMENT.getRegistryName() + "_" + type.name(), "inventory"));
        }
    }

    private static void registryModel(Item item) {
        final ResourceLocation rgNm = item.getRegistryName();
        assert rgNm != null;
        final ModelResourceLocation mdlRL = new ModelResourceLocation(rgNm, "inventory");
        ModelBakery.registerItemVariants(item, mdlRL);
        ModelLoader.setCustomModelResourceLocation(item, 0, mdlRL);
    }
}