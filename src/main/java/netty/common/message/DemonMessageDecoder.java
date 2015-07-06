package netty.common.message;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class DemonMessageDecoder extends ByteToMessageDecoder {
	private final long timeoutMillis;
	private volatile long lastReadTime;
	private volatile ScheduledFuture<?> timeout;

	private boolean closed;
	private boolean isOutbound = false;

	public DemonMessageDecoder(long timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	public DemonMessageDecoder(long timeoutMillis, boolean isOutbound) {
		this.timeoutMillis = timeoutMillis;
		this.isOutbound = isOutbound;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		Object decoded=decode(ctx,in);
			if(decoded!=null){
				out.add(decoded);
			}
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) {
		if(buf.readableBytes()==0){
			return null;
		}
		if(buf.readableBytes()<3){
		    this.resumeTimer(ctx);
		    return null;
		}
		buf.markReaderIndex();
		int first=buf.readByte();
		int second=buf.readByte();
		int digit;
		int code=first;
		int msgLength=0;
		int multiplier=1;
		int lengthSize=0;
		do{
		  lengthSize++;
		  digit=buf.readByte();
		  code=code^digit;
		  msgLength+=(digit & 0x7f)*multiplier;
		}while((digit & 0x80)>0);
		if(code!=second){
			close(ctx);
			return null;
		}
		if(lengthSize>3){
			close(ctx);
			return null;
		}
		if(buf.readableBytes()<msgLength){
			resumeTimer(ctx);
			buf.resetReaderIndex();
			return null;
		}
		return null;
	}

	private void close(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}

	private void resumeTimer(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		
	}
}
