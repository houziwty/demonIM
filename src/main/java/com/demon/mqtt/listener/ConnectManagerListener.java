package com.demon.mqtt.listener;

import io.netty.channel.ChannelHandlerContext;
import com.demon.mqtt.proto.message.*;

/**
 * Created by Demon on 2017/4/7.
 */
public class ConnectManagerListener implements  MQTTServerListener {


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
