package com.xz.netty.hsp.nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * @Package: com.xz.netty.hsp.nio
 * @ClassName: BasicBuffer
 * @Author: xz
 * @Date: 2020/5/3 11:13
 * @Version: 1.0
 */
public class BasicBuffer {

    public static void main(String[] args) {
        //创建一个buffer，可以存放5个int
        IntBuffer allocate = IntBuffer.allocate(5);
        //向buffer中存数据
        for (int i = 0; i < allocate.capacity(); i++) {
            allocate.put(i);
        }
        //将buffer转换，读写切换
        allocate.flip();
        while (allocate.hasRemaining()) {
            System.out.println(allocate.get());
        }
    }
}
