package org.jingtao8a.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class VideoInfoEsDto implements Serializable {
    /**
     * 视频ID
     */
    private String videoId;
    /**
     * 视频封面
     */
    private String videoCover;
    /**
     * 视频名称
     */
    private String videoName;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 标签
     */
    private String tags;
    /**
     * 播放数量
     */
    private Integer playCount;
    /**
     * 弹幕数量
     */
    private Integer danmuCount;
    /**
     * 收藏数量
     */
    private Integer collectCount;
}
