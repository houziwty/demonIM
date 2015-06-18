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

	public DemonMessageDecoder(long timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

}
