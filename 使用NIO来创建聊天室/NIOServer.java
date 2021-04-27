package com.jarvis.nio.chatsystem;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {


    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;


    public NIOServer() {
        try {
            //获取Selector
            selector = Selector.open();
            //获取ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            //将该Channel注册到selector
            listenChannel.register(selector , SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //监听
    public void listen(){
        try {
            while(true){
                int select = selector.select(2000);
                if (select > 0){//表示有事件处理
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()){
                            //获取到客户端sockeChannel
                            SocketChannel socketChannel = listenChannel.accept();
                            //设置为非阻塞
                            socketChannel.configureBlocking(false);
                            //注册到selector中
                            socketChannel.register(selector , SelectionKey.OP_READ , ByteBuffer.allocate(1024));
                            //给出上线提示
                            System.out.println(new Date() + " " + socketChannel.getRemoteAddress() + "上线了！");
                        }
                        //表示是读事件
                        if (selectionKey.isReadable()){
                            readData(selectionKey);
                        }

                        iterator.remove();
                    }
                }else {
//                    System.out.println("等待链接....");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    //编写读取客户端消息
    public void readData(SelectionKey selectionKey){
        //定义一个SocketChannel
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count > 0){
                String msg = new String(byteBuffer.array());
                System.out.println("from 客户端" + msg);
                sendInfo(msg , channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                selectionKey.cancel();
                channel.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        } finally {
        }
    }

    //转发消息给其他客户
    public void sendInfo(String msg ,SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中");
        //遍历所有注册到selector上的selectionKey
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key:keys) {
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != selfChannel){
                SocketChannel dest = (SocketChannel) targetChannel;

                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        NIOServer nioServer = new NIOServer();
        nioServer.listen();
    }

}
