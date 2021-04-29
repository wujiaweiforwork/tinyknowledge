package com.jarvis.protocoltcp.client;

import com.jarvis.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送五条Message
        for (int i = 0; i < 5; i++) {
            String s = new String("今天天气冷，吃火锅" + i);
            //创建协议包并发送
            MessageProtocol message = new MessageProtocol();
            message.setContent(s.getBytes(CharsetUtil.UTF_8));
            message.setLen(s.getBytes(CharsetUtil.UTF_8).length);

            ctx.writeAndFlush(message);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("收到服务器的消息长度为：" + msg.getLen());
        System.out.println("收到服务器的消息为：" + new String(msg.getContent(),CharsetUtil.UTF_8));
        System.out.println("收到服务器的消息条数为：" + ++count);

    }

}
