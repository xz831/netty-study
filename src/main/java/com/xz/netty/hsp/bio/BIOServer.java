package com.xz.netty.hsp.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.xz.netty.hsp.bio
 * @ClassName: BIOServer
 * @Author: xz
 * @Date: 2020/5/2 12:59
 * @Version: 1.0
 */
public class BIOServer {

    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.toString(args));
        //核心5，最大5，存活时长不限，阻塞队列5，默认工厂，抛弃策略
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动");
        for (; ; ) {
            System.out.println("等待连接");
            final Socket accept = serverSocket.accept();
            System.out.println("连接到一个客户端");
            threadPoolExecutor.execute(() -> {
                byte[] bytes = new byte[1024];
                try {
                    InputStream inputStream = accept.getInputStream();
                    while (true) {
                        System.out.println("等待交互");
                        int index = inputStream.read(bytes);
                        if (index != -1) {
                            System.out.println(new String(bytes, "GBK"));
                            bytes = new byte[1024];
                        } else {
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        accept.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
