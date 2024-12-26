package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
import org.jingtao8a.dto.UserMessageCountDto;
import java.util.*;

/**
@Description:用户消息表Mapper
@Date:2024-12-25
*/

public interface UserMessageMapper<T,P> extends BaseMapper {
	/**
	 * 根据MessageId查询
	*/
	 T selectByMessageId(@Param("messageId") Integer messageId);

	/**
	 * 根据MessageId更新
	*/
	 Long updateByMessageId(@Param("bean") T t, @Param("messageId") Integer messageId);

	/**
	 * 根据MessageId删除
	*/
	 Long deleteByMessageId(@Param("messageId") Integer messageId);

    List<UserMessageCountDto> getMessageTypeNoReadCount(@Param("userId") String userId);

	void updateByParam(@Param("bean") T userMessage, @Param("query") P userMessageQuery);
}