package org.jingtao8a.vo;

import lombok.Data;
import lombok.ToString;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.VideoInfo;
import java.util.*;

@Data
@ToString
public class VideoInfoResultVO {
    private VideoInfo videoInfo;
    private List<UserAction> userActionList;
}
