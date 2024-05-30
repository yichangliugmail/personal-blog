package com.lyc.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;


public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * post请求方式
     *
     * @param url
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String doPost(String url) throws Exception {
        HttpsURLConnection conn = null;
        StringBuilder result = new StringBuilder();
        try {
            SSLContext sc = createSslContext();
            conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier((s, sslSession) -> true);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.connect();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while (null != (line = br.readLine())) {
                    result.append(line).append("\n");
                }
            } catch (Exception e) {
                //logger.error(">>> 返回结果处理异常！！{}", e);
                throw e;
            }
        } catch (Exception e) {
            //logger.error(">>> 接口调用异常！{}", e);
            throw e;
        } finally {
            conn.disconnect();
        }
        return result.toString();
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPostHttps(String url, String params, String authorization) throws Exception {
        OutputStream out = null;
        HttpsURLConnection conn = null;
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            logger.info(">>>> url: {}", url);
            SSLContext sc = createSslContext();
            conn = (HttpsURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier((s, sslSession) -> true);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestMethod("POST");
            /**
             *  设置请求头
             *  踩坑指南之 https请求POST请求之带参数请求
             *  绿盟接口调用：入参格式 name=shsnc_test_20231204&targets=133.0.184.22&template_id=0
             *  需要使用 该内容类型：application/x-www-form-urlencoded
             *  而非 application/json
             */

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", authorization);

            // 开始连接请求
            conn.connect();

            if (StringUtils.isNotBlank(params)) {
                logger.info(">>>> params: {}", params);
                out = conn.getOutputStream();
                out.write(params.getBytes("utf-8"));
                // 刷新
                out.flush();
                out.close();
            }
            try {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while (null != (line = br.readLine())) {
                    result.append(line).append("\n");
                }
            } catch (Exception e) {
                //logger.error(">>> 返回结果处理异常！！{}", e);
                throw e;
            } finally {
                br.close();
            }
        } catch (Exception e) {
            //logger.error(">>> 接口调用异常！{}", e);
            throw e;
        } finally {
            conn.disconnect();
        }
        return result.toString();
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url    url
     * @param params json字符串
     * @return 返回结果
     */
    public static String doPost(String url, String params, Map<String, String> header) throws IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            // 创建http
            httpclient = HttpClients.createDefault();

            // 创建httpPost
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            if (null != header && header.keySet().size() > 0) {
                header.forEach((K, V) -> {
                    httpPost.setHeader(K, V);
                });
            }
            StringEntity entity = new StringEntity(params, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
        } catch (IOException e) {
            //logger.error(">>>> 带参数post接口请求异常！{}", e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(">>>> response关闭异常！{}", e);
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(">>>> httpclient关闭异常！{}", e);
            }
        }
        return null;
    }

    /**
     * post请求（用于请求json格式的参数），任何情况均返回
     *
     * @param url    url
     * @param params json字符串
     * @return 返回结果
     */
    public static String doPostReturnAny(String url, String params, Map<String, String> header) throws IOException {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            // 创建http
            httpclient = HttpClients.createDefault();

            // 创建httpPost
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            if (null != header && header.keySet().size() > 0) {
                header.forEach((K, V) -> {
                    httpPost.setHeader(K, V);
                });
            }
            StringEntity entity = new StringEntity(params, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            HttpEntity responseEntity = response.getEntity();
            String jsonString = EntityUtils.toString(responseEntity);
            return jsonString;
        } catch (IOException e) {
            //logger.error(">>>> 带参数post接口请求异常！{}", e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(">>>> response关闭异常！{}", e);
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(">>>> httpclient关闭异常！{}", e);
            }
        }
    }


    private static SSLContext createSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSL");

        sc.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new java.security.SecureRandom());

        return sc;
    }

    public static void main(String[] args) {
        // 接口功能验证
//        Map<String, Object> body = new HashMap<>();
//        Map<String, Object> param = new HashMap<>();
//        Map<String, Object> condition = new HashMap<>();
//        condition.put("mainId", 1149);
//        param.put("condition", condition);
//        body.put("params", param);
//        Map<String, String> header = new HashMap<>();
//        header.put("Snc-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySW5mbyI6IntcInVzZXJJZFwiOjEsXCJ1c2VybmFtZVwiOlwiYWRtaW5cIixcImFsaWFzXCI6XCJhZG1pblwiLFwibW9iaWxlXCI6XCIxODg4ODg4ODg4XCIsXCJlbWFpbFwiOlwiXCIsXCJzdGF0dXNcIjoxLFwiZ3JvdXBJZHNcIjpbMSwyLDQsNV0sXCJzdXBlckFkbWluXCI6dHJ1ZSxcInN1cGVyR3JvdXBcIjpmYWxzZSxcInJlc291cmNlR3JvdXBJZHNcIjpbMTAwLDEwMSwxMDQsMTE2LDExNywxMTgsMTE5LDIwMl0sXCJyb2xlc1wiOlt7XCJyb2xlSWRcIjoyLFwicm9sZU5hbWVcIjpcIuS4iua1t-aWsOeCrFwiLFwicm9sZUNvZGVcIjpcInNoc25jXCIsXCJpc0J1aWx0XCI6MSxcImRlc2NyaXB0aW9uXCI6XCLkuIrmtbfmlrDngqxcIixcImNyZWF0ZVRpbWVcIjoyMDIwMDcyMTIyNTgzOSxcIm9yZGVyc1wiOjF9XX0iLCJ1dWlkIjoiNTlERTc5RUM5OEI2NDFCNEJDMzcwNDk2REZBMjNBNjUiLCJ0aW1lc3RhbXAiOjE3MDEyMjE2OTIzNTR9.DLuNS3m4M2NivxKuVM4AA7NZXqJAfQJ5wVJ5EAQGTWI");
//        try {
//            logger.info(">>>>>> param: {}", JSONObject.toJSONString(body));
//            String resultStr = doPost("http://133.0.184.23:8080/snc-sti/gradingRecord/main/queryDataByMainId", JSONObject.toJSONString(body), header);
//            logger.info(">>>>>> resultStr: {}", resultStr);
//
//            resultStr = doPost("http://133.0.184.23:8080/snc-sti/file/download?fileId=1233&fullfilename=1149_2020_销售门户系统_定级报告.docx");
//            logger.info(">>>>>> resultStr: {}", resultStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //验证获取用户信息
        String url= "http://133.0.184.20:10011/openapi/v1/user/getByName";
        String param = String.format("{\"params\":{\"userName\":\"shsnc_liuyc\"}}");
        Map<String, String> header = new HashMap<>();
        header.put("access-key", "fa404159-c134-43af-860f-b86a87d4681d");
        String s = null;
        try {
            s = doPostReturnAny(url, param, header);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info(s);


    }

}
