package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.api.IMetaItemModel;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.*;

public class CCItems {
    public static final Item bluestone = new DefaultItem("bluestone", CCCreativeTabs.tabCraftableCreatures);
    public static final Item blueprint = new DefaultItem("template", CCCreativeTabs.tabCraftableCreatures);
    public static final Item spawn_egg_blueprint = new DefaultItem("spawnEggTemplate", CCCreativeTabs.tabCraftableCreatures);
    public static final Item bat_wing = new DefaultItem("batWing", CCCreativeTabs.tabCraftableCreatures);
    public static final Item ocelot_tail = new DefaultItem("ocelotTail", CCCreativeTabs.tabCraftableCreatures);
    public static final Item soul = new ItemSoulElement();
    public static final Item guide_book = new ItemGuideBook();
    public static final Item transmutator = new ItemTransmutator();

    public static void register() {
        GameRegistry.registerItem(bluestone, "bluestone");
        GameRegistry.registerItem(blueprint, "template");
        GameRegistry.registerItem(spawn_egg_blueprint, "spawn_egg_template");
        GameRegistry.registerItem(bat_wing, "bat_wing");
        GameRegistry.registerItem(ocelot_tail, "ocelot_tail");
        GameRegistry.registerItem(guide_book, "guide");
        GameRegistry.registerItem(transmutator, "transmutator");
        registerExperimental();

        //Must be in the end
        GameRegistry.registerItem(soul, "soul");
    }

    public static void registerRenders() {
        registerRender(bluestone);
        registerRender(blueprint);
        registerRender(spawn_egg_blueprint);
        registerRender(bat_wing);
        registerRender(ocelot_tail);
        registerRender(guide_book);
        registerRender(transmutator);

        registerRender(soul);
    }


    public static void registerRender(Item item) {
        if (!(item instanceof IMetaItemModel))
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        else {
            IMetaItemModel metaModel = (IMetaItemModel) item;
            metaModel.register();
        }
    }
    public static void registerMetaVariant(Item item, int meta, String name) {
        ModelResourceLocation mrl = new ModelResourceLocation(CraftableCreatures.getModId() + ":" + name, "inventory");
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, mrl);
    }

    private static void registerExperimental() {
        if (!CCConfig.enableExperimentalContent) return;
    }
}