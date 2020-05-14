package com.xz.netty.hsp.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: ScatteringAndGatheringTest
 * @Author: xz
 * @Date: 2020/5/5 12:42
 * @Version: 1.0
 */
public class ScatteringAndGatheringTest {

    public static void main(String[] args) throws Exception{
        //Scattering 写入到buffer数组
        //Gathering  读取buffer数组
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        SocketChannel accept = serverSocketChannel.accept();
        for(;;){
            int byteRead = 0;
            while(byteRead < 8){
                long read = accept.read(byteBuffers);
                byteRead += read;
                System.out.println(byteRead);
                Arrays.asList(byteBuffers).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(ByteBuffer::flip);
            long byteWrite = 0;
            while(byteWrite < 8){
                long write = accept.write(byteBuffers);
                byteWrite += write;
            }
            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);
        }
    }
}
