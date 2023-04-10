package top.xsword.controller;
/*
 *ClassName:DownloadController
 *UserName:三号男嘉宾
 *CreateTime:2021-10-06
 *description:下载视频控制器
 */

import top.xsword.config.ConfigLoader;
import top.xsword.entity.BanGuMi;
import top.xsword.entity.LocalState;
import top.xsword.entity.Video;
import top.xsword.gui.DownloadList;
import top.xsword.service.GetBanGuMiInfoService;
import top.xsword.service.GetVideoInfoService;
import top.xsword.utils.HttpGet;
import top.xsword.utils.HttpUtil;
import top.xsword.utils.Processes;
import top.xsword.utils.VideoDownloadUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class DownloadController extends HttpUtil {
    public static Queue<Video> downloadQueue = new ArrayDeque();
    public static boolean isFinish;

    public DownloadController() {

    }

    private Video loadFirstVideo() {
        Video video = downloadQueue.peek();
        //创建下载地址
        VideoDownloadUtil.loadVideoPath(ConfigLoader.conf.getProperty("videoTarget"), video);
        try {
            if (video instanceof BanGuMi) {
                VideoDownloadUtil.loadBanGuMiPath(video);
            } else {
                VideoDownloadUtil.loadVideoPath(video);
            }
            VideoDownloadUtil.loadDownloadUrl(video);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return video;
    }

    /**
     * 调用此方法开始下载视频
     */
    public void start() {
        isFinish = false;
        while (!downloadQueue.isEmpty()) {
            synchronized (DownloadController.class) {
                Video video = loadFirstVideo();
                try {
                    download(video, new Processes());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
                downloadQueue.poll();
                if (DownloadList.listUpdate != null) {
                    try {
                        DownloadController.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        isFinish = true;
    }

    /**
     * 下载番剧的方法
     * 可以继续下载
     *
     * @param video video实体类
     */
    public void download(Video video, Processes processes) throws IOException, InterruptedException {
        System.out.println(video);
        //提示信息的名字
        String fileName;
        if (video instanceof BanGuMi) {
            BanGuMi banGuMi = (BanGuMi) video;
            fileName = banGuMi.getpVideoName() + " " + banGuMi.getLongTitle();
        } else {
            fileName = video.getpVideoName();
        }
        System.out.println("正在下载：" + fileName);
        download0(video, 0, false, processes);
        System.out.println(fileName + " 已下载完成");
        //下载完后如果文件是没后缀的，就给文件名字加上后缀
        if (Files.exists(Paths.get(video.getFilePath_f()))) {
            new File(video.getFilePath_f()).renameTo(new File(video.getFilePath_t()));//文件重命名
            //Files.delete(Paths.get(video.getPropertiesPath()));
        }
        System.out.println();
        /*long size;
        switch (video.getLocalState()) {
            case Finish -> {
                System.out.println(fileName + " 已下载完成");
                //下载完后如果文件是没后缀的，就给文件名字加上后缀
                if (Files.exists(Paths.get(video.getFilePath_f()))) {
                    new File(video.getFilePath_f()).renameTo(new File(video.getFilePath_t()));//文件重命名
                    Files.delete(Paths.get(video.getPropertiesPath()));
                }
                System.out.println();
            }
            case NotFinish -> {
                size = Files.size(Paths.get(video.getFilePath_f()));
                if (size == video.getLength()) {
                    video.setLocalState(LocalState.Finish);
                    download(video, processes);
                    break;
                }
                System.out.println("继续下载：" + fileName);
                download0(video, size, true, processes);
            }
            case NotFound -> {
                System.out.println("正在下载：" + fileName);
                download0(video, 0, false, processes);
            }
        }*/
    }

    public void download0(Video video, long size, boolean append, Processes processes) throws IOException, InterruptedException {
        get.requestRebuild();
        get.setUrl(video.getDownloadUrl());
        if (size > 0) {
            get.setHeader("range", "bytes=" + size + '-' + video.getLength());
            processes.length = size;
        }
        processes.size = video.getLength();
        HttpResponse<InputStream> response = get.doGet(HttpResponse.BodyHandlers.ofInputStream());

        /*if (video.getLocalState() == LocalState.NotFound) {
            video.setLocalState(LocalState.NotFinish);//将状态更改为未下载完成
        }*/

        new Thread(()->{
            try {
                processes.saveFile(response.body(), video.getFilePath_f(), append);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        //download(video, processes);
    }

    /**
     * flv格式转mp4格式
     *
     * @param video video实体类
     */
    /*public void flvToMp4(Video video) {
        String fileName = video.getTitleFormat() + "-" + video.getLongTitle();
        video.setMp4FilePath(videoPath + fileName + ".mp4");
        String order = "ffmpeg -loglevel quiet -i " + video.getFlvFilePath()
                + " -vcodec copy -acodec copy " + video.getMp4FilePath();

        System.out.println(order);
        try {
            Runtime runtime = Runtime.getRuntime();
            System.out.println(fileName + " flv转mp4 start");
            Process node = runtime.exec(order);
            node.waitFor();
            System.out.println(fileName + " flv转mp4 end");
            //Files.delete(Paths.get(tmpFile));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }*/


}
