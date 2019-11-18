package DemonNetty;

import java.io.*;
import java.net.Socket;
import java.util.Date;


/**
 * Created by Administrator on 2018/10/14 0014.
 * 为每个 TCP 客户端新开线程进行处理
*/
public class TimeServerHandler implements Runnable {


    private Socket socket = null;
 
    /**
     * 将每个 TCP 连接的 Socket 通过构造器传入
     *
     * @param socket
     */
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }
 
    @Override
    public void run() {
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            /**读客户端数据*/
            InputStream inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            String message = dataInputStream.readUTF();
            System.out.println("我是线程 " + Thread.currentThread().getName() + " ，收到客户端消息：" + message);
 
            /**往客户端写数据*/
            OutputStream outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(new Date().toString());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            /**操作完成，关闭流*/
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
 
            /**操作完成，关闭连接，线程自动销毁*/
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
