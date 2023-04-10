package top.xsword.service;
/*
 *ClassName:GetVideoUrlService
 *UserName:三号男嘉宾
 *CreateTime:2021-10-06
 *description:获取视频url服务层
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.xsword.entity.BanGuMi;
import top.xsword.entity.Video;
import top.xsword.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;


public class GetBanGuMiInfoService extends GetVideoInfoService {
    static String playUrlPrefix = "https://api.bilibili.com/pgc/player/web/playurl?";

    public GetBanGuMiInfoService() {

    }

    public GetBanGuMiInfoService build(String url) {
        try {
            loadBanGuMisInfo(url);
            loadPlayUrls();
            //loadVideoPath(conf.getProperty("videoTarget"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 获取番剧页面上的信息
     */
    private void loadBanGuMisInfo(String url) throws IOException, InterruptedException {
        String html = getHtmlString(url);
        Matcher matcher = BanGuMiParse.textParse(html, BanGuMiParse.epList);
        String banGuMiMainName = BanGuMiParse.getBanGuMiMainName(html);
        videos = new ArrayList<>();
        if (matcher.find()) {
            JSONArray jsonArray = JSONArray.parseArray(matcher.group(1));
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                BanGuMi banGuMi = new BanGuMi(
                        banGuMiMainName,
                        jsonObject.getString("titleFormat"), jsonObject.getString("longTitle"),
                        jsonObject.getString("id"), jsonObject.getString("bvid"),
                        jsonObject.getString("aid"), jsonObject.getString("cid")
                );
                videos.add(banGuMi);
            }
        }
    }

    /**
     * 获取并拼接番剧的所有playUrl地址, 保存到video实体
     */
    private void loadPlayUrls() {
        char tmp = '&';
        for (Video video : videos) {
            BanGuMi banGuMi = (BanGuMi) video;
            String avId = banGuMi.getAvId();
            String cId = banGuMi.getcId();
            String bvId = banGuMi.getBvId();
            String epId = banGuMi.getEpId();

            String url = playUrlPrefix +
                    "avid=" + avId + tmp +
                    "bvid=" + bvId + tmp +
                    "cid=" + cId + tmp +
                    "ep_id=" + epId;

            banGuMi.setPlayUrl(url);
        }
    }

    /**
     * 确定下载番剧名称
     */
    public void loadVideoPath0(Video video) throws IOException, InterruptedException {
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

}
