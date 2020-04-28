package com.xz.second;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Package: com.xz.second
 * @ClassName: MyServerHandler
 * @Author: xz
 * @Date: 2020/4/28 16:47
 * @Version: 1.0
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(ctx.channel().remoteAddress() + " , " + msg);
        ctx.channel().writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
