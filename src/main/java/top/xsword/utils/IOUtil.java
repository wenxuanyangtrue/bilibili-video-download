package top.xsword.utils;
/*
 *ClassName:IOUtil
 *UserName:三号男嘉宾
 *CreateTime:2021-10-03
 *description:IO流工具类
 */


import java.io.*;
import java.util.zip.GZIPInputStream;

public class IOUtil {

    public static void translate_byte(InputStream in, OutputStream out) throws IOException {
        byte[] data = new byte[2048];
        int len;
        while ((len = in.read(data)) != -1) {
            out.write(data, 0, len);
            out.flush();
        }
    }

    public static void translate_char(Reader reader, Writer writer) throws IOException {
        char[] data = new char[2048];
        int len;
        while ((len = reader.read(data)) != -1) {
            writer.write(data, 0, len);
            writer.flush();
        }
    }

    public static byte[] getBytesByGZIP(InputStream in) throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(in);
        return IOUtil.getBytes(gzipInputStream);
    }

    public static char[] getCharsByGZIP(InputStream in) throws IOException {
        GZIPInputStream gzipInputStream = new GZIPInputStream(in);
        return IOUtil.getChars(new InputStreamReader(gzipInputStream));
    }

    public static byte[] getBytes(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bis = new BufferedInputStream(in);
        translate_byte(bis, baos);
        baos.close();
        bis.close();
        return baos.toByteArray();
    }

    public static char[] getChars(Reader reader) throws IOException {
        CharArrayWriter caw = new CharArrayWriter();
        BufferedReader br = new BufferedReader(reader);
        translate_char(br, caw);
        caw.close();
        br.close();
        return caw.toCharArray();
    }

    public static void saveFile(InputStream in, String filePath, boolean append) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath, append));
        BufferedInputStream bis = new BufferedInputStream(in);
        translate_byte(bis, bos);
        bos.close();
        bis.close();
    }

    public static void saveFile(byte[] data, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        translate_byte(bais, bos);
        bos.close();
        bais.close();
    }
}
