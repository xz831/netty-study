package com.xz.netty.hsp.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: NioTest3
 * @Author: xz
 * @Date: 2020/5/14 22:00
 * @Version: 1.0
 */
public class NioTest3 {

    @Test
    public void client() throws Exception{
        SocketChannel localhost = SocketChannel.open(new InetSocketAddress("localhost", 7777));
        localhost.configureBlocking(false);
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put(LocalDateTime.now().toString().getBytes());
        allocate.flip();
        localhost.write(allocate);
        localhost.close();
    }

    @Test
    public void server() throws Exception{
        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(7777));
        open.configureBlocking(false);
        //选择器
        Selector selector = Selector.open();
        //注册监听事件
        open.register(selector, SelectionKey.OP_ACCEPT);
        //轮询的获取选择器上准备就绪的事件
        while (selector.select() > 0){
            //获取当前选择器中注册的选择键
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey item = iterator.next();
                //判断事件
                if(item.isAcceptable()){
                    try {
                        SocketChannel accept = open.accept();
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_READ);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if(item.isReadable()){
                    SocketChannel channel = (SocketChannel)item.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    while((channel.read(allocate) == -1)){
                        allocate.flip();
                        System.out.println(new String(allocate.array(),0,allocate.limit()));
                        allocate.clear();
                    }
                }
                iterator.remove();
            }
        }
    }
}
