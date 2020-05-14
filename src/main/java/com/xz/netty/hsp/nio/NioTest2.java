package com.xz.netty.hsp.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: NioTest2
 * @Author: xz
 * @Date: 2020/5/14 20:15
 * @Version: 1.0
 */
public class NioTest2 {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        SocketChannel localhost = SocketChannel.open(new InetSocketAddress("localhost", 8888));
        while (scanner.hasNext()){
            String next = scanner.next();
            char[] chars = next.toCharArray();
            CharBuffer allocate = CharBuffer.allocate(chars.length);
            allocate.put(chars);
            allocate.flip();
            ByteBuffer encode = StandardCharsets.UTF_8.encode(allocate);
            localhost.write(encode);
        }
    }



    @Test
    public void server() throws Exception{
        ServerSocketChannel open = ServerSocketChannel.open();
        open.bind(new InetSocketAddress(8888));
        SocketChannel accept = open.accept();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while(accept.read(byteBuffer) != -1){
            byteBuffer.flip();
            CharBuffer decode = StandardCharsets.UTF_8.decode(byteBuffer);
            System.out.println(new String(decode.array(),0,byteBuffer.limit()));
            byteBuffer.clear();
        }
    }
}
