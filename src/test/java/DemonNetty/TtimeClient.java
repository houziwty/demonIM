package DemonNetty;
import java.io.*;
import java.net.Socket;
/**
 * Created by Administrator on 2018/10/14 0014.
 * 时间 客户端
 */
public class TtimeClient {
	public static void main(String[] args) {
        /**
         * 5个线程模拟5个客户端
         */
        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    tcpSendMessage();
                }
            }.start();
        }
    }
 
    /**
     * Tcp 客户端连接服务器并发送消息
     */
    public static void tcpSendMessage() {
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        try {
            /**
             * Socket(String host, int port)：
             *      host）被连接的服务器 IP 地址
             *      port）被连接的服务器监听的端口
             * Socket(InetAddress address, int port)
             *      address）用于设置 ip 地址的对象
             * 此时如果 TCP 服务器未开放，或者其它原因导致连接失败，则抛出异常：
             * java.net.ConnectException: Connection refused: connect
             */
            socket = new Socket("127.0.0.1", 8080);
            System.out.println("连接成功.........." + Thread.currentThread().getName());
 
            /**往服务端写数据*/
            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("我是长城" + Thread.currentThread().getName());
            dataOutputStream.flush();
 
            /**读服务端数据*/
            InputStream inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            String message = dataInputStream.readUTF();
            System.out.println("收到服务器消息：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /**关闭流，释放资源*/
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
            /** 操作完毕关闭 socket*/
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
