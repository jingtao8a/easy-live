package org.jingtao8a.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessageExtendDto implements Serializable {
    private String messageContent;
    private String messageContentReply;
    //审核状态
    private Integer auditStatus;
}
