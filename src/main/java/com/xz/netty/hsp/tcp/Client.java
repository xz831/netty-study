package com.xz.netty.hsp.tcp;

import com.xz.netty.hsp.iohandler.ByteToMessageInboundHandler;
import com.xz.netty.hsp.iohandler.MessageToByteOutBoundHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.xz.netty.hsp.codec
 * @ClassName: Client
 * @Author: xz
 * @Date: 2020/7/17 10:56
 * @Version: 1.0
 */
public class Client {

    public static void main(String[] args) {
        NioEventLoopGroup worker = null;
        try {
            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture channelFuture = bootstrap.group(worker).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            for (int i = 0; i < 10; i++) {
                                                TimeUnit.MILLISECONDS.sleep(5);
                                                ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString()+"  ", CharsetUtil.UTF_8));
                                            }
                                        }

                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            System.out.println("延迟"+(System.currentTimeMillis()-(Long)msg));
                                            System.out.println("收到server端消息"+(Long)msg);
                                        }

                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

                                        }

                                    });
                        }
                    }).connect("localhost", 9999).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(worker != null){
                worker.shutdownGracefully();
            }
        }
    }
}
