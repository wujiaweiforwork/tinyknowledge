package com.jarvis.inboundhandlerandoutboundhandler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("调用MyByteToLongDecoder中的decode方法");
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
