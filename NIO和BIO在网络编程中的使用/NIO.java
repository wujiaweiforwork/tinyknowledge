public class NIOServer{	
	public static void main(String[] args){
		//首先创建ServerSocketchannel，表示服务器的Channel（其实就相当于ServerSocket）
		var serverSocketChannel = ServerSocketChannel.open();
		//创建Selector对象，这用于对客户端的请求进行选择
		var selector = Selector.open();
		
		//绑定一个端口，通过InetSocketAddress实现
		serverSocketChannel.bind(new InetSocektAddress(9998));
		
		//将serverSocketChannel设置为非阻塞
		serverSocketChannel.configureBlocking(false);

		//将ServerSocketChannel注册到Selector中，并注明所挑选的事件类型
		serverSocketChannel.register(selector , SelectionKey.OP_ACCETP);//表示该channel用于监听accept事件

		//等待客户端连接
		while(true){
			//在建立确定的连接之前进行select，确定有channel请求才准备建立连接
			selector.select();

			//获取所有的selectionKey
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectionKeys.iterator();
			while(iterator.hasNext())
				SelectionKey key = iterator.next();
				//表示selectionKey对应的channel为连接事件
				if(key.isAcceptable()){
					SocketChannel socketChannel = serverSocketChannel.acccept();
					socketChannel.configureBlocking(false);
					//将该socketChannel注册selector上，并且给该channel关联一个byteBuffer
					socketChannel.register(selector , SelectionKey.OP_READ , ByteBuffer.allocate(1024));
				}

				if(key.isReadable()){
					SocketChannel socketChannel = (SocketChannel)key.channel();
					//获取该socketChannel的关联项,即byteBuffer
					ByteBuffer buffer = (ByteBuffer)socketChannel.attachment();
					socketChannel.read(buffer);
					System.out.println("Server获取到客户端的数据："  + new String(buffer.array()));
				}
				//必须要进行这一步，将该selectionKey从集合中移除，否则当相同的channel再次响应时将不会被执行
				iterator.remove();
			}
		}
		
	}
}




public class NIOClient{
	public static void main(String[] args){
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		 if (!socketChannel.connect(new InetSocketAddress("127.0.0.1" , 9998))){
            			while(!socketChannel.finishConnect()){
                		System.out.println("因为链接需要时间，客户端不会阻塞，可以做其他工作");
            			}
        		}
		//如果连接成功，则向服务器发送数据
		ByteBuffer buffer = ByteBuffer.wrap("客户端发来问候！".getBytes());
		socketChannel.write(buffer);
	}
}