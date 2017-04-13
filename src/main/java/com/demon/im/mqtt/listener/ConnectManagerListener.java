package com.demon.im.mqtt.listener;

import io.netty.channel.ChannelHandlerContext;
import com.demon.im.mqtt.proto.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Demon on 2017/4/7.
 */
public class ConnectManagerListener implements  MQTTServerListener {
    public static Logger LOGGER = LoggerFactory.getLogger(ConnectManagerListener.class);

    @Override
    public void connected(ConnectMessage msg, ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void disconnected(DisconnectMessage msg, ChannelHandlerContext ctx) throws Exception {

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
