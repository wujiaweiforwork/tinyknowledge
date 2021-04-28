public class NettyClient{
	private final int port;
	private final String inetIp;
	
	public NettyClient(String inetIp , int port){
		this.inetIp = inetIp;
		this.port = port;
	}

	public void run() throws Exception{
		NioEventLoopGroup eventExecutor = new NioEventLoopGroup();
		
		try{
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventExecutor)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE , true)
				.handler(new Channelnitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel ch){
						ch.pipeline().addLast("encoder" , new StringEncoder());
						ch.pipeline().addLast("decoder" , new StringDecoder());
						ch.pipeline().addLast(new NettyClientHandler());					

					}
				});
			
			ChannelFuture sync = bootstrap.connect(ientIp , port).sync();
			System.out.println("--------" + channelFuture.channel().localAddress() + "--------");
			Channel channel = sync.channel();
			Scanner scanner = new Scanner(System.in);
			while(scanner.hasNextLine()){
				channel.writeAndFlush(scanner.nextLine() + "\r\n");
			}
		}finally{
			eventExecutor.shutdownGracefully();
		}
	}

	public static void main(String[] args){
		NettyClient client = new NettyClient("localhost" , 9999);
		client.run();
	}
	
}