public class MyByteToLongDecoder extends ByteToMessageDecoder{
	@Override
	protected void decode(ChannelHandlerContext ctx , ByteBuf in , List<Object> out) throws Exception{
		//注意编码解码出的数据类型必须和待处理的数据类型一直 否则该handler不会执行
		if(in.readableBytes() >= 8){
			out.add(in.readLong());
		}
	}
}