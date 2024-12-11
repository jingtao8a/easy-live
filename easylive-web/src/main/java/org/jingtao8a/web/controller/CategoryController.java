package org.jingtao8a.web.controller;

import org.jingtao8a.service.CategoryInfoService;
import org.jingtao8a.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController extends ABaseController {
    @Resource
    private CategoryInfoService categoryInfoService;

    @RequestMapping("/loadAllCategory")
    public ResponseVO loadAllCategory() {
        return getSuccessResponseVO(categoryInfoService.getAllCategoryList());
    }
}

