public class HeartBeatHandler extends ChannelInboundHandlerAdapter{
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx , Object evt) throws Exception{
		if(evt instanceof IdleStateEvent){
			//将evt向下转型为IdleStateEvent
			IdleStateEvent event = (IdleStateEvent)evt;
			String eventType = null;
			switch(event.state()){
				case READER_IDLE:
					eventType = "读空闲";
					break;
				case WRITER_IDLE:
					eventType = "写空闲";
					break;
				case ALL_IDLE:
					eventType = "读写空闲";
					break;
				default:
					break;
			}
			System.out.println(ctx.channel().remoteAddress() + "发生了超时事件-----" + eventType);
           			 System.out.println("服务器做相应处理");
		}
	}
}