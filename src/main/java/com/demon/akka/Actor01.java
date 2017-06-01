package com.demon.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by Demon on 2017/6/1.
 */
public class Actor01 extends UntypedActor {



    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            System.out.println("Received String message: "+ message);

            getSender().tell(message, getSelf());
        } else
            unhandled(message);
    }

    String doWork(String str) {
        try {
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result----" + str + " 。";
    }
}
