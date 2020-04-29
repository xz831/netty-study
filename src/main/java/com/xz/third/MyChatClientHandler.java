package com.xz.third;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Package: com.xz.third
 * @ClassName: MyChatClientHandler
 * @Author: xz
 * @Date: 2020/4/29 16:57
 * @Version: 1.0
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(msg);
    }
}
