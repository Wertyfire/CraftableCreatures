/**
 * File created on 17:34 19.11.2023 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.transmutator.render;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.WavefrontObject;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.render.ModelWrapperDisplayList;


import static org.lwjgl.opengl.GL11.*;

public class TransmutatorRender implements IItemRenderer
{
    private final ResourceLocation modelPath = new ResourceLocation(CraftableCreatures.getModId(), "models/transmutator.obj");
    private final ResourceLocation modelTexture = new ResourceLocation(CraftableCreatures.getModId(), "models/transmutator.png");
    private final IModelCustom model = new ModelWrapperDisplayList((WavefrontObject) AdvancedModelLoader.loadModel(modelPath));

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        Minecraft.getMinecraft().renderEngine.bindTexture(modelTexture);
        glPushMatrix();
        glTranslatef(1.3f, 2.1f, 0.6f);
        glRotatef(-255f, 0f, 1f, 0f);
        glScalef(4f, 4f, 2.8f);
        model.renderAll();
        glPopMatrix();
    }
}