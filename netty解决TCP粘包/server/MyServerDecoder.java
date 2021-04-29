package com.jarvis.protocoltcp.server;

import com.jarvis.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

public class MyServerDecoder extends ReplayingDecoder<Void> {


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("=====服务器解码器被调用=====");
        MessageProtocol messageProtocol = new MessageProtocol();
        int len = in.readInt();
        messageProtocol.setLen(len);
        byte[] buffer = new byte[len];
        in.readBytes(buffer);
        messageProtocol.setContent(buffer);
        out.add(messageProtocol);
        System.out.println("=====服务器解码器调用完毕=====");
    }
}
