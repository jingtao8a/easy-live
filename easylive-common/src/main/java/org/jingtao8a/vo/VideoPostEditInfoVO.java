package org.jingtao8a.vo;

import lombok.Data;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.po.VideoInfoPost;

import java.util.*;

@Data
public class VideoPostEditInfoVO {
    private VideoInfoPost videoInfo;
    private List<VideoInfoFilePost> videoInfoFileList;
}
