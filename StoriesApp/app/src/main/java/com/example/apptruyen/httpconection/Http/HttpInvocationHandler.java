package com.example.apptruyen.httpconection.Http;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;


import com.example.apptruyen.httpconection.HttpAnotation.Body;
import com.example.apptruyen.httpconection.HttpAnotation.Form;
import com.example.apptruyen.httpconection.HttpAnotation.Header;
import com.example.apptruyen.httpconection.HttpAnotation.HttpMethod;
import com.example.apptruyen.httpconection.HttpAnotation.MethodType;
import com.example.apptruyen.httpconection.HttpAnotation.Query;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpInvocationHandler implements InvocationHandler {
    private final Context context;
    private  String baseUrl;
    private HashMap<String, String> headers;
    Gson gson = new Gson();
    public HttpInvocationHandler(Context context,String baseUrl, HashMap<String, String> headers) {
        this.context = context;
        this.baseUrl=baseUrl;
        this.headers=headers;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy;
            policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        HttpMethod httpMethod = method.getAnnotation(HttpMethod.class);
        Parameter[] parameters = method.getParameters();
        String url = baseUrl + httpMethod.url();

        Header[] __headers = method.getAnnotationsByType(Header.class);

        String query = "";
        String body = null;
        String form = "";
        int i = 0;
        for (Parameter parameter : parameters) {
            String name = parameter.getName();
       //     String pwd=parameter.get
            Object paramValue = args[i];
            Object rqParam = parameter.getAnnotation(Query.class);
            if (rqParam != null) {
                Query p = (Query) rqParam;
                name = p.name();
                query += name + "=" + URLEncoder.encode(paramValue.toString()) + "&";
            } else if ((rqParam = parameter.getAnnotation(Body.class)) != null) {
                Body p = (Body) rqParam;
                name = p.name();
                Gson gson = new Gson();
                body = gson.toJson(paramValue);
            } else if ((rqParam = parameter.getAnnotation(Form.class)) != null) {
                Form p = (Form) rqParam;
                name = p.name();

                form += name + "=" + URLEncoder.encode(paramValue.toString()) + "&";
            }
            i++;
        }
        url = url + "?" + query;
        URL url_conn = new URL(url);
        HttpURLConnection connection;

        if (url_conn.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            HttpsURLConnection https = (HttpsURLConnection) url_conn.openConnection();
            https.setHostnameVerifier(DO_NOT_VERIFY);
            connection = https;
        } else {
            connection = (HttpURLConnection) url_conn.openConnection();
        }

        connection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        if (this.headers != null && this.headers.size() > 0) {
            for (String key : headers.keySet()) {
                connection.addRequestProperty(key, headers.get(key));
            }
        }
        if (__headers != null && __headers.length > 0) {

            for (Header header : __headers) {
                connection.addRequestProperty(header.name(), header.value());
            }
        }
        if (httpMethod.method()== MethodType.GET) {

        }
        if (httpMethod.method() == MethodType.POST) {
            String dataString = null;
            if (form != null && form != "") {
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                dataString = form;
            }
            else if (body != null && body != "") {
                connection.setRequestProperty("Content-Type","application/json");
                dataString = body;
            }
            if (dataString != null) {
                connection.setDoOutput(true);
                OutputStream bufferedWriter = connection.getOutputStream();
                bufferedWriter.write(dataString.getBytes());
                bufferedWriter.close();

            }
        }

        connection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        Log.d("url",url);
        Log.d("response", stringBuilder.toString());
        return gson.fromJson(stringBuilder.toString(), method.getReturnType());

    }// always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
