package com.xz.netty.hsp.codec2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import java.util.Random;

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
                            ch.pipeline().addLast("encoder", new ProtobufEncoder())
                                    .addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    int i = new Random().nextInt(3);
                                    MyDataInfo.MyMessage message = null;
                                    if(i==0){
                                        message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                                                .setStudent(MyDataInfo.Student.newBuilder().setId(123).setName("呵呵").build()).build();
                                    }else{
                                        message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                                                .setWorker(MyDataInfo.Worker.newBuilder().setAge(321).setName("嘿嘿").build()).build();
                                    }
                                    ctx.writeAndFlush(message);
                                }
                            });
                        }
                    }).connect("localhost", 8888).sync();
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
