package com.jarvis.protocoltcp.server;

import com.jarvis.protocoltcp.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("服务器收到消息长度为：" + msg.getLen());
        System.out.println("服务器收到消息为：" + new String(msg.getContent(),CharsetUtil.UTF_8));
        System.out.println("服务器收到消息条数为：" + ++count);


        String respMsg = "收到消息啦辛苦啦";
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(respMsg.getBytes("UTF-8").length);
        messageProtocol.setContent(respMsg.getBytes("UTF-8"));

        ctx.writeAndFlush(messageProtocol);
    }
}
