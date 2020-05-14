package com.xz.netty.hsp.nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: TestBio
 * @Author: xz
 * @Date: 2020/5/14 19:59
 * @Version: 1.0
 */
public class NioTest {

    @Test
    public void client() throws Exception{
        SocketChannel localhost = SocketChannel.open(new InetSocketAddress("localhost", 7777));
        FileChannel open = FileChannel.open(Paths.get("d://a.jpg"), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        while(open.read(byteBuffer) != -1){
            byteBuffer.flip();
            localhost.write(byteBuffer);
            byteBuffer.clear();
        }
        open.close();
        localhost.close();
    }

    @Test
    public void server() throws Exception{
        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(7777));
        SocketChannel accept = open.accept();
        FileChannel fileChannel = FileChannel.open(Paths.get("d://b.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        while(accept.read(byteBuffer) != -1){
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        fileChannel.close();
        accept.close();
        open.close();
    }
}
