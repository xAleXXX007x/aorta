package msifeed.mc.aorta.chat.net;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import msifeed.mc.aorta.chat.SpeechFormatter;

public class SpeechMessageHandler implements IMessageHandler<SpeechMessage, IMessage> {
    @Override
    public IMessage onMessage(SpeechMessage message, MessageContext ctx) {
        SpeechFormatter.formatSpeech(message);
        FMLClientHandler.instance().getClientPlayerEntity().addChatMessage(message.chatComponent);
        return null;
    }
}