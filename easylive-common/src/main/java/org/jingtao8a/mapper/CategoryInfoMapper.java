package org.jingtao8a.mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
@Description:分类信息Mapper
@Date:2024-12-09
*/

public interface CategoryInfoMapper<T,P> extends BaseMapper {
	/**
	 * 根据CategoryId查询
	*/
	 T selectByCategoryId(@Param("categoryId") Integer categoryId);

	/**
	 * 根据CategoryId更新
	*/
	 Long updateByCategoryId(@Param("bean") T t, @Param("categoryId") Integer categoryId);

	/**
	 * 根据CategoryId删除
	*/
	 Long deleteByCategoryId(@Param("categoryId") Integer categoryId);

	/**
	 * 根据CategoryCode查询
	*/
	 T selectByCategoryCode(@Param("categoryCode") String categoryCode);

	/**
	 * 根据CategoryCode更新
	*/
	 Long updateByCategoryCode(@Param("bean") T t, @Param("categoryCode") String categoryCode);

	/**
	 * 根据CategoryCode删除
	*/
	 Long deleteByCategoryCode(@Param("categoryCode") String categoryCode);

	/**
	 * 查找最大的sort
	 */
	Integer selectMaxSort(@Param("pCategoryId") Integer pCategoryId);

	Long deleteByParam(@Param("query")  P query);

	void updateSortBatch(@Param("list") List<T> list);
}