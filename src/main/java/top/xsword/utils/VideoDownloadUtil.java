package top.xsword.utils;

import top.xsword.entity.BanGuMi;
import top.xsword.entity.Video;

import java.io.IOException;
import java.net.http.HttpResponse;

public class VideoDownloadUtil extends HttpUtil{

    public static String videoPath;

    /**
     * 确定下载视频名称
     */
    public static void loadVideoPath(String path,Video video) {
        String videoName = video.getVideoName();
        videoPath = path + videoName + '/';//番剧保存的地址
        FileUtil.mkdirs(path, videoPath);
    }

    /**
     * 获取视频的格式，并设置两个文件地址
     *
     * @param video
     * @throws IOException
     * @throws InterruptedException
     */
    public static void loadVideoPath(Video video) throws IOException, InterruptedException {
        loadJson_Format(video);
        video.setFormat(Parse.getVideoFormat(video.getJson()));
        video.setFilePath_f(videoPath + video.getpVideoName());
        video.setFilePath_t(video.getFilePath_f() + '.' + video.getFormat());
    }

    /**
     * 确定下载番剧名称
     */
    public static void loadBanGuMiPath(Video video) throws IOException, InterruptedException {
        loadJson_Format(video);
        BanGuMi banGuMi = (BanGuMi) video;
        //确定下载的视频文件名称
        String fileName;
        if (banGuMi.getpVideoName().equals("") && !banGuMi.getLongTitle().equals("")) {
            fileName = banGuMi.getLongTitle();
        } else if (!banGuMi.getpVideoName().equals("") && banGuMi.getLongTitle().equals("")) {
            fileName = banGuMi.getpVideoName();
        } else {
            fileName = banGuMi.getpVideoName() + "-" + banGuMi.getLongTitle();
        }
        banGuMi.setFilePath_f(videoPath + fileName);
        video.setFilePath_t(video.getFilePath_f() + '.' + video.getFormat());
    }

    /**
     * 基于playUrl发送get请求, 从返回的结果中筛选出视频的url地址保存到BanGuMi实体
     */
    public static void loadDownloadUrl(Video video) throws IOException, InterruptedException {
        String json = video.getJson();
        video.setQuality(Parse.getQuality(json));
        video.setPlayUrl(video.getPlayUrl() + "&qn=" + video.getQuality()[0]);

        HttpResponse<String> stringHttpResponse = get.setUrl(video.getPlayUrl()).doGet(HttpResponse.BodyHandlers.ofString());
        json = stringHttpResponse.body();
        video.setDownloadUrl(Parse.getVideoUrl(json).replace("\\u0026", "&"));
        video.setLength(Parse.getLength(json));
        video.setFormat(Parse.getVideoFormat(json));
    }



    protected static void loadJson_Format(Video video) throws IOException, InterruptedException {
        HttpResponse<String> stringHttpResponse = get.setUrl(video.getPlayUrl()).doGet(HttpResponse.BodyHandlers.ofString());
        String json = stringHttpResponse.body();
        video.setJson(json);
        video.setFormat(Parse.getVideoFormat(video.getJson()));
    }
}
