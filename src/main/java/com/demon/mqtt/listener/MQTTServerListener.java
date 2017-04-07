package com.demon.mqtt.listener;

import io.netty.channel.ChannelHandlerContext;
import com.demon.mqtt.proto.message.ConnectMessage;
import com.demon.mqtt.proto.message.DisconnectMessage;
import com.demon.mqtt.proto.message.PubAckMessage;
import com.demon.mqtt.proto.message.PublishMessage;
import com.demon.mqtt.proto.message.SubscribeMessage;
import com.demon.mqtt.proto.message.UnsubscribeMessage;

public interface MQTTServerListener {


	void connected(ConnectMessage msg, ChannelHandlerContext ctx)
			throws Exception;

	void disconnected(DisconnectMessage msg, ChannelHandlerContext ctx)
			throws Exception;

	void publishArrived(PublishMessage msg, ChannelHandlerContext ctx)
			throws Exception;

	void pubAckArrived(PubAckMessage msg, ChannelHandlerContext ctx)
			throws Exception;

	
	//目前没有 query 以后会有query 设计
//	void queryArrived(QueryMessage msg, ChannelHandlerContext ctx)
//			throws Exception;
//
//	void queryConArrived(QueryConMessage msg, ChannelHandlerContext ctx)
//			throws Exception;

	void subscribeArrived(SubscribeMessage msg, ChannelHandlerContext ctx)
			throws Exception;

	void unsubscribeArrived(UnsubscribeMessage msg, ChannelHandlerContext ctx)
			throws Exception;

	void pingArrived(ChannelHandlerContext ctx) throws Exception;

	void closed(ChannelHandlerContext ctx) throws Exception;

	void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception;
}
