package com.demon.im.mqtt.common;

import io.netty.util.AttributeKey;

/**
 * Created by Demon on 2017/4/18.
 */
public class ContextAttributeKey {
    public static final AttributeKey<String>IDENTIFIER=AttributeKey.newInstance("STATE.identifier");

    public static final AttributeKey<String> USERID = AttributeKey.newInstance("STATE.userId");

    public static final AttributeKey<String> DEVICEID = AttributeKey.newInstance("STATE.deviceId");

    public static final AttributeKey<String> DEVICETYPE = AttributeKey.newInstance("STATE.deviceType");

    public static final AttributeKey<String> TOKEND = AttributeKey.newInstance("STATE.token");
}
