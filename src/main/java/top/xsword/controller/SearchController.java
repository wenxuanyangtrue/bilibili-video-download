package top.xsword.controller;

import top.xsword.gui.DownloadStage;
import top.xsword.service.GetBanGuMiInfoService;
import top.xsword.service.GetVideoInfoService;

public class SearchController {
    private GetVideoInfoService videoInfoService;//视频信息

    public SearchController(String url) {
        String videoType = url.split("/")[3];
        switch (videoType) {
            case "bangumi" -> videoInfoService = new GetBanGuMiInfoService().build(url);
            case "video" -> videoInfoService = new GetVideoInfoService().build(url);
        }
        new DownloadStage(videoInfoService).build();
    }
}
