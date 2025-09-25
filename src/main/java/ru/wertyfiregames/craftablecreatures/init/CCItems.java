package ru.wertyfiregames.craftablecreatures.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.api.IMetaItemModel;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;
import ru.wertyfiregames.craftablecreatures.creativetab.CCCreativeTabs;
import ru.wertyfiregames.craftablecreatures.item.DefaultItem;
import ru.wertyfiregames.craftablecreatures.item.ItemGuideBook;
import ru.wertyfiregames.craftablecreatures.item.ItemSoulElement;
import ru.wertyfiregames.craftablecreatures.item.ItemTransmutator;

public class CCItems {
    public static final Item BLUESTONE = new DefaultItem("bluestone", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
    public static final Item BLUEPRINT = new DefaultItem("template", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
    public static final Item SPAWN_EGG_BLUEPRINT = new DefaultItem("spawnEggTemplate", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
    public static final Item BAT_WING = new DefaultItem("batWing", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
    public static final Item OCELOT_TAIL = new DefaultItem("ocelotTail", CCCreativeTabs.TAB_CRAFTABLE_CREATURES);
    public static final Item SOUL = new ItemSoulElement();
    public static final Item GUIDE_BOOK = new ItemGuideBook();
    public static final Item TRANSMUTATOR = new ItemTransmutator();

    public static void register() {
        register(BLUESTONE, "bluestone");
        register(BLUEPRINT, "blueprint");
        register(SPAWN_EGG_BLUEPRINT, "spawn_egg_template");
        register(BAT_WING, "bat_wing");
        register(OCELOT_TAIL, "ocelot_tail");
        register(GUIDE_BOOK, "guide");
        register(TRANSMUTATOR, "transmutator");
        registerExperimental();

        //Must be in the end
        register(SOUL, "soul");
    }

    public static void registerRenders() {
        registerRender(BLUESTONE);
        registerRender(BLUEPRINT);
        registerRender(SPAWN_EGG_BLUEPRINT);
        registerRender(BAT_WING);
        registerRender(OCELOT_TAIL);
        registerRender(GUIDE_BOOK);
        registerRender(TRANSMUTATOR);

        registerRender(SOUL);
    }


    private static void register(Item item, String id) {
        GameRegistry.register(item.setRegistryName(CraftableCreatures.getModId(), id));
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