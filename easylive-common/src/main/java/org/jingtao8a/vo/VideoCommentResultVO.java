package org.jingtao8a.vo;
import lombok.Data;
import lombok.ToString;
import org.jingtao8a.entity.po.UserAction;
import org.jingtao8a.entity.po.VideoComment;

import java.util.*;

@Data
@ToString
public class VideoCommentResultVO {
    PaginationResultVO<VideoComment> commentData;
    List<UserAction> userActionList;
}
