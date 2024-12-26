package org.jingtao8a.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCountInfoDto implements Serializable {
    private Integer fansCount;
    private Integer focusCount;
    private Integer currentCoinCount;
}
