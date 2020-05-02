package com.xz.netty.zl.third;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Package: com.xz.third
 * @ClassName: MyChatClient
 * @Author: xz
 * @Date: 2020/4/29 16:32
 * @Version: 1.0
 */
public class MyChatClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).handler(new MyChatClientInit());
            ChannelFuture localhost = bootstrap.connect("localhost", 8899);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            for(;;){
                localhost.channel().writeAndFlush(bufferedReader.readLine());
            }
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
