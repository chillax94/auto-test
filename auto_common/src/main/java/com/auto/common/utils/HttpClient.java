package com.auto.common.utils;

import com.alibaba.fastjson.JSON;
import io.restassured.response.Response;

import java.awt.print.Printable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpClient {


    public static Response get(String url, Map pathParams, Map queryParams, Map header) {
        Response response = given().headers(header)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .get(url)
                .then().log().all()
                .extract().response();
        return response;

    }

    public static Response get(String url, Map header) {
        Map pathParams = new HashMap();
        Map queryParams = new HashMap();
        return get(url, pathParams, queryParams, header);
    }

    public static Response queryGet(String url, Map queryParams, Map header) {
        Map pathParams = new HashMap();
        return get(url, pathParams, queryParams, header);
    }

    public static Response pathGet(String url, Map pathParams, Map header) {
        Map queryParams = new HashMap();
        return get(url, pathParams, queryParams, header);
    }


    public static Response post(String url, Map body, Map pathParams, Map queryParams, Map header) {
        Response response = given()
                .headers(header)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .body(JSON.toJSONString(body))
                .when().post(url)
                .then().log().all()
                .extract().response();
        return response;
    }


    public static Response post(String url, Map body, Map header) {
        Map pathParams = new HashMap();
        Map queryParams = new HashMap();
        return post(url, body, pathParams, queryParams, header);
    }

    public static Response post(String url, Map header) {
        Map pathParams = new HashMap();
        Map queryParams = new HashMap();
        Map body = new HashMap();
        return post(url, body, pathParams, queryParams, header);
    }


    public static Response queryPost(String url, Map body, Map queryParams, Map header) {
        Map pathParams = new HashMap();
        return post(url, body, pathParams, queryParams, header);
    }

    public static Response pathPost(String url, Map body, Map pathParams, Map header) {
        Map queryParams = new HashMap();
        return post(url, body, pathParams, queryParams, header);
    }

    public static Response postFile(String url, String path, Map header) {
        Response response = given()
                .headers(header)
                .multiPart(new File(path))
                .when().post(url)
                .then().log().all()
                .extract().response();
        return response;
    }

}
