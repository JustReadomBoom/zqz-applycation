package com.zqz.service.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 4:34 PM 2020/7/27
 */
public class NIODemo {


    public static void main(String[] args) {
        Integer port = 8080;
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"), port));
            socketChannel.configureBlocking(false);
            Selector se = Selector.open();


        } catch (Exception e) {

        }

    }

}
