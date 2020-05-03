package com.xz.netty.hsp.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: NIOFileChannel2
 * @Author: xz
 * @Date: 2020/5/3 14:31
 * @Version: 1.0
 */
public class NIOFileChannel2 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("d://a.txt"));
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        channel.read(allocate);
        byte[] array = allocate.array();
        System.out.println(new String(array));
        fileInputStream.close();
    }
}
