package com.jarvis.protocoltcp.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //向客户端的pipeLine中添加解码器
        pipeline.addLast(new MyClientDecoder());
        //向客户端的pipeline中添加编码器
        pipeline.addLast(new MyClientEncoder());


        pipeline.addLast(new MyClientHandler());
    }
}
