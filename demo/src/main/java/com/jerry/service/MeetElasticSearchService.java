package com.jerry.service;

import java.io.IOException;

public interface MeetElasticSearchService {
    void initEs();

    /**
     * 查看api
     */
    String CatApi() throws IOException, Exception;

    /**
     * 创建索引
     *
     * @return
     */
    String CreateIndex() throws IOException;

    void closeEs();
}
