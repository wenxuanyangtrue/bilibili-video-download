package top.xsword.config;
/*
 *ClassName:ConfigLoader
 *UserName:三号男嘉宾
 *CreateTime:2021-10-06
 *description:
 */

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static Properties request_header = new Properties();
    public static Properties conf = new Properties();
    public static final Properties videoInfo = new Properties();

    //加载配置文件
    static {
        try {
            conf.load(ConfigLoader.class.getResourceAsStream("/conf.properties"));
            String url = conf.getProperty("url");
            request_header.load(ConfigLoader.class.getResourceAsStream("/request-header.properties"));
            request_header.setProperty("referer",url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
