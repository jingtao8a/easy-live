package org.jingtao8a.vo;

import lombok.Data;

@Data
public class VideoStatusCountInfoVO {
    private Long auditPassCount;
    private Long auditFailCount;
    private Long inProgress;
}
