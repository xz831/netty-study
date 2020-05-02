package com.xz.netty.zl.second;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Package: com.xz.second
 * @ClassName: MyClient
 * @Author: xz
 * @Date: 2020/4/28 16:52
 * @Version: 1.0
 */
public class MyClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).handler(new MyClientInit());
            ChannelFuture localhost = bootstrap.connect("localhost", 8899);
            localhost.channel().closeFuture().sync();
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
