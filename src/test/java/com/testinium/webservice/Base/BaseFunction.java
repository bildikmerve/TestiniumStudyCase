package com.testinium.webservice.Base;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.util.Dictionary;
import java.util.Map;

public class BaseFunction {

    private String APIKey;
    private String APIToken;

    public  BaseFunction() {
    }

    public  HttpResponse<JsonNode> Get(String url) {
        HttpResponse<JsonNode> response = null;
            try {
                response = Unirest.get(url)
                    .header("Accept", "application/json")
                    .queryString("key", getAPIKey())
                    .queryString("token", getAPIToken())
                    .asJson();
        }
        catch (Exception ex) {
            System.out.println();
        }
        return response;
    }

    public HttpResponse<String>  Post(String url, Map<String, Object> data) {
        HttpResponse<String> response = null;
        try {
            response = Unirest.post(url)
                    .queryString(data)
                    .queryString("key", getAPIKey())
                    .queryString("token", getAPIToken())
                    .asString();
        }
        catch (Exception ex) {
            System.out.println();
        }
        return response;
    }

    public  HttpResponse<JsonNode>  Put(String url, String id,  Map<String, Object> data) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.put(url + id )
                    .header("Accept", "application/json")
                    .queryString(data)
                    .queryString("key", getAPIKey())
                    .queryString("token", getAPIToken())
                    .asJson();
        }
        catch (Exception ex) {
            System.out.println();
        }
        return response;
    }

    public  HttpResponse<String>  Delete(String url, String id) {
        HttpResponse<String> response = null;
        try {
                response = Unirest.delete(url + id)
                        .queryString("key", APIKey)
                        .queryString("token", APIToken)
                        .asString();
            }
            catch (Exception ex) {
                System.out.println();
            }
        return response;
    }

    public String getAPIKey() {
        return APIKey;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }

    public String getAPIToken() {
        return APIToken;
    }

    public void setAPIToken(String APIToken) {
        this.APIToken = APIToken;
    }
}
