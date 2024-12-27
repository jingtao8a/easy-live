package org.jingtao8a.component;

import org.jingtao8a.config.AppConfig;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.SysSettingDto;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.dto.UploadingFileDto;
import org.jingtao8a.dto.VideoPlayInfoDto;
import org.jingtao8a.entity.po.CategoryInfo;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.redis.RedisUtils;
import org.jingtao8a.utils.DateUtils;
import org.jingtao8a.utils.StringTools;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Component
public class RedisComponent {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AppConfig appConfig;

    public String saveCheckCode(String checkCode) {
        String checkCodeKey = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey, checkCode, Constants.REDIS_KEY_EXPIRES_TEN_MINUTE);
        return checkCodeKey;
    }

    public String getCheckCode(String checkCodeKey) {
        return (String) redisUtils.get(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
    }

    public void cleanCheckCode(String checkCodeKey) {
        redisUtils.delete(Constants.REDIS_KEY_CHECK_CODE + checkCodeKey);
    }

    public void saveTokenInfo(TokenUserInfoDto tokenUserInfoDto) {
        String token = UUID.randomUUID().toString();
        tokenUserInfoDto.setToken(token);
        tokenUserInfoDto.setExpireAt(System.currentTimeMillis() + Constants.REDIS_KEY_EXPIRES_ONE_DAY * 7);
        redisUtils.setex(Constants.REDIS_KEY_TOKEN_WEB + token, tokenUserInfoDto, Constants.REDIS_KEY_EXPIRES_ONE_DAY * 7);
    }

    public void updateTokenUserInfo(TokenUserInfoDto tokenUserInfoDto) {
        redisUtils.setex(Constants.REDIS_KEY_TOKEN_WEB + tokenUserInfoDto.getToken(), tokenUserInfoDto, Constants.REDIS_KEY_EXPIRES_ONE_DAY * 7);
    }

    public TokenUserInfoDto getTokenInfo(String token) {
        return (TokenUserInfoDto) redisUtils.get(Constants.REDIS_KEY_TOKEN_WEB + token);
    }

    public void cleanToken(String token) {
        redisUtils.delete(Constants.REDIS_KEY_TOKEN_WEB + token);
    }

    public String saveTokenInfo4Admin(String account) {
        String token = UUID.randomUUID().toString();
        redisUtils.setex(Constants.REDIS_KEY_TOKEN_ADMIN + token, account, Constants.REDIS_KEY_EXPIRES_ONE_DAY);
        return token;
    }

    public String getTokenInfo4Admin(String token) {
        return (String) redisUtils.get(Constants.REDIS_KEY_TOKEN_ADMIN + token);
    }

    public void cleanToken4Admin(String token) {
        redisUtils.delete(Constants.REDIS_KEY_TOKEN_ADMIN + token);
    }

    public void saveCategoryList(List<CategoryInfo> list) {
        redisUtils.set(Constants.REDIS_KEY_CATEGORY_LIST, list);
    }

    public List<CategoryInfo> getCategoryList() {
        return (List<CategoryInfo>)redisUtils.get(Constants.REDIS_KEY_CATEGORY_LIST);
    }

    public String savePreVideoFileInfo(String userId, String fileName, Integer chunks) {
        String uploadId = StringTools.getRandomString(Constants.LENGTH_15);
        UploadingFileDto fileDto = new UploadingFileDto();
        fileDto.setUploadId(uploadId);
        fileDto.setChunkIndex(0);
        fileDto.setChunks(chunks);
        fileDto.setFileName(fileName);
        String day = DateUtils.format(new Date(), DateUtils.YYYYMMDD);
        String filePath = day + '/' + userId + uploadId;
        fileDto.setFilePath(filePath);
        String folder = appConfig.getProjectFolder() + Constants.FILE_FOLDER
                + Constants.FILE_TEMP + filePath;
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        redisUtils.setex(Constants.REDIS_KEY_UPLOADING_FILE + userId + uploadId, fileDto, Constants.REDIS_KEY_EXPIRES_ONE_DAY);
        return uploadId;
    }

    public UploadingFileDto getUploadingFileDto(String userId, String uploadId) {
        return (UploadingFileDto) redisUtils.get(Constants.REDIS_KEY_UPLOADING_FILE + userId + uploadId);
    }

    public SysSettingDto getSysSettingDto() {
        SysSettingDto sysSettingDto = (SysSettingDto) redisUtils.get(Constants.REDIS_KEY_SYS_SETTING);
        if (sysSettingDto == null) {
            sysSettingDto = new SysSettingDto();
            redisUtils.set(Constants.REDIS_KEY_SYS_SETTING, sysSettingDto);
        }
        return sysSettingDto;
    }

    public void saveSysSettingDto(SysSettingDto sysSettingDto) {
        redisUtils.set(Constants.REDIS_KEY_SYS_SETTING, sysSettingDto);
    }

    public void updateUploadingFileDto(String userId, String uploadId, UploadingFileDto uploadingFileDto) {
        redisUtils.setex(Constants.REDIS_KEY_UPLOADING_FILE + userId + uploadId, uploadingFileDto, Constants.REDIS_KEY_EXPIRES_ONE_DAY);
    }

    public void delUploadingFileDto(String userId, String uploadId) {
        redisUtils.delete(Constants.REDIS_KEY_UPLOADING_FILE + userId + uploadId);
    }

    public void addFile2DelQueue(String videoId, List<String> filePathList) {
        redisUtils.lpushAll(Constants.REDIS_KEY_FILE_DEL + videoId, filePathList, Long.valueOf(Constants.REDIS_KEY_EXPIRES_ONE_DAY * 7));
    }

    public List<String> getDelFileList(String videoId) {
        return redisUtils.getQueueList(Constants.REDIS_KEY_FILE_DEL + videoId);
    }

    public void cleanDelFileList(String videoId) {
        redisUtils.delete(Constants.REDIS_KEY_FILE_DEL + videoId);
    }

    public void addFile2TransferQueue(List<VideoInfoFilePost> videoInfoFilePostList) {
        redisUtils.lpushAll(Constants.REDIS_KEY_QUEUE_TRANSFER, videoInfoFilePostList, 0L);
    }

    public void addFile2TransferQueue4Single(VideoInfoFilePost videoInfoFilePost) {
        redisUtils.lpush(Constants.REDIS_KEY_QUEUE_TRANSFER, videoInfoFilePost, 0L);
    }

    public VideoInfoFilePost getFileFromTransferQueue() {
        return (VideoInfoFilePost)redisUtils.rpop(Constants.REDIS_KEY_QUEUE_TRANSFER);
    }

    public VideoPlayInfoDto getVideoPlayInfoFromVideoPlayQueue() {
        return (VideoPlayInfoDto)redisUtils.rpop(Constants.REDIS_KEY_QUEUE_VIDEO_PLAY);
    }

    public Long reportVideoPlayOnline(String fileId, String deviceId) {
        String userPlayOnlineKey = String.format(Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER, fileId, deviceId);
        String playOnlineCountKey = String.format(Constants.REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE, fileId);
        if (!redisUtils.keyExists(userPlayOnlineKey)) {
            redisUtils.setex(userPlayOnlineKey, fileId, Constants.REDIS_KEY_EXPIRES_ONE_SECOND * 8);
            return redisUtils.incrementex(playOnlineCountKey, Long.valueOf(Constants.REDIS_KEY_EXPIRES_ONE_SECOND * 10));
        }
        redisUtils.expire(userPlayOnlineKey, Constants.REDIS_KEY_EXPIRES_ONE_SECOND * 8);
        redisUtils.expire(playOnlineCountKey, Constants.REDIS_KEY_EXPIRES_ONE_SECOND * 10);
        Long count = Long.valueOf((Integer)redisUtils.get(playOnlineCountKey));
        return count == null ? 1L : count;
    }

    public void decrementPlayOnlineCount(String key) {
        redisUtils.decrement(key);
    }

    public void addKeyWordCount(String keyWord) {
        redisUtils.zaddCount(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, keyWord);
    }

    public List<String> getKeywordTop(Integer top) {
        return redisUtils.getZSetList(Constants.REDIS_KEY_VIDEO_SEARCH_COUNT, top - 1);
    }

    public void addVideoPlayInfo(VideoPlayInfoDto videoPlayInfoDto) {
        redisUtils.lpush(Constants.REDIS_KEY_QUEUE_VIDEO_PLAY, videoPlayInfoDto, null);
    }

    public void recordVideoPlayCount(String videoId) {
        String date = DateUtils.format(new Date(), DateUtils.YYYY_MM_DD);
        redisUtils.incrementex(Constants.REDIS_KEY_VIDEO_PLAY_COUNT + date + ":" + videoId, (long)Constants.REDIS_KEY_EXPIRES_ONE_DAY * 2);
    }

    public Map<String, Integer> getVideoPlayCount(String date) {
        Map<String, Integer> result = redisUtils.getBatch(Constants.REDIS_KEY_VIDEO_PLAY_COUNT + date);
        return result;
    }
}
