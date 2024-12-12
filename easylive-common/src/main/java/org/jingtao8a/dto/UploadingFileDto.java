package org.jingtao8a.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)//序列化时忽略不存在的字段
@Data
public class UploadingFileDto implements Serializable {
    private static final long serialVersionUID = 8442729547933408839L;
    private String uploadId;
    private String fileName;
    private Integer chunkIndex;
    private Integer chunks;
    private Long fileSize = 0L;
    private String filePath;
}
