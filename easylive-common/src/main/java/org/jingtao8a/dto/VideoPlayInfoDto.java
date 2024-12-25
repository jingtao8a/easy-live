package org.jingtao8a.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoPlayInfoDto implements Serializable {
    private String videoId;
    private String userId;
    private Integer fileIndex;
}
