package org.jingtao8a.admin.controller;

import org.jingtao8a.entity.po.CategoryInfo;
import org.jingtao8a.entity.query.CategoryInfoQuery;
import org.jingtao8a.exception.BusinessException;
import org.jingtao8a.service.CategoryInfoService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/category")
public class CategoryController extends ABaseController {
    @Resource
    private CategoryInfoService categoryInfoService;

    @RequestMapping("/loadCategory")
    public ResponseVO loadCategory(CategoryInfoQuery query) {
        query.setOrderBy("sort asc");
        query.setConvert2Tree(true);
        List<CategoryInfo> list =  categoryInfoService.findListByParam(query);
        return getSuccessResponseVO(list);
    }

    @RequestMapping("/saveCategory")
    public ResponseVO saveCategory(@NotNull Integer pCategoryId,
                                   Integer categoryId,
                                   @NotEmpty String categoryCode,
                                   @NotEmpty String categoryName,
                                   String icon,
                                   String background) throws BusinessException {
        CategoryInfo info = new CategoryInfo();
        info.setPCategoryId(pCategoryId);
        info.setCategoryId(categoryId);
        info.setCategoryCode(categoryCode);
        info.setCategoryName(categoryName);
        info.setIcon(icon);
        info.setBackground(background);
        categoryInfoService.saveCategory(info);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delCategory")
    public ResponseVO delCategory(@NotNull Integer categoryId) throws BusinessException {
        categoryInfoService.delCategory(categoryId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/changeSort")
    public ResponseVO changeSort(@NotNull Integer pCategoryId, @NotNull String categoryIds) throws BusinessException {
        categoryInfoService.changeSort(pCategoryId, categoryIds);
        return getSuccessResponseVO(null);
    }
}

