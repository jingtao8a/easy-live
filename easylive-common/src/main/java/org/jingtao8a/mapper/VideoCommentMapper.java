package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@Description:评论Mapper
@Date:2024-12-17
*/

public interface VideoCommentMapper<T,P> extends BaseMapper {
	/**
	 * 根据CommentId查询
	*/
	 T selectByCommentId(@Param("commentId") Integer commentId);

	/**
	 * 根据CommentId更新
	*/
	 Long updateByCommentId(@Param("bean") T t, @Param("commentId") Integer commentId);

	/**
	 * 根据CommentId删除
	*/
	 Long deleteByCommentId(@Param("commentId") Integer commentId);

	 List<T> selectChildComment(@Param("commentId") Integer commentId);

	 List<T> selectListWithChildren(@Param("query") P p);

	void updateCountInfo(@Param("commentId") Integer commentId, @Param("field") String field, @Param("changeCount") Integer changeCount,
						 @Param("opposeField") String opposeField, @Param("opposeChangeCount") Integer opposeChangeCount);

	void updateByParam(@Param("bean") T videoComment,@Param("query") P videoCommentQuery);

	void deleteByParam(@Param("query") P videoCommentQuery);
}