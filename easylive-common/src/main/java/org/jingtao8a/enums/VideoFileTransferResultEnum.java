package org.jingtao8a.enums;

import lombok.Getter;

@Getter
public enum VideoFileTransferResultEnum {
    TRANSFER(0, "转码中"),
    SUCCESS(1, "转码成功"),
    FAIL(2, "转码失败"),
    ;
    private Integer status;
    private String desc;
    VideoFileTransferResultEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
