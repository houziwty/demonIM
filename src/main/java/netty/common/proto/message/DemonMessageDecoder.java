package netty.common.proto.message;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import netty.common.util.FormatUtil;

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
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) {
		if (buf.readableBytes() == 0) {
			return null;
		}
		if (buf.readableBytes() < 3) {
			this.resumeTimer(ctx);
			return null;
		}
		buf.markReaderIndex();
		int first = buf.readByte();
		int second = buf.readByte();
		int digit;
		int code = first;
		int msgLength = 0;
		int multiplier = 1;
		int lengthSize = 0;
		do {
			lengthSize++;
			digit = buf.readByte();
			code = code ^ digit;
			msgLength += (digit & 0x7f) * multiplier;
		} while ((digit & 0x80) > 0);
		if (code != second) {
			close(ctx);
			return null;
		}
		if (lengthSize > 3) {
			close(ctx);
			return null;
		}
		if (buf.readableBytes() < msgLength) {
			resumeTimer(ctx);
			buf.resetReaderIndex();
			return null;
		}
		byte[] data = new byte[2 + lengthSize + msgLength];
		buf.resetReaderIndex();
		buf.readBytes(data);
		pauseTimer();
		data = FormatUtil.obfuscation(ctx, data, 2 + lengthSize);
		MessageInputStream mis = new MessageInputStream(
				new ByteArrayInputStream(data), msgLength, lengthSize,
				isOutbound);
		DemonMessage msg = mis.readMessage();
		mis.close();
		return msg;
	}

	private void pauseTimer() {
		if (timeout != null) {
			timeout.cancel(false);
			timeout = null;
		}
	}

	private void close(ChannelHandlerContext ctx) {
		if (timeout != null) {
			timeout.cancel(false);
			timeout = null;
		}
		closed = true;
		ctx.fireExceptionCaught(BadMessageException.INSTANCE);
		ctx.close();
	}

	private void resumeTimer(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		lastReadTime = System.currentTimeMillis();
		if (timeoutMillis > 0 && (timeout == null || timeout.isCancelled())
				&& !closed) {
			timeout = ctx.executor().schedule(new ReadTimeoutTask(ctx),
					timeoutMillis, TimeUnit.MILLISECONDS);
		}

	}

	private final class ReadTimeoutTask implements Runnable {
		private final ChannelHandlerContext ctx;

		ReadTimeoutTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!ctx.channel().isOpen()) {
				return;
			}
			long currentTime = System.currentTimeMillis();
			long nextDelay = timeoutMillis - (currentTime - lastReadTime);
			if (nextDelay <= 0) {
				timeout = ctx.executor().schedule(this, timeoutMillis,
						TimeUnit.MICROSECONDS);
				try {
					readTimeOut(ctx);
				} catch (Exception ex) {
					ctx.fireExceptionCaught(ex);
				}
			} else {
				timeout = ctx.executor().schedule(this, nextDelay,
						TimeUnit.MICROSECONDS);
			}
		}

		private void readTimeOut(ChannelHandlerContext ctx) {
			// TODO Auto-generated method stub
			if (!closed) {
				if (timeout != null) {
					timeout.cancel(false);
					timeout = null;
				}
				closed = true;
				ctx.fireExceptionCaught(ReadDataTimeoutException.INSTANCE);
				ctx.close();
			}
		}

	}
}
