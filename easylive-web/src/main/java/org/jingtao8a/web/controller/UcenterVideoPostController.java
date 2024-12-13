package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.entity.po.VideoInfoPost;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.jingtao8a.service.VideoInfoFileService;
import org.jingtao8a.service.VideoInfoPostService;
import org.jingtao8a.service.VideoInfoService;
import org.jingtao8a.utils.JsonUtils;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@RestController
@RequestMapping("/ucenter")
@Validated
@Slf4j
public class UcenterVideoPostController extends ABaseController{
    @Resource
    private VideoInfoPostService videoInfoPostService;
    @Resource
    private VideoInfoFilePostService videoInfoFilePostService;
    @Resource
    private VideoInfoService videoInfoService;
    @Resource
    private VideoInfoFileService videoInfoFileService;

    @RequestMapping("/postVideo")
    public ResponseVO postVideo(String videoId,
                                @NotEmpty String videoCover,
                                @NotEmpty @Size(max=100) String videoName,
                                @NotNull Integer pCategoryId,
                                Integer categoryId,
                                @NotNull Integer postType,
                                @NotEmpty @Size(max=300) String tags,
                                @Size(max=2000) String introduction,
                                @Size(max = 3) String interaction,
                                @NotEmpty String uploadFileList) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        List<VideoInfoFilePost> videoInfoFilePostList = JsonUtils.convertJson2List(uploadFileList, VideoInfoFilePost.class);
        //只有create_time last_update_time status origin_info duration 未初始化
        VideoInfoPost videoInfoPost = new VideoInfoPost();
        videoInfoPost.setVideoId(videoId);
        videoInfoPost.setVideoCover(videoCover);
        videoInfoPost.setVideoName(videoName);
        videoInfoPost.setUserId(tokenUserInfoDto.getUserId());
        videoInfoPost.setPCategoryId(pCategoryId);
        videoInfoPost.setCategoryId(categoryId);
        videoInfoPost.setPostType(postType);
        videoInfoPost.setTags(tags);
        videoInfoPost.setIntroduction(introduction);
        videoInfoPost.setInteraction(interaction);

        videoInfoPostService.saveVideoInfoPost(videoInfoPost, videoInfoFilePostList);
        return getSuccessResponseVO(null);
    }
}
