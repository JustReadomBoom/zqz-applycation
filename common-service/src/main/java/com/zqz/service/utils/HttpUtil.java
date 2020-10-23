package com.zqz.service.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=UTF-8");//json串数据

    private static OkHttpClient client = new OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory(), x509TrustManager())
            .retryOnConnectionFailure(false)// 是否开启缓存
            .connectionPool(pool())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .hostnameVerifier((hostname, session) -> true)
            /*.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888)))// 设置代理
            .addInterceptor()// 拦截器*/
            .build();

    @Bean
    public static ConnectionPool pool() {
        return new ConnectionPool(200, 300, TimeUnit.SECONDS);
    }

    @Bean
    private static SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL"); // 信任任何链接
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    private static X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    /**
     * post方式发送json串
     */
    public static String postJson(String url, Map<String, String> headers, String json) {
        Response response = null;
        try {
            //创建请求地址
            Request.Builder builder = new Request.Builder().url(url);

            //创建请求头
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
            }

            //创建RequestBody请求体
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, json);

            //创建Request对象
            Request request = builder.post(requestBody).build();

            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseStr = response.body().string();
                return responseStr;
            } else {
                log.error(">>>>>>发送http-post请求返回错误，code[{}]：response[{}]", response.code(), response.toString());
                return null;
            }
        } catch (Exception e) {
            log.error(">>>>>>发送http-post请求异常", e);
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }


    /**
     * @Author: zqz
     * @Description: post请求封装 参数为?a=1&b=2&c=3， 例如info: city=Beijing&name=123
     * @Param: [path, Info]
     * @Return: com.alibaba.fastjson.JSONObject
     * @Date: 11:52 AM 2020/7/31
     */
    public static String postResponse(String path, String Info) {
        try {
            //1, 得到URL对象
            URL url = new URL(path);
            //2, 打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //3, 设置提交类型
            conn.setRequestMethod("POST");
            //4, 设置允许写出数据,默认是不允许 false
            conn.setDoOutput(true);
            conn.setDoInput(true);//当前的连接可以从服务器读取内容, 默认是true
            //5, 获取向服务器写出数据的流
            OutputStream os = conn.getOutputStream();
            //参数是键值队  , 不以"?"开始
            os.write(Info.getBytes());
            os.flush();
            //6, 获取响应的数据
            //得到服务器写回的响应数据
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String str = br.readLine();
            log.info(">>>>>>发送http-post请求返回:[{}]", str);
            return str;
        }catch (Exception e){
            log.error(">>>>>>发送http-post请求异常:[{}]", e.getMessage(), e);
            return null;
        }
    }

}
