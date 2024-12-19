package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:用户关注表Mapper
@Date:2024-12-19
*/

public interface UserFocusMapper<T,P> extends BaseMapper {
	/**
	 * 根据UserIdAndFocusUserId查询
	*/
	 T selectByUserIdAndFocusUserId(@Param("userId") String userId, @Param("focusUserId") String focusUserId);

	/**
	 * 根据UserIdAndFocusUserId更新
	*/
	 Long updateByUserIdAndFocusUserId(@Param("bean") T t, @Param("userId") String userId, @Param("focusUserId") String focusUserId);

	/**
	 * 根据UserIdAndFocusUserId删除
	*/
	 Long deleteByUserIdAndFocusUserId(@Param("userId") String userId, @Param("focusUserId") String focusUserId);

	 Long selectFansCount(@Param("userId") String userId);

	 Long selectFocusCount(@Param("userId") String userId);
}