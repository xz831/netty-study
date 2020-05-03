package com.xz.netty.hsp.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: FileChannel
 * @Author: xz
 * @Date: 2020/5/3 14:19
 * @Version: 1.0
 */
public class NIOFileChannel {

    public static void main(String[] args) throws Exception{
        String text = "hello world";
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d://a.txt"));
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        allocate.put(text.getBytes());
        allocate.flip();
        channel.write(allocate);
        fileOutputStream.close();
    }
}
