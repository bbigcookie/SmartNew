package UserMouleTest;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpConnector {

    private HttpClient client;
    private PostMethod pm;

    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HttpConnector(String url, String restAPI, byte[] bytes) {
        HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setSoTimeout(20000);
        client = new HttpClient(httpClientParams);
        pm = new PostMethod(url + restAPI);
        InputStream is = new ByteArrayInputStream(bytes);
        InputStreamRequestEntity inputStreamRequestEntity = new InputStreamRequestEntity(is);
        pm.setRequestEntity(inputStreamRequestEntity);
    }

    public HttpConnector(String url, byte[] bytes) {
        HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setSoTimeout(20000);
        client = new HttpClient(httpClientParams);
        pm = new PostMethod(url);
        InputStream is = new ByteArrayInputStream(bytes);
        InputStreamRequestEntity inputStreamRequestEntity = new InputStreamRequestEntity(is);
        pm.setRequestEntity(inputStreamRequestEntity);
    }

    public HttpConnector(String url, String body) {
        HttpClientParams httpClientParams = new HttpClientParams();
        httpClientParams.setSoTimeout(20000);
        client = new HttpClient(httpClientParams);
        pm = new PostMethod(url);
        try {
            RequestEntity requestEntity = new StringRequestEntity(body, "application/json ", "utf-8");
            pm.setRequestEntity(requestEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setHeaders(Map<String, String> headers) {
        Iterator<Entry<String, String>> headersMap = headers.entrySet().iterator();
        while (headersMap.hasNext()) {
            Entry<String, String> next = headersMap.next();
            String headerName = next.getKey();
            String headerValue = next.getValue();
            pm.setRequestHeader(headerName, headerValue);
        }
    }

    public void setParam(String key, String value) {
        pm.setParameter(key, value);
    }

    public String send() {
        try {
            int state = client.executeMethod(pm);
            System.out.println(state);
            Header[] responseHeaders = pm.getResponseHeaders();
//                    for(Header h : responseHeaders){
//                        System.out.println("header-key:["+h.getName()+"] header-value:["+h.getValue()+"]");
//                    }
            if (state == HttpStatus.SC_OK) {
                byte[] responseBody = pm.getResponseBody();
                String result = new String(responseBody, "utf-8");
//                        System.out.println("body:["+ result +"]");
                return result;
            }
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
