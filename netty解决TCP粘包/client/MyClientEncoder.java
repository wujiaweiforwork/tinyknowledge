package com.jarvis.protocoltcp.client;

import com.jarvis.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MyClientEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("=======客户端的编码器被调用======");
        //写数据包的长度
        out.writeInt(msg.getLen());
        System.out.println(msg.getContent());
        //写数据包的内容
        out.writeBytes(msg.getContent());
        System.out.println("=======客户端的编码器调用完毕======");
    }
}
