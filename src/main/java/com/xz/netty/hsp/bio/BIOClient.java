package com.xz.netty.hsp.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Package: com.xz.netty.hsp.bio
 * @ClassName: BIOClient
 * @Author: xz
 * @Date: 2020/5/2 13:15
 * @Version: 1.0
 */
public class BIOClient {

    public static final String lineSeparator = System.lineSeparator();

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6666);
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        for (; ; ) {
            String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
            printStream.println(s.replace(lineSeparator, ""));
        }
    }
}
