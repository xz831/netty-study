package com.xz.netty.hsp.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: NIOFileChannel4
 * @Author: xz
 * @Date: 2020/5/5 11:57
 * @Version: 1.0
 */
public class NIOFileChannel4 {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("d://a.jpg");
        FileChannel read = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("d://b.jpg");
        FileChannel write = fileOutputStream.getChannel();
        write.transferFrom(read,0,read.size());
        fileInputStream.close();
        fileOutputStream.close();
    }
}
