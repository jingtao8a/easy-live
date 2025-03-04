package org.jingtao8a.entity.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaseQuery {
    private SimplePage simplePage;
    private Long pageNo;
    private Long pageSize;
    private String orderBy;
}
