package com.xz.netty.hsp.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: NIOFileChannel3
 * @Author: xz
 * @Date: 2020/5/3 14:40
 * @Version: 1.0
 */
public class NIOFileChannel3 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("d://a.txt"));
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d://b.txt"));
        FileChannel readChannel = fileInputStream.getChannel();
        FileChannel writeChannel = fileOutputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(5);
        while (readChannel.read(allocate) != -1) {
            allocate.clear();
            writeChannel.write(allocate);
            allocate.flip();
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
