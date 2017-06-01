package com.demon.akka;

import akka.actor.UntypedActor;

/**
 * Created by Demon on 2017/6/1.
 */
public class Actor02  extends UntypedActor {
    @Override
    public void onReceive(Object arg0) throws Exception {
        if(arg0 instanceof String)
            System.err.println("2-------------->"+arg0);
    }
}
