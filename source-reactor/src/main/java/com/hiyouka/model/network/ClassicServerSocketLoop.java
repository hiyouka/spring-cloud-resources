package com.hiyouka.model.network;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * 经典的服务端循环模型
 * 该模式为每个请求创建线程来执行
 * 无法利用多线程的优势，
 * @author hiyouka
 * @since JDK 1.8
 */
public class ClassicServerSocketLoop {

    private static final Logger logger = LoggerFactory.getLogger(ClassicServerSocketLoop.class);

    /**
     * 循环式监听
     */
    public static void main(String[] args) {
        new LoopServer(8088).run();
    }


    @Test
    public void testSend() {

        while (true){
            new Thread(() -> {
                ClientServer client = new ClientServer("127.0.0.1", 8088);
                client.send(new byte[]{1,2,3,4});
            }).start();
        }

    }


    @Test
    public void test(){
        byte[] input = new byte[5000];
    }

    /**
     * 经典的服务端循环模型（BIO）
     * 服务端监听服务端口后,循环等待消息,每当接受到一个请求开启一个线程去执行请求
     */
    static class LoopServer implements Runnable{

        private final int port;

        LoopServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (!Thread.interrupted()){
                    // 监听端口
                    new Thread(new LoopServerHandle(serverSocket.accept())).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        class LoopServerHandle implements Runnable{
            final Socket socket;
            private int MAX_INPUT = 5000;

            LoopServerHandle(Socket s) {
                this.socket = s;
            }

            @Override
            public void run() {
                try {
                    // 读写socket监听的请求/应答
                    byte[] input = new byte[MAX_INPUT];
                    int read = socket.getInputStream().read(input);
                    input = Arrays.copyOf(input,read);
                    logger.info("request message in : " + this.getClass().getName() + ", request: " + Arrays.toString(input));
                    byte[] output = process(input);
                    socket.getOutputStream().write(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private byte[] process(byte[] cmd){
                byte[] result = cmd;
                try {
                    // 模拟数据处理
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("response message in : " + this.getClass().getName() + ", response: " + Arrays.toString(result));
                return result;
            }

        }

    }

    /**
     * 消息发送
     */
    static class ClientServer{

        private final String host;

        private final int port;

        private final Socket socket;

        public ClientServer(String host, int port) {
            this.host = host;
            this.port = port;
            this.socket = init(host,port);
        }

        private Socket init(String host, int port){
            try {
                return new Socket(host,port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void send(byte[] message){
            SendHandler sendHandler = new SendHandler(socket, message);
            sendHandler.run();
            logger.info("send message in : " + this.getClass().getName() + ", message: " + Arrays.toString(message));
            byte[] response = sendHandler.getResponse();
            logger.info("response message in : " + this.getClass().getName() + ", response: " + Arrays.toString(response));
        }

    }

    static class SendHandler implements Runnable{

        private byte[] message;

        final Socket socket;

        private byte[] response;

        public SendHandler( Socket socket, byte[] message) {
            this.message = message;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                byte[] result = new byte[5000];
                socket.getOutputStream().write(message);
                int read = socket.getInputStream().read(result);
                this.response = Arrays.copyOf(result,read);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public byte[] getResponse() {
            while (true){
                if(response == null){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return response;
                }
            }
        }
    }

}