package com.xz.netty.hsp.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @Package: com.xz.netty.hsp.protocoltcp
 * @ClassName: Client
 * @Author: xz
 * @Date: 2020/7/20 19:57
 * @Version: 1.0
 */
public class Client {

    public static void main(String[] args) {
        NioEventLoopGroup worker = null;
        try {
            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture future = bootstrap.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageToByteEncoder<MessageProtocol>() {
                        @Override
                        protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
                            out.writeInt(msg.getLen());
                            out.writeBytes(msg.getContent());
                        }
                    }).addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            String msg = "你好呀";
                            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
                            int len = content.length;
                            for (int i = 0; i < 10; i++) {
                                MessageProtocol messageProtocol = new MessageProtocol();
                                messageProtocol.setContent(content);
                                messageProtocol.setLen(len);
                                ctx.writeAndFlush(messageProtocol);
                            }
                        }
                    });
                }
            }).connect("localhost", 8888).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(worker != null){
                worker.shutdownGracefully();
            }
        }
    }
}
