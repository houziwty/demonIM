package com.demon.mqtt.handler;

import io.netty.channel.Channel;
import io.netty.handler.timeout.ReadTimeoutException;
import com.demon.mqtt.proto.message.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import com.demon.mqtt.listener.MQTTServerListener;

public class MqttMessageServerHandler extends SimpleChannelInboundHandler<AbstractMessage> {
	public static Logger LOGGER = LoggerFactory.getLogger(MqttMessageServerHandler.class);
	private MQTTServerListener listener;

	public MqttMessageServerHandler(MQTTServerListener listener) {
		this.listener = listener;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractMessage msg) throws Exception {
		handleMessage(msg,ctx);
	}
	
	private void handleMessage(AbstractMessage msg,ChannelHandlerContext ctx)throws Exception{
		if (msg == null) {
			return;
		}
		switch(msg.getMessageType()){
			case AbstractMessage.CONNECT:
				handleMessage((ConnectMessage) msg, ctx);
				break;
			case AbstractMessage.PUBLISH:
				handleMessage((PublishMessage) msg, ctx);
				break;
			case AbstractMessage.PUBACK:
				handleMessage((PubAckMessage) msg, ctx);
				break;
			case AbstractMessage.SUBSCRIBE:
				handleMessage((SubscribeMessage) msg, ctx);
				break;
			case AbstractMessage.UNSUBSCRIBE:
				handleMessage((UnsubscribeMessage) msg, ctx);
				break;
			case AbstractMessage.PINGREQ:
				handleMessage((PingReqMessage) msg, ctx);
				break;
			case AbstractMessage.DISCONNECT:
				handleMessage((DisconnectMessage) msg, ctx);
				break;
			default:
				break;
		}
	}


	private void handleMessage(ConnectMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.connected(msg, ctx);
	}

	private void handleMessage(PingReqMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.pingArrived(ctx);
	}

	private void handleMessage(DisconnectMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.disconnected(msg, ctx);
	}

	private void handleMessage(PublishMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.publishArrived(msg, ctx);
	}

	private void handleMessage(PubAckMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.pubAckArrived(msg, ctx);
	}

	private void handleMessage(SubscribeMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.subscribeArrived(msg, ctx);
	}

	private void handleMessage(UnsubscribeMessage msg, ChannelHandlerContext ctx) throws Exception {
		listener.unsubscribeArrived(msg, ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming=ctx.channel();
		if(cause instanceof ReadTimeoutException){
			LOGGER.error("exception", cause);
		}else if(cause instanceof BadMessageException){
			LOGGER.error("exception", cause);
		}else if(incoming.isActive()){
			LOGGER.error("exception", cause);
			listener.closed(ctx);
		}else{
			LOGGER.error("exception", cause);
		}

	}

}
