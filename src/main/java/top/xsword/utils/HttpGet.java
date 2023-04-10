package top.xsword.utils;
/*
 *ClassName:HttpGet
 *UserName:三号男嘉宾
 *CreateTime:2021-10-25
 *description:
 */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class HttpGet {
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    private HttpRequest.Builder httpRequest = HttpRequest.newBuilder();
    private final Properties properties;

    public HttpGet(Properties headers) {
        properties = headers;
        headers.forEach((key, val) -> httpRequest.header((String) key, (String) val));
    }

    public HttpGet() {
        properties = null;
    }

    public HttpGet setUrl(String url) {
        try {
            httpRequest.uri(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void addHeader(String key, String val) {
        httpRequest.header(key, val);
    }

    public void setHeader(String key, String val) {
        httpRequest.setHeader(key,val);
    }

    public void requestRebuild(){
        httpRequest = HttpRequest.newBuilder();
        properties.forEach((key, val) -> httpRequest.header((String) key, (String) val));
    }

    public <T> HttpResponse<T> doGet(HttpResponse.BodyHandler<T> bodyHandler) throws IOException, InterruptedException {
        HttpRequest request = httpRequest.build();
        return httpClient.send(request, bodyHandler);
    }

}
