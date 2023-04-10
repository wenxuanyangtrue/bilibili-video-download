package top.xsword.utils;
/*
 *ClassName:TextParse
 *UserName:三号男嘉宾
 *CreateTime:2021-10-06
 *description:文本解析工具类
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parse {
    public static final String videoUrl = "\"url\":\"(https://[\\w.\\-]+(:\\d{4})?([/\\w-]+)+\\.([\\d\\w]+)\\?.+?logo=\\w+)\",";
    public static final String videoData = "\"videoData\":(\\{.*\\}),\"upData\"";
    public static final String length = "\"size\":(\\d+),";
    public static final String range = "\\d+";
    public static final String quality = "\"accept_quality\":\\[([\\d,]+)\\]";

    public static Matcher textParse(String str, String pattern) {
        Pattern compile = Pattern.compile(pattern);
        return compile.matcher(str);
    }

    /**
     * @param str     需要解析的字符
     * @param pattern 正则表达式
     * @return 筛选后的字符串
     */
    public static String getString(String str, String pattern, int group) {
        Matcher matcher = textParse(str, pattern);
        String name = null;
        if (matcher.find()) {
            name = matcher.group(group);
        }
        return name;
    }

    /**
     * 筛选json字符串中的下载链接
     *
     * @param str playurl请求返回的json字符串
     * @return 下载链接
     */
    public static String getVideoUrl(String str) {
        return getString(str, videoUrl, 1);
    }

    /**
     * 找出视频格式
     *
     * @param str playurl请求返回的json字符串
     * @return 视频格式
     */
    public static String getVideoFormat(String str) {
        return getString(str, videoUrl, 4);
    }

    /**
     * 获取视频的总长度
     *
     * @param str
     * @return
     */
    public static long getLength(String str) {
        return Long.parseLong(getString(str, length, 1));
    }

    public static String[] getQuality(String str){
        return getString(str, quality, 1).split(",");
    }

    public static long[] getRange(String str) {
        Matcher matcher = textParse(str, range);
        long[] range = new long[3];
        for (int i = 0; matcher.find(); i++) {
            range[i] = Long.parseLong(matcher.group(0));
        }
        return range;
    }

}
