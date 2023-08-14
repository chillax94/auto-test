package com.auto.common.service;

import com.auto.common.utils.HttpClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpService {

    public String json() {
        String url = "";
        String method = "";


        Map pathParams = new HashMap();
        Map body = new HashMap();
        Map queryParams = new HashMap();
        Map header = new HashMap();

        String[] filePaths= {""};
        if ("post".equals(method)) {
            if (pathParams != null && queryParams != null) {
                return HttpClient.post(url, body, pathParams, queryParams, header).print();
            } else if (pathParams != null && queryParams == null) {
                return HttpClient.pathPost(url, body, pathParams, header).print();

            } else if (pathParams == null && queryParams != null) {
                return HttpClient.queryPost(url, body, queryParams, header).print();

            } else if (filePaths != null && filePaths.length > 0) {
                List<String> res = new ArrayList<>();
                header.put("Content-Type", "multipart/form-data");
                for (String path : filePaths) {
                    String resPrint = HttpClient.postFile(url, path, header).print();
                    res.add(resPrint);
                    log.info("上传文件路径为：{}，返回结果：{}", path, res);
                }
                return res.toString();
            } else if (body == null) {
                return HttpClient.post(url, header).print();

            } else {
                return HttpClient.post(url, body, header).print();
            }
        }
        if ("get".equals(method)) {
            if (pathParams != null && queryParams != null) {
                return HttpClient.get(url, pathParams, queryParams, header).print();
            } else if (pathParams != null && queryParams == null) {
                return HttpClient.pathGet(url, pathParams, header).print();

            } else if (pathParams == null && queryParams != null) {
                return HttpClient.queryGet(url, queryParams, header).print();

            } else {
                return HttpClient.get(url, header).print();
            }
        }
        return null;
    }
}
