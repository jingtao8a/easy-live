package org.jingtao8a.vo;

import lombok.Data;
import org.jingtao8a.entity.po.UserVideoSeries;
import org.jingtao8a.entity.po.UserVideoSeriesVideo;

import java.util.List;

@Data
public class UserVideoSeriesDetailVO {
    private UserVideoSeries videoSeries;
    private List<UserVideoSeriesVideo> seriesVideoList;
}
