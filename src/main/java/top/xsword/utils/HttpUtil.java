package top.xsword.utils;
/*
 *ClassName:HttpUtil
 *UserName:三号男嘉宾
 *CreateTime:2021-10-03
 *description:http请求工具类
 */

import top.xsword.config.ConfigLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class HttpUtil extends ConfigLoader {

    public static final HttpGet get = new HttpGet(request_header);


    /*public static HttpURLConnection getInstance(String url) throws IOException {
        return (HttpURLConnection) new URL(url).openConnection();
    }

    public static byte[] doGet_bytes(HttpURLConnection ins) throws IOException {
        return IOUtil.getBytes(ins.getInputStream());
    }

    public static char[] doGet_chars(HttpURLConnection ins, String charset) throws IOException {
        return IOUtil.getChars(
                charset == null ?
                        new InputStreamReader(ins.getInputStream()) :
                        new InputStreamReader(ins.getInputStream(), charset)
        );
    }

    public static void requestLoad(HttpURLConnection ins, Properties properties) {
        properties.forEach((key, value) -> {
            ins.setRequestProperty((String) key, (String) value);
        });
    }*/

}
