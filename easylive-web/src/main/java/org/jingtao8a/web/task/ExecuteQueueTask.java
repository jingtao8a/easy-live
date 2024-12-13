package org.jingtao8a.web.task;

import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.jingtao8a.entity.po.VideoInfoFilePost;
import org.jingtao8a.service.VideoInfoFilePostService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class ExecuteQueueTask {
    private ExecutorService executorService = Executors.newFixedThreadPool(Constants.LENGTH_5);

    @Resource
    private RedisComponent  redisComponent;

    @Resource
    private VideoInfoFilePostService videoInfoFilePostService;

    @PostConstruct
    public void consumTransferFileQueue() {
        executorService.submit(()->{
            while (true) {
                VideoInfoFilePost videoInfoFilePost = null;
                try {
                    videoInfoFilePost = redisComponent.getFileFromTransferQueue();
                    if (videoInfoFilePost == null) {
                        Thread.sleep(1500);
                        continue;
                    }
                    videoInfoFilePostService.transferVideoFile(videoInfoFilePost);
                } catch (Exception e) {
                    log.error("获取转码文件队列信息失败", e);
                    if (videoInfoFilePost != null) {
                        redisComponent.addFile2TransferQueue4Single(videoInfoFilePost);
                    }
                }
            }
        });
    }


}
