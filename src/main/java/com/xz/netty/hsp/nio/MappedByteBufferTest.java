package com.xz.netty.hsp.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: MappedByteBuffer
 * @Author: xz
 * @Date: 2020/5/5 12:30
 * @Version: 1.0
 */
public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception{
        //可以让文件在内存（堆外内存）中修改，即操作系统不需要再拷贝一次
        RandomAccessFile rw = new RandomAccessFile("d://a.txt", "rw");
        FileChannel channel = rw.getChannel();
        //读写模式，起始位置，映射到内存的大小
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.putChar(0,'H');
        map.putChar(3,'W');
        rw.close();
    }
}
