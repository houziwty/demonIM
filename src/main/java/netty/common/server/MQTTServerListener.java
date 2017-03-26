package netty.common.server;

import io.netty.channel.ChannelHandlerContext;
import netty.common.proto.message.ConnectMessage;
import netty.common.proto.message.DisconnectMessage;
import netty.common.proto.message.PubAckMessage;
import netty.common.proto.message.PublishMessage;
import netty.common.proto.message.SubscribeMessage;
import netty.common.proto.message.UnsubscribeMessage;

public interface MQTTServerListener {
	void create(ChannelHandlerContext ctx) throws Exception;

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
