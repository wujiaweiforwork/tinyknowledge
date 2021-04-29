public class MyServerHandler extends SimpleChannelInboundHandler<Long>{
	@Override
   	 public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
   	 }

	@Override
    	protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        		System.out.println("从客户端" + ctx.channel().remoteAddress() + "读取到long型数据： " + msg);
    	}

    	@Override
    	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        		System.out.println("向客户端输出long型数据" + 99999L);
        		ctx.writeAndFlush(99999L);
    	}
}