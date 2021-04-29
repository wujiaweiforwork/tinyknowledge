package com.jarvis.protocoltcp.server;

import com.jarvis.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyServerEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println();
        System.out.println("=====调用服务器的编码器=====");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
        System.out.println("=====调用服务器的编码器结束=====");
        System.out.println();
    }
}
