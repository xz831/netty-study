package com.xz.netty.hsp.rpc.api;

/**
 * @Package: com.xz.netty.hsp.rpc.api
 * @ClassName: MessageService
 * @Author: xz
 * @Date: 2020/7/21 13:59
 * @Version: 1.0
 */
public interface MessageService {
    /**
     * 发送消息
     * @param msg 消息体
     * @return
     */
    String send(String msg);
}
