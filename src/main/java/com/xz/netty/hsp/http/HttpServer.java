package com.xz.netty.hsp.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Package: com.xz.netty.hsp.http
 * @ClassName: HttpServer
 * @Author: xz
 * @Date: 2020/7/13 10:11
 * @Version: 1.0
 */
public class HttpServer {

    public static void main(String[] args) {
        EventLoopGroup boss = null;
        EventLoopGroup worker = null;
        try {
            boss = new NioEventLoopGroup();
            worker = new NioEventLoopGroup();
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("myHttpServerCodec",new HttpServerCodec()).addLast(new SimpleChannelInboundHandler<HttpObject>() {

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
                                    if(msg instanceof HttpRequest){
                                        if("/favicon.ico".equals(((HttpRequest) msg).uri())){
                                            return;
                                        }
                                        System.out.println(msg.getClass());
                                        System.out.println(ctx.channel().remoteAddress());
                                        ByteBuf content = Unpooled.copiedBuffer("hello http，我是服务器", CharsetUtil.UTF_8);
                                        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                                        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
                                        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
                                        ctx.writeAndFlush(response);
                                    }
                                }
                            });
                        }
                    })
                    .bind("localhost", 8888).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
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
