public class MyLongToByteEncoder extends MessageToByteEncoder<Long>{
	protected void encode(ChannelHandlerContext ctx , Long msg , ByteBuf out) throws Exception{
		out.writeLong(msg);
	}
}