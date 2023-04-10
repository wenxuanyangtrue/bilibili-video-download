package top.xsword.entity;

import java.util.Arrays;

/*
 *ClassName:BanGuMi
 *UserName:三号男嘉宾
 *CreateTime:2021-10-06
 *description:视频实体类
 */
public class BanGuMi extends Video {
    private String longTitle;//视频小标题
    private String epId;

    public BanGuMi() {
    }

    public BanGuMi(String videoName, String pVideoName, String longTitle, String epId, String bvId, String avId, String cId) {
        super(videoName,bvId,avId);
        this.pVideoName = pVideoName;
        this.longTitle = longTitle;
        this.epId = epId;
        this.cId = cId;
    }

    public String getLongTitle() {
        return longTitle;
    }

    public void setLongTitle(String longTitle) {
        this.longTitle = longTitle;
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId;
    }

    @Override
    public String toString() {
        return "BanGuMi{" +
                "videoName='" + videoName + '\'' +
                ", pVideoName='" + pVideoName + '\'' +
                ", longTitle='" + longTitle + '\'' +
                ", format='" + format + '\'' +
                ", length=" + length +
                ", quality=" + Arrays.toString(quality) +
                ", filePath_f='" + filePath_f + '\'' +
                ", filePath_t='" + filePath_t + '\'' +
                ", localState=" + localState +
                ", playUrl='" + playUrl + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", bvId='" + bvId + '\'' +
                ", avId='" + avId + '\'' +
                ", cId='" + cId + '\'' +
                ", epId='" + epId + '\'' +
                '}';
    }
}
