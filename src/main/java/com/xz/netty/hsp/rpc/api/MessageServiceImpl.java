package com.xz.netty.hsp.rpc.api;

import java.util.UUID;

/**
 * @Package: com.xz.netty.hsp.rpc.api
 * @ClassName: MessageServiceImpl
 * @Author: xz
 * @Date: 2020/7/21 14:00
 * @Version: 1.0
 */
public class MessageServiceImpl implements MessageService{
    @Override
    public String send(String msg) {
        System.out.println(msg);
        return UUID.randomUUID().toString();
    }
}
