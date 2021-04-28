public class NettyServerHandler extends SimpleChannelInboundHandler<String>{
	//需要定义一个容器来管理所有的channel
	//public static List<Channel> channels = new ArrayList<>();
	//public static Map<String , Channel> channels = new HashMap<>();

	//或者定义一个ChannelGroup来管理所有的channel
	//GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		

	@Override
	public void handlerAdd(ChannelHandlerContext ctx) throws Exception{
		Channel channel = ctx.channel();
		//将该channel加入的消息发送给所有的客户端channel
		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天" + sdf.format(new java.util.Date()) + " \n");
		channelGroup.add(channel);
	}	


	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception{
		Channel channel = ctx.channel();
        		channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
       		System.out.println("channelGroup size" + channelGroup.size());
		//这里不需要将channel移除出channelGroup，会自动完成
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception{
		 Channel channel = ctx.channel();
        		System.out.println("[" +sdf.format(new Date()) + "]:" + channel.remoteAddress() + "上线了");
        		System.out.println(channelGroup.size());
	}

    	@Override
    	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        		System.out.println("[" +sdf.format(new Date()) + "]:" + ctx.channel().remoteAddress() + "离线了");
   	 }

	@Override
	public void channelRead0(ChannelHandlerContext ctx , String msg) throws Exception{
		Channel channel = ctx.channel();
		channelGroup.foreach(ch -> {
			if(channel != ch){
				ch.writeAndFlush("[" +sdf.format(new Date()) + "]" + "【客户】" + channel.remoteAddress() + "说：" + msg + "\n");
			}else{
				ch.writeAndFlush("[" +sdf.format(new Date()) + "]" + "【自己】发送了消息："+ msg + "\n");
			}
		});
	}

	@Override
    	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        		//关闭通道即可
        		ctx.close();
    	}

}