package top.xsword.entity;

/*
 *ClassName:Video
 *UserName:三号男嘉宾
 *CreateTime:2021-10-29
 *description:
 */

import java.util.Arrays;

public class Video {
    protected String videoName;//视频的名字
    protected String pVideoName;//当前集数的名字
    protected String format;//视频文件格式
    protected long length;//视频总长度
    protected String[] quality;//视频的清晰度
    protected String filePath_f;//视频文件保存地址，未重命名
    protected String filePath_t;//视频文件保存地址，已重命名
    protected LocalState localState;//本地状态，不存在，未下载完毕，已下载完毕
    protected String playUrl;//playUrl链接
    protected String downloadUrl;//video下载链接
    protected String propertiesPath;//该视频的信息文件地址
    protected  String json;//playUrl请求返回的json

    protected String bvId;
    protected String avId;
    protected String cId;

    public Video(){

    }

    public Video(String videoName, String bvId, String avId) {
        this.videoName = videoName;
        this.bvId = bvId;
        this.avId = avId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getpVideoName() {
        return pVideoName;
    }

    public void setpVideoName(String pVideoName) {
        this.pVideoName = pVideoName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String[] getQuality() {
        return quality;
    }

    public void setQuality(String[] quality) {
        this.quality = quality;
    }

    public String getFilePath_f() {
        return filePath_f;
    }

    public void setFilePath_f(String filePath_f) {
        this.filePath_f = filePath_f;
    }

    public String getFilePath_t() {
        return filePath_t;
    }

    public void setFilePath_t(String filePath_t) {
        this.filePath_t = filePath_t;
    }

    public LocalState getLocalState() {
        return localState;
    }

    public void setLocalState(LocalState localState) {
        this.localState = localState;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPropertiesPath() {
        return propertiesPath;
    }

    public void setPropertiesPath(String propertiesPath) {
        this.propertiesPath = propertiesPath;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getBvId() {
        return bvId;
    }

    public void setBvId(String bvId) {
        this.bvId = bvId;
    }

    public String getAvId() {
        return avId;
    }

    public void setAvId(String avId) {
        this.avId = avId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoName='" + videoName + '\'' +
                ", pVideoName='" + pVideoName + '\'' +
                ", format='" + format + '\'' +
                ", length=" + length +
                ", quality=" + Arrays.toString(quality) +
                ", filePath_f='" + filePath_f + '\'' +
                ", filePath_t='" + filePath_t + '\'' +
                ", localState=" + localState +
                ", playUrl='" + playUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", propertiesPath='" + propertiesPath + '\'' +
                ", json='" + json + '\'' +
                ", bvId='" + bvId + '\'' +
                ", avId='" + avId + '\'' +
                ", cId='" + cId + '\'' +
                '}';
    }
}
