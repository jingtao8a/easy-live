package org.jingtao8a.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserMessageCountDto implements Serializable {
    private Integer messageType;
    private Integer messageCount;
}
