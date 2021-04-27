package com.jarvis.nio.chatsystem;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {


    private SocketChannel socketChannel ;
    private Selector selector;
    private final String HOST = "10.0.223.187";
    private final int PORT = 6667;
    private String username;


    public NIOClient() {
        try {
            selector = Selector.open();
            //获取SocketChannel
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST , PORT));
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(12,16);
            System.out.println(username + "is ok");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    //向服务器发送消息
    public void sendInfo(String info){
        info = username + "说：" + info;

        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    //读取回复的消息
    public void readInfo(){
        try {
            int readChannels = selector.select();
            if (readChannels > 0){//表示有事件发生
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int count = channel.read(byteBuffer);
                        if (count>0){
                            System.out.println(new String(byteBuffer.array()).trim());
                        }
                        iterator.remove();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void main(String[] args) throws IOException {
        final NIOClient nioClient = new NIOClient();

        //启动一个线程，每隔三秒读取服务器的数据
        new Thread(){
            public  void  run(){
                while(true){
                    nioClient.readInfo();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        //发送数据给服务器端
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String message = scanner.nextLine();
            nioClient.sendInfo(message);
        }
    }
}
