package org.jingtao8a.entity.po;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
@Description:分类信息
@Date:2024-12-09
*/
@Data
@ToString
public class CategoryInfo implements Serializable {
	/**
	 * 自增分类id
	*/
	private Integer categoryId;
	/**
	 * 分类编码
	*/
	private String categoryCode;
	/**
	 * 分类名称
	*/
	private String categoryName;
	/**
	 * 父级分类id
	*/
	private Integer pCategoryId;
	/**
	 * 图标
	*/
	private String icon;
	/**
	 * 背景图
	*/
	private String background;
	/**
	 * 排序号
	*/
	private Integer sort;

	private List<CategoryInfo> children;
}