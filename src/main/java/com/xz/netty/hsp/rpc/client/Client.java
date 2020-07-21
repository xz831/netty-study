package com.xz.netty.hsp.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Package: com.xz.netty.hsp.rpc.client
 * @ClassName: ClientMain
 * @Author: xz
 * @Date: 2020/7/21 14:52
 * @Version: 1.0
 */
public class Client {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    private static ClientHandler clientHandler;

    public Object getBean(final Class<?> clazz,final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{clazz},(proxy,method,args)->{
                    if(clientHandler == null){
                        clientHandler = new ClientHandler();
                    }
                    clientHandler.setPara(providerName+args[0].toString());
                    return executorService.submit(clientHandler).get();
                });
    }

    public void start() {
        clientHandler = new ClientHandler();
        NioEventLoopGroup worker = null;
        try {
            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            ChannelFuture channelFuture = bootstrap.group(worker).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(clientHandler)
                                    .addLast(new SimpleChannelInboundHandler<String>() {
                                        @Override
                                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                            System.out.println(msg);
                                        }

//                                        @Override
//                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                            ctx.writeAndFlush("rpc#hehehe");
//                                        }
                                    });
                        }
                    }).connect("localhost", 8888).sync();
            ClientMain.start = true;
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
