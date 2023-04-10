package top.xsword.utils;
/*
 *ClassName:BanGuMiParse
 *UserName:三号男嘉宾
 *CreateTime:2021-10-29
 *description:
 */

public class BanGuMiParse extends Parse {
    public static final String banGuMiUrl = "\"url\":\"(https://[\\w.\\-]+(:\\d{4})?([/\\w-]+)+\\.([\\d\\w]+)\\?(\\w+=[\\w,]+=?&?)+)";
    public static final String epList = "\"epList\":(\\[.*\\]),\"epInfo";
    public static final String mainName = "\"name\":\\s\"(.+)\",";

    /**
     * 获取番剧名称
     *
     * @param str html页面
     * @return 番剧名称
     */
    public static String getBanGuMiMainName(String str) {
        return getString(str, mainName, 1);
    }

    /**
     * 筛选json字符串中的下载链接
     *
     * @param str playurl请求返回的json字符串
     * @return 下载链接
     */
    public static String getBanGuMiUrl(String str) {
        return getVideoUrl(str);
    }
}
