package com.demon.im.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by Demon on 2017/6/9.
 */
public class IPUtil {

    public static String getLocalIp() throws IllegalStateException {
        try {
            Enumeration<NetworkInterface>interfaces=NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()){
                NetworkInterface current=interfaces.nextElement();
                if(!current.isUp()||current.isLoopback()||current.isVirtual()){
                    continue;
                }
                Enumeration<InetAddress>address=current.getInetAddresses();
                while(address.hasMoreElements()){
                    InetAddress addr=address.nextElement();
                    if(addr.isLoopbackAddress()){
                        continue;
                    }
                    if(addr.isSiteLocalAddress()){
                        return  addr.getHostAddress();
                    }
                }
            }

        } catch (Exception exp) {
            throw new IllegalStateException("can not get local ip address!", exp);
        }

    }
}
