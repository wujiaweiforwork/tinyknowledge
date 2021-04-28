public class NettyServer{
	private int port ;
	public NettyServer(int port){
		this.port = port;
	}
	
	public void run(){
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();

		try{
			ServerBootstrap bootstrap = new ServerBootstap();
			bootstrap.group(bossGroup , workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG , 128)
				.childOption(ChannelOption.SP_KEEPALIVE,true)
				.childHandler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel  ch) throws Exception{
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast("decoder" , new StringDecoder());
						pipeline.addLast("encoder" , new StringEncoder());
						pipeline.addLast(new NettyServerHandler());
					}	
				});


			Syetem.out.println("netty服务器准备就绪,开启绑定端及连接......");
			ChannelFuture sync = bootstrap.bind(port).sync();
			System.out.println(sync.isSuccess());
			//监听channel的关闭事件
			sync.channel().closeFuture().sync();		
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public void static main(String[] args){
		ChatServer chatServer = new ChatServer(9999);
		chatServer.run();
	}
}