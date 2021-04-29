package com.jarvis.protocoltcp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //在服务器端添加解码器
        pipeline.addLast(new MyServerDecoder());
        //在服务器端添加编码器
        pipeline.addLast(new MyServerEncoder());

        pipeline.addLast(new MyServerHandler());
    }
}
