package org.jingtao8a.service.impl;

import org.jingtao8a.entity.po.CategoryInfo;
import org.jingtao8a.entity.query.CategoryInfoQuery;
import org.jingtao8a.entity.query.SimplePage;
import org.jingtao8a.enums.PageSize;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.mapper.CategoryInfoMapper;
import org.jingtao8a.service.CategoryInfoService;
import org.jingtao8a.vo.PaginationResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
@Description:CategoryInfoService
@Date:2024-12-09
*/
@Service("categoryInfoService")
public class CategoryInfoServiceImpl implements CategoryInfoService {

	@Resource
	private CategoryInfoMapper<CategoryInfo, CategoryInfoQuery> categoryInfoMapper;
	/**
	 * 根据条件查询列表
	*/
	@Override
	public List<CategoryInfo> findListByParam(CategoryInfoQuery query) {
		List<CategoryInfo> list = this.categoryInfoMapper.selectList(query);
		if (query.getConvert2Tree() != null && query.getConvert2Tree()) {
			return convertLine2Tree(list, 0);
		}
		return list;
	}

	public List<CategoryInfo> convertLine2Tree(List<CategoryInfo> list, Integer pid) {
		List<CategoryInfo> res = new ArrayList<>();
		for (CategoryInfo c : list) {
			if (c.getPCategoryId().equals(pid)) {
				res.add(c);
			}
		}
		for (CategoryInfo c : res) {
			List<CategoryInfo> children = convertLine2Tree(list, c.getCategoryId());
			c.setChildren(children);
		}
		return res;
	}

	/**
	 * 根据条件查询数量
	*/
	@Override
	public Long findCountByParam(CategoryInfoQuery query) {
		return this.categoryInfoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	*/
	@Override
	public PaginationResultVO<CategoryInfo> findListByPage(CategoryInfoQuery query) {
		Long count = this.findCountByParam(query);
		Long pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize(): query.getPageSize();
		SimplePage simplePage = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(simplePage);
		List<CategoryInfo> userInfoList = findListByParam(query);
		PaginationResultVO<CategoryInfo> paginationResultVO = new PaginationResultVO<>(count, simplePage.getPageSize(), simplePage.getPageNo(), simplePage.getPageTotal(), userInfoList);
		return paginationResultVO;
	}

	/**
	 * 新增
	*/
	@Override
	public Long add(CategoryInfo bean) {
		return categoryInfoMapper.insert(bean);
	}

	/**
	 * 新增/修改
	*/
	@Override
	public Long addOrUpdate(CategoryInfo bean) {
		return categoryInfoMapper.insertOrUpdate(bean);
	}

	/**
	 * 批量新增
	*/
	@Override
	public Long addBatch(List<CategoryInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return categoryInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	*/
	@Override
	 public Long addOrUpdateBatch(List<CategoryInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0L;
		}
		return categoryInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据CategoryId查询
	*/
	@Override
	public CategoryInfo selectByCategoryId(Integer categoryId) {
		return categoryInfoMapper.selectByCategoryId(categoryId);
	}

	/**
	 * 根据CategoryId更新
	*/
	@Override
	public Long updateByCategoryId(CategoryInfo bean, Integer categoryId) {
		return categoryInfoMapper.updateByCategoryId(bean, categoryId);
	}

	/**
	 * 根据CategoryId删除
	*/
	@Override
	public Long deleteByCategoryId(Integer categoryId) {
		return categoryInfoMapper.deleteByCategoryId(categoryId);
	}

	/**
	 * 根据CategoryCode查询
	*/
	@Override
	public CategoryInfo selectByCategoryCode(String categoryCode) {
		return categoryInfoMapper.selectByCategoryCode(categoryCode);
	}

	/**
	 * 根据CategoryCode更新
	*/
	@Override
	public Long updateByCategoryCode(CategoryInfo bean, String categoryCode) {
		return categoryInfoMapper.updateByCategoryCode(bean, categoryCode);
	}

	/**
	 * 根据CategoryCode删除
	*/
	@Override
	public Long deleteByCategoryCode(String categoryCode) {
		return categoryInfoMapper.deleteByCategoryCode(categoryCode);
	}

	@Override
	public void saveCategory(CategoryInfo bean) throws BusinessException {
		CategoryInfo res = categoryInfoMapper.selectByCategoryCode(bean.getCategoryCode());
		if (res != null) {
			throw new BusinessException("分类编号已存在");
		}
		if (bean.getCategoryId() == null) {//新增分类
			Integer maxSort = categoryInfoMapper.selectMaxSort(bean.getPCategoryId());
			bean.setSort(maxSort + 1);
			this.categoryInfoMapper.insert(bean);
		} else {//更新分类
			this.categoryInfoMapper.updateByCategoryId(bean, bean.getCategoryId());
		}
	}

	@Override
	public void delCategory(Integer categoryId) {
		//TODO 查询分类下是否有视频

		CategoryInfoQuery categoryInfoQuery = new CategoryInfoQuery();
		categoryInfoQuery.setCategoryIdOrPCategoryId(categoryId);
		categoryInfoMapper.deleteByParam(categoryInfoQuery);

		//TODO 刷新缓存
	}

	@Override
	public void changeSort(Integer pCategoryId, String categoryIds) {
		String[] strings = categoryIds.split(",");
		List<CategoryInfo> categoryInfoList = new ArrayList<>();
		Integer sort = 1;
		for (int i = 0; i < strings.length; i++) {
			CategoryInfo c = new CategoryInfo();
			c.setCategoryId(Integer.parseInt(strings[i]));
			c.setSort(sort++);
			categoryInfoList.add(c);
		}
		categoryInfoMapper.updateSortBatch(categoryInfoList);

	}
}