package com.xz.netty.hsp.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Package: com.xz.netty.hsp.codec
 * @ClassName: Server
 * @Author: xz
 * @Date: 2020/7/17 10:51
 * @Version: 1.0
 */
public class Server {

    public static void main(String[] args) {
        NioEventLoopGroup boss = null;
        NioEventLoopGroup worker = null;
        try {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder",new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()))
                                    .addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    StudentPOJO.Student student = (StudentPOJO.Student) msg;
                                    System.out.println(student.getId());
                                    System.out.println(student.getName());
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    ctx.channel().close();
                                }
                            });
                        }
                    }).bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(boss != null){
                boss.shutdownGracefully();
            }
            if(worker != null){
                worker.shutdownGracefully();
            }
        }
    }
}
