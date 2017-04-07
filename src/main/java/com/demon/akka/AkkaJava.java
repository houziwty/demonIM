package com.demon.akka;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class AkkaJava {

	public static class Greet implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8939745048564362444L;

	}

	/**
	 * 谁发起的问候
	 */
	public static class WhoToGreet implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4348565950113501799L;

		public final String who;

		public WhoToGreet(String who) {
			this.who = who;
		}

	}

	/**
	 * 问候
	 */
	public static class Greeting implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8465872810032813852L;

		// 问候内容
		private String message;

		public Greeting(String message) {
			this.message = message;
		}

	}

	/**
	 * 接待员
	 */
	public static class Greeter extends UntypedActor {
		String greeting = "";

		@Override
		public void onReceive(Object message) throws Exception {
			if (message instanceof WhoToGreet) {
				greeting = "hello" + ((WhoToGreet) message).who;
			} else if (message instanceof Greet) {
				// 发送当前问候返回给发送者
				getSender().tell(new Greeting(greeting), getSelf());
			} else {
				unhandled(message);
			}
		}

	}

	public static void main(String[] args) {
		// 创建系统
		final ActorSystem system = ActorSystem.create("akka");
		// 创建一个接待者
		final ActorRef greeter = system.actorOf(Props.create(Greeter.class),
				"greeter");
		// 创建一个收件箱
		final Inbox inbox = Inbox.create(system);
		// 告诉“接待者”改变“问候”的消息
		greeter.tell(new WhoToGreet("顾客"), greeter);
		// 问“接待员最新的“问候语”回复应该去的“actor-in-a-box”
		inbox.send(greeter, new Greet());
		// 等待5秒，与“问候”消息的答复
		Greeting greeting1 = (Greeting) inbox.receive(Duration.create(5,
				TimeUnit.SECONDS));

		System.out.println("问候:" + greeting1.message);
		// 改变问候，问一遍
		greeter.tell(new WhoToGreet("接待者"), ActorRef.noSender());
		inbox.send(greeter, new Greet());
		Greeting greeting2 = (Greeting) inbox.receive(Duration.create(5,
				TimeUnit.SECONDS));
		System.out.println("问候收到的问候语: " + greeting2.message);
		// 打印出问候信息
		ActorRef greetPrinter = system
				.actorOf(Props.create(GreetPrinter.class));
		// 立刻执行（每秒一次）
		system.scheduler().schedule(Duration.Zero(),
				Duration.create(6, TimeUnit.SECONDS), greeter, new Greet(),
				system.dispatcher(), greetPrinter);
	}

	/**
	 * 问候打印机
	 */
	public static class GreetPrinter extends UntypedActor {
		@Override
		public void onReceive(Object message) throws Exception {
			if (message instanceof Greeting) {
				System.out.println("问候打印机:" + ((Greeting) message).message);
			} else {
				System.out.println("Other:" + message);
			}
		}
	}
}
