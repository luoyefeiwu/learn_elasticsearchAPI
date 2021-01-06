package com.jerry.controller;

import com.jerry.service.MeetElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/springboot/es")
public class MeetElasticSearchController {

    @Autowired
    private MeetElasticSearchService meetElasticSearchService;


    @RequestMapping(value = "/init")
    public String initElasticSearch() {
        meetElasticSearchService.initEs();
        return "Init ElasticSearch Over!";
    }

    @RequestMapping(value = "/catApi")
    public String CatApi() throws Exception {
        return meetElasticSearchService.CatApi();
    }

    @RequestMapping(value = "/createIndex")
    public String CreateIndex() throws IOException {
        return  meetElasticSearchService.CreateIndex();
    }

}
