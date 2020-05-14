package com.xz.netty.hsp.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: NioTest4
 * @Author: xz
 * @Date: 2020/5/14 22:53
 * @Version: 1.0
 */
public class NioTest4 {

    @Test
    public void client() throws Exception{
        SocketChannel localhost = SocketChannel.open(new InetSocketAddress("localhost", 7777));
        localhost.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            byteBuffer.put(next.getBytes());
            byteBuffer.flip();
            localhost.write(byteBuffer);
            byteBuffer.clear();
        }
        localhost.close();
    }

    @Test
    public void server() throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(7777));
        Selector open = Selector.open();
        serverSocketChannel.register(open, SelectionKey.OP_ACCEPT);
        while (open.select() > 0){
            Iterator<SelectionKey> iterator = open.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey next = iterator.next();
                if(next.isAcceptable()){
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    accept.register(open,SelectionKey.OP_READ);
                }else if(next.isReadable()){
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    while(channel.read(byteBuffer) != -1){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                }
                iterator.remove();
            }
        }
    }
}
