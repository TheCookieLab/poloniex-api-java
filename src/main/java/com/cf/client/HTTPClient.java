package com.cf.client;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author David
 */
public class HTTPClient
{

    private final HttpHost proxy;

    public HTTPClient() {
        this.proxy = null;
    }

    public HTTPClient(HttpHost proxy) {
        this.proxy = proxy;
    }


    public String postHttp(String url, List<NameValuePair> params, List<NameValuePair> headers) throws IOException
    {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        post.getEntity().toString();

        if (headers != null)
        {
            for (NameValuePair header : headers)
            {
                post.addHeader(header.getName(), header.getValue());
            }
        }

        HttpClient httpClient = HttpClientBuilder.create().setProxy(proxy).build();
        HttpResponse response = httpClient.execute(post);

        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            return EntityUtils.toString(entity);

        }
        return null;
    }

    public String getHttp(String url, List<NameValuePair> headers) throws IOException
    {
        HttpRequestBase request = new HttpGet(url);

        if (headers != null)
        {
            for (NameValuePair header : headers)
            {
                request.addHeader(header.getName(), header.getValue());
            }
        }

        HttpClient httpClient = HttpClientBuilder.create().setProxy(proxy).build();
        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        if (entity != null)
        {
            return EntityUtils.toString(entity);

        }
        return null;
    }
}
