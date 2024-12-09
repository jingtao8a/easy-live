package org.jingtao8a.admin.controller;

import org.jingtao8a.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends ABaseController {
    @RequestMapping("/loadCategory")
    public ResponseVO loadDataList() {
        return getSuccessResponseVO(null);
    }
}

