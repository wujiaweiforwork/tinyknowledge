public class MyServerInitializer extends ChannelInitializer<SocketChannel>{
	@Override
	protected void initChannel(SocketChannel ch) throws Exception{
		ChannelPipeline pipeline = ch.pipeline();
		
		//添加入站的handler作为解码handler	
		pipeline.addLast(new MyByteToLongDecoder());
		//添加出站的handler作为编码handler
		pipeline.addLast(new MyLongToByteEncoder());
		pipeline.addLast(new MyServerHandler());
	}
}