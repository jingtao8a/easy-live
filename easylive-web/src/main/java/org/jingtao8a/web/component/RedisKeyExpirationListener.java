package org.jingtao8a.web.component;


import lombok.extern.slf4j.Slf4j;
import org.jingtao8a.component.RedisComponent;
import org.jingtao8a.constants.Constants;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Resource
    private RedisComponent redisComponent;

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = message.toString();
        if (!key.startsWith(Constants.REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE_PREFIX + Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX)) {
            return;
        }
        //监听 在线用户过期的key
        Integer userKeyIndex = key.indexOf(Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX) + Constants.REDIS_KEY_VIDEO_PLAY_COUNT_USER_PREFIX.length();
        String fileId = key.substring(userKeyIndex, userKeyIndex + Constants.LENGTH_20);
        redisComponent.decrementPlayOnlineCount(String.format(Constants.REDIS_KEY_VIDEO_PLAY_COUNT_ONLINE, fileId));
    }
}
