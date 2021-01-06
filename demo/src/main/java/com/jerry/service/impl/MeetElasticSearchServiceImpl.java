package com.jerry.service.impl;

import com.jerry.service.MeetElasticSearchService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class MeetElasticSearchServiceImpl implements MeetElasticSearchService {
    private static Logger log = LoggerFactory.getLogger(MeetElasticSearchServiceImpl.class);


    private RestClient restClient;

    @Override
    public void initEs() {
        restClient = RestClient.builder(new HttpHost("192.168.43.122", 9200, "http"), new HttpHost("192.168.43.122", 9201, "http")).build();
        log.info(" ElasticSearch init service.");
    }

    @Override
    public void closeEs() {
        try {
            restClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看api
     */
    @Override
    public String CatApi() throws Exception {
        String method = "GET";
        String endpoint = "/_cat";
        Request request = new Request(method, endpoint);
        Response response = restClient.performRequest(request);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * 创建索引
     *
     * @throws Exception
     */

    public String CreateIndex() throws IOException {
        String method = "PUT";
        String endpoint = "/test-index";
        Response response = restClient.performRequest(new Request(method, endpoint));
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * 获取文档
     *
     * @throws Exception
     */
    public void getDocument() throws Exception {
        String method = "GET";
        String endpoint = "/test-index/test/1";
        Response response = restClient.performRequest(new Request(method, endpoint));
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


    /**
     * 查询所有数据
     *
     * @throws Exception
     */
    public void QueryAll() throws Exception {
        String method = "POST";
        String endpoint = "/test-index/test/_search";
        HttpEntity entity = new NStringEntity("{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  }\n" +
                "}", ContentType.APPLICATION_JSON);

        Request request = new Request(method, endpoint);
        request.setEntity(entity);
        Response response = restClient.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    /**
     * 根据ID获取
     *
     * @throws Exception
     */
    public void QueryByField() throws Exception {
        String method = "POST";
        String endpoint = "/test-index/test/_search";
        HttpEntity entity = new NStringEntity("{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"user\": \"kimchy\"\n" +
                "    }\n" +
                "  }\n" +
                "}", ContentType.APPLICATION_JSON);
        Request request = new Request(method, endpoint);
        request.setEntity(entity);
        Response response = restClient.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }

    /**
     * 更新数据
     *
     * @throws Exception
     */
    public void UpdateByScript() throws Exception {
        String method = "POST";
        String endpoint = "/test-index/test/1/_update";
        HttpEntity entity = new NStringEntity("{\n" +
                "  \"doc\": {\n" +
                "    \"user\":\"大美女\"\n" +
                "	}\n" +
                "}", ContentType.APPLICATION_JSON);
        Request request = new Request(method, endpoint);
        request.setEntity(entity);
        Response response = restClient.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


    public void GeoBoundingBox() throws IOException {
        String method = "POST";
        String endpoint = "/attractions/restaurant/_search";
        HttpEntity entity = new NStringEntity("{\n" +
                "  \"query\": {\n" +
                "    \"match_all\": {}\n" +
                "  },\n" +
                "  \"post_filter\": {\n" +
                "    \"geo_bounding_box\": {\n" +
                "      \"location\": {\n" +
                "        \"top_left\": {\n" +
                "          \"lat\": 39.990481,\n" +
                "          \"lon\": 116.277144\n" +
                "        },\n" +
                "        \"bottom_right\": {\n" +
                "          \"lat\": 39.927323,\n" +
                "          \"lon\": 116.405638\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}", ContentType.APPLICATION_JSON);
        Request request = new Request(method, endpoint);
        request.setEntity(entity);
        Response response = restClient.performRequest(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }


}
