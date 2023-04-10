package top.xsword.utils;
/*
 *ClassName:Processes
 *UserName:三号男嘉宾
 *CreateTime:2021-10-09
 *description:
 */

import java.io.*;

public class Processes extends Thread {

    public volatile long length;
    public long size;

    public Processes() {

    }

    public Processes(long length, long size) {
        this.length = length;
        this.size = size;
    }

    /**
     * @param size 字节长度
     * @return 返回MB字符串
     */
    public static long getMB(long size) {
        return size / 1024 / 1024;
    }

    /**
     * @param size 字节长度
     * @return 返回KB字符串
     */
    public static long getKB(long size) {
        return size / 1024;
    }

    @Override
    public void run() {
        while (length != size) {
            synchronized (this) {
                System.out.printf("\r%dMB/%dMB", getMB(length), getMB(size));
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println();
    }

    /**
     * 保存文件显示进度
     *
     * @param in       输入流
     * @param filePath 文件地址
     * @param append   是否将流接在原有流的后面
     * @throws IOException
     */
    public void saveFile(InputStream in, String filePath, boolean append) throws IOException {
        if(!isAlive()){
            start();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filePath, append));
        BufferedInputStream bis = new BufferedInputStream(in);
        byte[] data = new byte[8192];
        int len;
        while ((len = in.read(data)) != -1) {
            out.write(data, 0, len);
            out.flush();
            synchronized (this) {
                length += len;
                notify();
            }
        }
        out.close();
        bis.close();
    }

}
