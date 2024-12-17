package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
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

}