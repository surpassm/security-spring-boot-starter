package com.github.surpassm.common.tool.okhttp;

import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
public class OkhttpUtils {

    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                .hostnameVerifier((hostname, session) -> true)
                .build();
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    /**
     * get请求
     *
     * @param url url
     * @return Response
     * @throws IOException
     */
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();

    }

    public static Response delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        return client.newCall(request).execute();

    }

    public static Response post(String url, Map<String, String> params) throws IOException {
        FormBody.Builder b = new FormBody.Builder();
        params.forEach(
                (k, v) -> {
                    if (v != null) {
                        b.add(k, v);
                    }
                });
        RequestBody body = b.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static Response postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .header("Content-Type", "application/json;charset=UTF-8")
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public static Response patch(String url, Map<String, String> params) throws IOException {
        FormBody.Builder b = new FormBody.Builder();
        params.forEach((k, v) -> b.add(k, v));
        RequestBody body = b.build();
        Request request = new Request.Builder()
                .url(url)
                .patch(body)
                .build();
        return client.newCall(request).execute();
    }


    public static Response postList(String url, List<Map<String, String>> paramsList) throws IOException {
        FormBody.Builder b = new FormBody.Builder();
        paramsList.forEach(i -> i.forEach((k, v) -> b.add(k, v)));
        RequestBody body = b.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    public static Response getIp(String ip) throws IOException {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=%s";
        Request request = new Request.Builder()
                .url(String.format(url, ip))
                .build();
        return client.newCall(request).execute();
    }
}
