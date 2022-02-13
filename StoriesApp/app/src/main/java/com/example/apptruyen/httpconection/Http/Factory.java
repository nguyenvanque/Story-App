package com.example.apptruyen.httpconection.Http;

import android.content.Context;

import java.lang.reflect.Proxy;
import java.util.HashMap;

public class Factory {
    @SuppressWarnings("unchecked")
    public static <T> T create(Context context, Class<T> type, String baseUrl, HashMap<String, String> headers) throws InstantiationException, IllegalAccessException {

        HttpInvocationHandler handler = new HttpInvocationHandler(context,baseUrl,headers);
        T obj = (T) Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{ type }, handler);
        return obj;
    }
}
