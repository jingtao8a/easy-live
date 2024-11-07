package org.jingtao8a.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)//序列化时忽略不存在的字段
@Data
public class TokenUserInfoDto implements Serializable {
    private static final long serialVersionUID = 9170480547933408839L;
    private String userId;
    private String nickName;
    private String avatar;
    private Long expireAt;// token 失效时间
    private String token;

    private Integer fansCount;
    private Integer currentCoinCount;
    private Integer focusCount;
}
