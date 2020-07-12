package com.xz.netty.hsp.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * @Package: com.xz.netty.hsp.simple
 * @ClassName: NettyServer
 * @Author: xz
 * @Date: 2020/7/12 18:29
 * @Version: 1.0
 */
public class NettyServer {

    public static void main(String[] args) throws Exception{
        NioEventLoopGroup boss = null;
        NioEventLoopGroup worker = null;
        try {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = null;
                                    try {
                                        byteBuf = (ByteBuf) msg;
                                        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
                                    }finally {
                                        if(byteBuf != null){
                                            ReferenceCountUtil.release(byteBuf);
                                        }
                                    }
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                    ByteBuf byteBuf = Unpooled.copiedBuffer("server 收到",CharsetUtil.UTF_8);
                                    ctx.writeAndFlush(byteBuf);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    ctx.close();
                                }
                            });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动连接状态
                    .bind("localhost", 8888).sync();
            channelFuture.addListener(f->{
                if(f.isSuccess()){
                    System.out.println("成功");
                }else{
                    System.out.println("失败");
                }
            });
            channelFuture.channel().closeFuture().sync();
        }finally {
            if(boss != null){
                boss.shutdownGracefully();
            }
            if(worker != null){
                worker.shutdownGracefully();
            }
        }
    }
}
