package com.xz.netty.hsp.protocoltcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @Package: com.xz.netty.hsp.protocoltcp
 * @ClassName: Server
 * @Author: xz
 * @Date: 2020/7/20 19:52
 * @Version: 1.0
 */
public class Server {

    static int count = 0;

    public static void main(String[] args) {
        NioEventLoopGroup boss = null;
        NioEventLoopGroup worker = null;
        try {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture future = serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ByteToMessageDecoder() {
                                @Override
                                protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
                                    int i = in.readInt();
                                    byte[] content = new byte[i];
                                    in.readBytes(content);
                                    MessageProtocol messageProtocol = new MessageProtocol();
                                    messageProtocol.setLen(i);
                                    messageProtocol.setContent(content);
                                    out.add(messageProtocol);
                                }
                            }).addLast(new SimpleChannelInboundHandler<MessageProtocol>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
                                    System.out.println(++count);
                                    System.out.println(new String(msg.getContent(), CharsetUtil.UTF_8));
                                }
                            });
                        }
                    }).bind("localhost", 8888).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(boss!=null){
                boss.shutdownGracefully();
            }
            if(worker!=null){
                worker.shutdownGracefully();
            }
        }
    }
}
