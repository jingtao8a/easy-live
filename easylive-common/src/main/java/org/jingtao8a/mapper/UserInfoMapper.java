package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;
/**
@Description:用户信息Mapper
@Date:2024-11-06
*/

public interface UserInfoMapper<T,P> extends BaseMapper {
	/**
	 * 根据UserId查询
	*/
	 T selectByUserId(@Param("userId") String userId);

	/**
	 * 根据UserId更新
	*/
	 Long updateByUserId(@Param("bean") T t, @Param("userId") String userId);

	/**
	 * 根据UserId删除
	*/
	 Long deleteByUserId(@Param("userId") String userId);

	/**
	 * 根据Email查询
	*/
	 T selectByEmail(@Param("email") String email);

	/**
	 * 根据Email更新
	*/
	 Long updateByEmail(@Param("bean") T t, @Param("email") String email);

	/**
	 * 根据Email删除
	*/
	 Long deleteByEmail(@Param("email") String email);

	/**
	 * 根据NickName查询
	*/
	 T selectByNickName(@Param("nickName") String nickName);

	/**
	 * 根据NickName更新
	*/
	 Long updateByNickName(@Param("bean") T t, @Param("nickName") String nickName);

	/**
	 * 根据NickName删除
	*/
	 Long deleteByNickName(@Param("nickName") String nickName);

	 Long updateCoinCount(@Param("userId") String userId, @Param("changeCount") int changeCount);
}