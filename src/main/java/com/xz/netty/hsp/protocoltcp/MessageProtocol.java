package com.xz.netty.hsp.protocoltcp;

/**
 * @Package: com.xz.netty.hsp.protocoltcp
 * @ClassName: MessageProtocol
 * @Author: xz
 * @Date: 2020/7/20 19:52
 * @Version: 1.0
 */
public class MessageProtocol {

    private int len;

    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
