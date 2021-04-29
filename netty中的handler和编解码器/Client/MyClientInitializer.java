package com.jarvis.inboundhandlerandoutboundhandler.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.net.Socket;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个入站的解码器，并在随后的handler中进行处理
        pipeline.addLast(new MyByteToLongDecoder());

        //加入一个出站的handler，对数据进行编码,再加入一个自定义的handler，处理业务
        pipeline.addLast(new MyLongToByteEncoder());

        pipeline.addLast(new MyClientHandler());


    }
}
