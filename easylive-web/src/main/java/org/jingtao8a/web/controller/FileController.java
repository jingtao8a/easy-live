package org.jingtao8a.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.config.AppConfig;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.dto.SysSettingDto;
import org.jingtao8a.dto.TokenUserInfoDto;
import org.jingtao8a.dto.UploadingFileDto;
import org.jingtao8a.enums.ResponseCodeEnum;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.utils.DateUtils;
import org.jingtao8a.utils.FFmpegUtils;
import org.jingtao8a.utils.StringTools;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

@RestController
@RequestMapping("/file")
@Validated
@Slf4j
public class FileController extends ABaseController{
    @Resource
    private AppConfig appConfig;

    @Resource
    private FFmpegUtils fFmpegUtils;

    @Resource
    private RedisComponent redisComponent;

    @RequestMapping("/uploadImage")
    public ResponseVO uploadImage(@NotNull MultipartFile file, @NotNull Boolean createThumbnail) throws IOException, BusinessException {
        String month = DateUtils.format(new Date(), DateUtils.YYYYMM);
        String folder = appConfig.getProjectFolder() + Constants.FILE_FOLDER + Constants.FILE_COVER + month;
        File folderFile = new File(folder);
        if (!folderFile.exists()) {
            folderFile.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        String fileSuffix = StringTools.getFileSuffix(fileName);;
        String realFileName = StringTools.getRandomString(Constants.LENGTH_30) + fileSuffix;
        String filePath = folder + "/" + realFileName;
        file.transferTo(new File(filePath));
        if (createThumbnail) {
            //生成缩略图
            fFmpegUtils.createImageThumbnail(filePath);
        }
        return getSuccessResponseVO(Constants.FILE_COVER + month + "/" + realFileName);
    }

    @RequestMapping("/getResource")
    public void getResource(HttpServletResponse response, @NotNull String sourceName) throws BusinessException {
        if (!StringTools.pathIsOk(sourceName)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String suffix = StringTools.getFileSuffix(sourceName);
        response.setContentType("image/" + suffix.replace(".", ""));
        response.setHeader("Cache-Control", "max-age=2592000");
        readFile(response, sourceName);
    }

    protected void readFile(HttpServletResponse response, String sourceName) {
        File file = new File(appConfig.getProjectFolder() + Constants.FILE_FOLDER + sourceName);
        if (!file.exists()) {
            return;
        }
        try {
            OutputStream out = response.getOutputStream();
            FileInputStream in = new FileInputStream(file);
            byte[] byteData = new byte[1024];
            int len = 0;
            while ((len = in.read(byteData)) != -1) {
                out.write(byteData, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            log.error("读取文件异常", e);
        }
    }

    @RequestMapping("/preUploadVideo")
    public ResponseVO preUploadVideo(@NotNull String fileName, @NotNull Integer chunks) throws BusinessException {
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        String uploadId = redisComponent.savePreVideoFileInfo(tokenUserInfoDto.getUserId(), fileName, chunks);
        return getSuccessResponseVO(uploadId);
    }

    @RequestMapping("/uploadVideo")
    public ResponseVO uploadVideo(MultipartFile chunkFile, @NotNull Integer chunkIndex, @NotEmpty String uploadId) throws BusinessException, IOException {
        if (chunkFile == null || chunkFile.getSize() == 0) {
            throw new BusinessException("文件不能为空");
        }
        TokenUserInfoDto tokenUserInfoDto = getTokenUserInfoDto();
        UploadingFileDto uploadingFileDto = redisComponent.getUploadingFileDto(tokenUserInfoDto.getUserId(), uploadId);
        if (uploadingFileDto == null) {
            throw new BusinessException("文件不存在，请重新上传");
        }
        SysSettingDto sysSettingDto = redisComponent.getSysSettingDto();
        if (uploadingFileDto.getFileSize() > sysSettingDto.getVideoSize() * Constants.MB_SIZE) {
            throw new BusinessException("文件大小超过大小限制");
        }
        //判断分片
        if (chunkIndex - 1 > uploadingFileDto.getChunkIndex() || chunkIndex > uploadingFileDto.getChunks() - 1) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        String folder = appConfig.getProjectFolder() + Constants.FILE_FOLDER + Constants.FILE_TEMP + uploadingFileDto.getFilePath();
        File targetFile = new File(folder + "/" + chunkIndex);
        chunkFile.transferTo(targetFile);
        uploadingFileDto.setChunkIndex(chunkIndex);
        uploadingFileDto.setFileSize(uploadingFileDto.getFileSize() + chunkFile.getSize());
        redisComponent.updateUploadingFileDto(tokenUserInfoDto.getUserId(), uploadId, uploadingFileDto);
        return getSuccessResponseVO(null);
    }
}
