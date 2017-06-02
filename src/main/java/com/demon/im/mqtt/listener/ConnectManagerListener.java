package com.demon.im.mqtt.listener;

import com.demon.im.mqtt.common.ContextAttributeKey;
import com.demon.service.UserService;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import com.demon.im.mqtt.proto.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Demon on 2017/4/7.
 */
public class ConnectManagerListener implements MQTTServerListener {


    UserService userService;
    public static Logger LOGGER = LoggerFactory.getLogger(ConnectManagerListener.class);

    @Override
    public void connected(ConnectMessage msg, ChannelHandlerContext ctx) throws Exception {
        ConnAckMessage ackMessage = new ConnAckMessage();
        msg.getClientID();
        msg.getPassword();
        msg.getUsername();
        ctx.writeAndFlush(ackMessage).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 注册管道
                // 添加路由信息
            }
        });
    }

    @Override
    public void disconnected(DisconnectMessage msg, ChannelHandlerContext ctx) throws Exception {
        String identifier = ctx.channel().attr(ContextAttributeKey.SESSION_KEY).get();
    }

    @Override
    public void publishArrived(PublishMessage msg, ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void pubAckArrived(PubAckMessage msg, ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void subscribeArrived(SubscribeMessage msg, ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void unsubscribeArrived(UnsubscribeMessage msg, ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void pingArrived(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void closed(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }
}
