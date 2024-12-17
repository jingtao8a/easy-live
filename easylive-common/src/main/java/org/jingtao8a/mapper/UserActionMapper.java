package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:用户行为 点赞、评论Mapper
@Date:2024-12-17
*/

public interface UserActionMapper<T,P> extends BaseMapper {
	/**
	 * 根据ActionId查询
	*/
	 T selectByActionId(@Param("actionId") Integer actionId);

	/**
	 * 根据ActionId更新
	*/
	 Long updateByActionId(@Param("bean") T t, @Param("actionId") Integer actionId);

	/**
	 * 根据ActionId删除
	*/
	 Long deleteByActionId(@Param("actionId") Integer actionId);

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId查询
	*/
	 T selectByVideoIdAndCommentIdAndActionTypeAndUserId(@Param("videoId") String videoId, @Param("commentId") Integer commentId, @Param("actionType") Integer actionType, @Param("userId") String userId);

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId更新
	*/
	 Long updateByVideoIdAndCommentIdAndActionTypeAndUserId(@Param("bean") T t, @Param("videoId") String videoId, @Param("commentId") Integer commentId, @Param("actionType") Integer actionType, @Param("userId") String userId);

	/**
	 * 根据VideoIdAndCommentIdAndActionTypeAndUserId删除
	*/
	 Long deleteByVideoIdAndCommentIdAndActionTypeAndUserId(@Param("videoId") String videoId, @Param("commentId") Integer commentId, @Param("actionType") Integer actionType, @Param("userId") String userId);

}