package DemonNetty;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * Created by Administrator on 2018/10/14 0014.
 * 时间服务器
 */
public class TimeServer {

	 public static void main(String[] args) {
	        tcpAccept();
	    }
	 
	    public static void tcpAccept() {
	        ServerSocket serverSocket = null;
	 
	        /**
	         * 构造线程池
	         * ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,
	         * TimeUnit unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler)
	         * 参数含义：2个核心线程数，最大线程数为5，空闲线程最大等待时间为 120秒，任务队列大小2个，线程饱和策略采用抛弃旧任务策略
	         * 应该根据实际场景进行合适调整
	         */
	        Executor executor = new ThreadPoolExecutor(2, 5,
	                120L, TimeUnit.SECONDS,
	                new ArrayBlockingQueue<Runnable>(2),
	                new ThreadPoolExecutor.DiscardOldestPolicy());
	 
	        try {
	            /**Tcp 服务器监听端口，ip 默认为本机地址*/
	            serverSocket = new ServerSocket(8080);
	            /**循环监听客户端的连接请求
	             * accept 方法会一直阻塞，直到 客户端连接成功，主线程才继续往后执行*/
	            Socket socket = null;
	            while (true) {
	                System.out.println("我是线程 " + Thread.currentThread().getName() + "，等待客户端连接..........");
	                socket = serverSocket.accept();
	                System.out.println("我是线程 " + Thread.currentThread().getName() + "，客户端连接成功..........");
	                /**线程池调度线程执行任务
	                 * 为每一个客户端连接都新开线程进行处理
	                 */
	                executor.execute(new TimeServerHandler(socket));
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            /**发生意外时，关闭服务端*/
	            if (serverSocket != null && !serverSocket.isClosed()) {
	                try {
	                    serverSocket.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }


}
