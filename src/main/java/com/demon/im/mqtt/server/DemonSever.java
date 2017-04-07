package com.demon.im.mqtt.server;

import io.netty.channel.EventLoopGroup;

public interface DemonSever {
     void run(EventLoopGroup bossGroup,EventLoopGroup workerGroup)throws Exception;
     void close()throws Exception;
}
