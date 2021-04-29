package com.jarvis.protocoltcp.client;

import com.jarvis.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MyClientDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("\n=====调用客户端的解码器=====");
        int length = in.readInt();
        byte[] buffer = new byte[length];
        in.readBytes(buffer);

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(length);
        messageProtocol.setContent(buffer);

        out.add(messageProtocol);
        System.out.println("====调用客户端的解码器结束====\n");
    }
}
