package com.xz.netty.hsp.rpc.client;

import com.xz.netty.hsp.rpc.api.MessageService;

import java.util.concurrent.TimeUnit;

/**
 * @Package: com.xz.netty.hsp.rpc.client
 * @ClassName: ClientMain
 * @Author: xz
 * @Date: 2020/7/21 16:18
 * @Version: 1.0
 */
public class ClientMain {
    public static volatile boolean start = false;
    public static void main(String[] args) throws InterruptedException {
        Client client = new Client();
        new Thread(()->client.start()).start();
        while(!start){
            TimeUnit.MILLISECONDS.sleep(100);
        }
        MessageService bean = (MessageService)client.getBean(MessageService.class, "rpc#");
        for (int i = 0; i < 10; i++) {
            System.out.println(bean.send("h1h1h1h1"));
        }
    }
}
