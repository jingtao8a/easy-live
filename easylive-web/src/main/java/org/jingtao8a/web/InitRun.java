package org.jingtao8a.web;

import org.jingtao8a.component.EsSearchComponent;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InitRun implements ApplicationRunner {
    @Resource
    private EsSearchComponent esSearchComponent;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        esSearchComponent.createIndex();//创建es索引
    }
}
