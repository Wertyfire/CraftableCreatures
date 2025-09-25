/**
 * File created on 15:16 29.08.2025 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.network.client;

import io.netty.buffer.ByteBuf;
//import morph.api.Api;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.HashMap;
import java.util.Map;

public class MorphClientPacket implements IMessage {
    private static final Map<EntityPlayerMP, Long> lastMorphs = new HashMap<>();
    public MorphClientPacket() {}

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class Handler implements IMessageHandler<MorphClientPacket, IMessage> {
        @Override
        public IMessage onMessage(MorphClientPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;

//            if (!Loader.isModLoaded("Morph")) {
                player.addChatMessage(new TextComponentTranslation("craftableCreatures.chat.morphRequired"));
                player.closeScreen();
                return null;
//            } else {
//                ContainerTransmutator container = (ContainerTransmutator) player.openContainer;
//                ItemStack soul = container.getInv().getStackInSlot(9);
//                if (soul == null) return null;
//                lastMorphs.putIfAbsent(player, 0L);
//                if (soul.isItemEqual(new ItemStack(CCItems.soul_element, 1, 0))) {
//                    if (!Api.hasMorph(player.getDisplayName(), false)) return null;
//                    if (System.currentTimeMillis() - lastMorphs.get(player) < 4000) return null;
//                    Api.forceDemorph(player);
//                } else {
//                    EntityLivingBase entity = CraftableCreaturesRegistry.getMorphEntity(soul);
//                    if (entity == null || Api.getMorphEntity(player.getDisplayName(), false) == entity) return null;
//                    if (System.currentTimeMillis() - lastMorphs.get(player) < 4000) return null;
//                    Api.forceMorph(player, entity);
//                }
//                lastMorphs.put(player, System.currentTimeMillis());
//                container.getInv().decrStackSize(9, 1);
//            }
//
//            return null;
        }
    }
}