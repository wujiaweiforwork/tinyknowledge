public class HeartBeatServer{
	public static void main(String[] args) throws Exception{
		NioEventLoopGroup boss = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();
		try{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boss , worker)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG , 128)
				.childOption(ChannelOption.SO_KEEPALIVE)
				.handler(new LoggingHandler(LogLevel.INFO))//添加一个日志处理器
				.childHandler(new ChannelInitializer<SocketChannel>(){
					@Override
					public void initChannel(SocketChannel ch) throws Exception{
						ChannelPipeline pipeline = ch.pipeline();
						//添加一个心跳处理器handler
						//参数为（readerIdleTime ， writerIdleTime ， AllIdleTime）
						ch.addLast(new IdleStateHandler(3,5,7,TimeUtils.SECONDS));
						//当IdleStateEvent发出后 ，会传递给pipeline的下一个handler进行处理，并调用userEventTriggered方法
						ch.addLast(new HeartBeatsHandler());
					}
				});
			ChannelFuture channelFuture = bootstrap.bind(9999).sync();
			System.out.println(channelFuture.isSuccess());
			channelFuture.channel().closeChannel().sync();
		}finally{
			bossGroup.shutdownGracefully();
            			workerGroup.shutdownGracefully();
		}
	}
}