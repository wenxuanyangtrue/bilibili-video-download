package top.xsword.service;
/*
 *ClassName:GetVideoInfoService
 *UserName:三号男嘉宾
 *CreateTime:2021-10-30
 *description:
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.xsword.config.ConfigLoader;
import top.xsword.entity.LocalState;
import top.xsword.entity.Video;
import top.xsword.utils.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class GetVideoInfoService extends ConfigLoader {
    static String playUrlPrefix = "https://api.bilibili.com/x/player/playurl?";
    protected static final HttpGet get = new HttpGet(request_header);
    protected String videoPath;
    List<Video> videos; //视频实体类

    public GetVideoInfoService build(String url) {
        try {
            loadVideosInfo(url);
            loadPlayUrls();
            //loadVideoPath(conf.getProperty("videoTarget"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    protected String getHtmlString(String url) throws IOException, InterruptedException {
        HttpResponse<InputStream> inputStreamHttpResponse = null;
        try{
            inputStreamHttpResponse = get.setUrl(url).doGet(HttpResponse.BodyHandlers.ofInputStream());
        }catch (IOException e){
            if(e.getMessage().equals("Connection reset")){
                get.requestRebuild();
                inputStreamHttpResponse = get.setUrl(url).doGet(HttpResponse.BodyHandlers.ofInputStream());
            }else{
                e.printStackTrace();
            }
        }
        Map<String, List<String>> map = inputStreamHttpResponse.headers().map();
        List<String> list = map.get("content-encoding");
        String html;
        if(list != null && list.contains("gzip")){
            html = new String(IOUtil.getBytesByGZIP(inputStreamHttpResponse.body()));
        }else{
            html = new String(IOUtil.getBytes(inputStreamHttpResponse.body()));
        }
        return html;
    }

    /**
     * 获取视频页面上的信息
     */
    private void loadVideosInfo(String url) throws IOException, InterruptedException {
        String html = getHtmlString(url);
        Matcher matcher = Parse.textParse(html, Parse.videoData);
        videos = new ArrayList<>();
        if (matcher.find()) {
            JSONObject jsonObject = JSONObject.parseObject(matcher.group(1));
            String title = jsonObject.getString("title");
            if(title.indexOf('|') > -1){
                title = title.replace('|','-');
            }
            String bvid = jsonObject.getString("bvid");
            String avid = jsonObject.getString("aid");
            JSONArray pages = jsonObject.getJSONArray("pages");
            for (Object page : pages) {
                Video video = new Video(title, bvid, avid);
                JSONObject pageObj = (JSONObject) page;
                String cid = pageObj.getString("cid");
                String part = pageObj.getString("part");
                video.setcId(cid);
                video.setpVideoName(part);
                videos.add(video);
            }
        }
    }

    /**
     * 获取并拼接视频的所有playUrl地址, 保存到video实体
     */
    private void loadPlayUrls() {
        char tmp = '&';
        for (Video video : videos) {
            String cId = video.getcId();
            String bvId = video.getBvId();
            String avId = video.getAvId();

            String url = playUrlPrefix +
                    "bvid=" + bvId + tmp +
                    "cid=" + cId + tmp +
                    "avid=" + avId;
            video.setPlayUrl(url);
        }
    }

    protected void loadJson_Format(Video video) throws IOException, InterruptedException {
        HttpResponse<String> stringHttpResponse = get.setUrl(video.getPlayUrl()).doGet(HttpResponse.BodyHandlers.ofString());
        String json = stringHttpResponse.body();
        video.setJson(json);
        video.setFormat(Parse.getVideoFormat(video.getJson()));
    }
/*
    *//**
     * 基于playUrl发送get请求, 从返回的结果中筛选出视频的url地址保存到BanGuMi实体
     *//*
    public void loadDownloadUrl(Video video) throws IOException, InterruptedException {
        String json = video.getJson();
        video.setQuality(Parse.getQuality(json));
        video.setPlayUrl(video.getPlayUrl() + "&qn=" + video.getQuality()[0]);

        HttpResponse<String> stringHttpResponse = get.setUrl(video.getPlayUrl()).doGet(HttpResponse.BodyHandlers.ofString());
        json = stringHttpResponse.body();
        video.setDownloadUrl(Parse.getVideoUrl(json).replace("\\u0026", "&"));
        video.setLength(Parse.getLength(json));
        video.setFormat(Parse.getVideoFormat(json));
    }*/
/*

    */
/**
     * 确定下载视频名称
     *//*

    protected void loadVideoPath(String path) {
        String videoName = videos.get(0).getVideoName();
        videoPath = path + videoName + '/';//番剧保存的地址
        FileUtil.mkdirs(path, videoPath);
    }

    */
/**
     * 获取视频的格式，并设置两个文件地址
     *
     * @param video
     * @throws IOException
     * @throws InterruptedException
     *//*

    public void loadVideoPath0(Video video) throws IOException, InterruptedException {
        loadJson_Format(video);
        video.setFormat(Parse.getVideoFormat(video.getJson()));
        video.setFilePath_f(videoPath + video.getpVideoName());
        video.setFilePath_t(video.getFilePath_f() + '.' + video.getFormat());
    }
*/

    /**
     * 加载视频在本地的信息，是否已下载完毕
     */
    public void loadBanGuMiLocalState(Video video) throws IOException {
        if (Files.exists(Paths.get(video.getFilePath_f()))) {
            long size = Files.size(Paths.get(video.getFilePath_f()));
            if (size == video.getLength()) {//本集下载完毕，名称未更改
                video.setLocalState(LocalState.Finish);
            } else {
                video.setLocalState(LocalState.NotFinish);
            }
        } else if (Files.exists(Paths.get(video.getFilePath_t()))) {//本集下载完毕
            video.setLocalState(LocalState.Finish);
        } else {
            video.setLocalState(LocalState.NotFound);//本集未开始下载
        }
    }


    public void loadUrlOrConfig(Video video) throws IOException, InterruptedException {
        //loadVideoPath0(video);
        loadBanGuMiLocalState(video);
        //video.setPropertiesPath(video.getFilePath_f() + ".properties");

        /*if (video.getLocalState() == LocalState.NotFound) {
            loadDownloadUrl(video);
            videoInfo.setProperty("length", String.valueOf(video.getLength()));
            videoInfo.setProperty("format", video.getFormat());
            videoInfo.setProperty("downloadurl", video.getDownloadUrl());
            FileOutputStream fos = new FileOutputStream(video.getPropertiesPath());
            videoInfo.store(fos, "");
        } else if (video.getLocalState() == LocalState.NotFinish) {
            FileInputStream fis = new FileInputStream(video.getPropertiesPath());
            videoInfo.load(fis);
            video.setDownloadUrl(videoInfo.getProperty("downloadurl"));
            video.setLength(Long.parseLong(videoInfo.getProperty("length")));
            video.setFormat(videoInfo.getProperty("format"));
            videoInfo.clear();
            fis.close();
        }*/
    }

    public List<Video> getVideos() {
        return videos;
    }

}
