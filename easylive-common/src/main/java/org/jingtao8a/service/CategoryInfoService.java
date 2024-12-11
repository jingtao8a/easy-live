package org.jingtao8a.service;

import org.jingtao8a.entity.po.CategoryInfo;
import org.jingtao8a.entity.query.CategoryInfoQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.vo.PaginationResultVO;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
@Description:分类信息Service
@Date:2024-12-09
*/
public interface CategoryInfoService {

	/**
	 * 根据条件查询列表
	*/
	List<CategoryInfo> findListByParam(CategoryInfoQuery param);

	/**
	 * 根据条件查询数量
	*/
	Long findCountByParam(CategoryInfoQuery param);

	/**
	 * 分页查询
	*/
	PaginationResultVO<CategoryInfo> findListByPage(CategoryInfoQuery param);

	/**
	 * 新增
	*/
	Long add(CategoryInfo bean);

	/**
	 * 新增/修改
	*/
	Long addOrUpdate(CategoryInfo bean);

	/**
	 * 批量新增
	*/
	Long addBatch(List<CategoryInfo> listBean);

	/**
	 * 批量新增/修改
	*/
	Long addOrUpdateBatch(List<CategoryInfo> listBean);

	/**
	 * 根据CategoryId查询
	*/
	CategoryInfo selectByCategoryId(Integer categoryId);

	/**
	 * 根据CategoryId更新
	*/
	Long updateByCategoryId(CategoryInfo bean, Integer categoryId);

	/**
	 * 根据CategoryId删除
	*/
	Long deleteByCategoryId(Integer categoryId);

	/**
	 * 根据CategoryCode查询
	*/
	CategoryInfo selectByCategoryCode(String categoryCode);

	/**
	 * 根据CategoryCode更新
	*/
	Long updateByCategoryCode(CategoryInfo bean, String categoryCode);

	/**
	 * 根据CategoryCode删除
	*/
	Long deleteByCategoryCode(String categoryCode);

	void saveCategory(CategoryInfo bean) throws BusinessException;

	void delCategory(Integer categoryId);

	void changeSort(Integer pCategoryId, String categoryId);

	List<CategoryInfo> getAllCategoryList();
}