package org.jingtao8a.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CountInfoDto implements Serializable {
    private Integer playCount;
    private Integer likeCount;
}
