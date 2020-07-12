package com.xz.netty.hsp.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Package: com.xz.netty.hsp.simple
 * @ClassName: Netty
 * @Author: xz
 * @Date: 2020/7/12 19:03
 * @Version: 1.0
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup worker = null;
        try {
            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture channelFuture = bootstrap.group(worker).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    ctx.channel().eventLoop().execute(() -> {
                                        try {
                                            Thread.sleep(10000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        ctx.writeAndFlush(Unpooled.copiedBuffer("延迟1", CharsetUtil.UTF_8));
                                    });
                                    ctx.channel().eventLoop().schedule(() -> {
                                        ctx.writeAndFlush(Unpooled.copiedBuffer("延迟2", CharsetUtil.UTF_8));
                                        }, 20, TimeUnit.SECONDS);
                                    ctx.writeAndFlush(Unpooled.copiedBuffer("client 连接", CharsetUtil.UTF_8));
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = null;
                                    try {
                                        byteBuf = (ByteBuf) msg;
                                        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
                                    } finally {
                                        if (byteBuf != null) {
                                            ReferenceCountUtil.release(byteBuf);
                                        }
                                    }
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    })
                    .connect("localhost", 8888).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            if (worker != null) {
                worker.shutdownGracefully();
            }
        }
    }
}
