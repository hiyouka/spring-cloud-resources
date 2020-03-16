package com.hiyouka.model.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * 事件驱动
 * 在通信io操作中事件通常分为以下几种：
 *  read, decode, compute, encode, send
 * @author hiyouka
 * @since JDK 1.8
 */
public class EventDriven {

    class ClassicReactor implements Runnable{

        private final Selector selector;

        public ClassicReactor(int port) throws IOException {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
        }

        @Override
        public void run() {

        }
    }


}