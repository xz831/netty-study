package com.xz.netty.hsp.iohandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Package: com.xz.netty.hsp.iohandler
 * @ClassName: MessageToByteOutBoundHandler
 * @Author: xz
 * @Date: 2020/7/17 17:24
 * @Version: 1.0
 */
public class MessageToByteOutBoundHandler extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        out.writeLong(msg);
    }
}
