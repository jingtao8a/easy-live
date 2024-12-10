package org.jingtao8a.entity.query;
import lombok.Data;
import lombok.ToString;

/**
@Description:分类信息
@Date:2024-12-09
*/
@Data
@ToString
public class CategoryInfoQuery extends BaseQuery {
	/**
	 * 自增分类id
	*/
	private Integer categoryId;
	/**
	 * 分类编码
	*/
	private String categoryCode;
	private String categoryCodeFuzzy;

	/**
	 * 分类名称
	*/
	private String categoryName;
	private String categoryNameFuzzy;

	/**
	 * 父级分类id
	*/
	private Integer pCategoryId;
	/**
	 * 图标
	*/
	private String icon;
	private String iconFuzzy;

	/**
	 * 背景图
	*/
	private String background;
	private String backgroundFuzzy;

	/**
	 * 排序号
	*/
	private Integer sort;

	private Integer CategoryIdOrPCategoryId;

	private Boolean convert2Tree;
}