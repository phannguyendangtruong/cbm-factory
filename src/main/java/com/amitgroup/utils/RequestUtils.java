package com.amitgroup.utils;

import org.springframework.web.context.request.WebRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    public static String getRequestDescription(WebRequest webRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(webRequest);

        stringBuilder.append("REQUEST ");
        stringBuilder.append("path=[").append(webRequest.getDescription(true)).append("] ");

        if (!parameters.isEmpty()) {
            stringBuilder.append("parameters=[").append(parameters).append("] ");
        }

        if (body != null) {
            stringBuilder.append("body=[" + body + "]");
        }

        return stringBuilder.toString();
    }

    private static Map<String, String> buildParametersMap(WebRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        httpServletRequest.getParameterNames();
        try {
            Enumeration<String> parameterNames = (Enumeration<String>) httpServletRequest.getParameterNames();

            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                String value = httpServletRequest.getParameter(key);
                resultMap.put(key, value);
            }

            return resultMap;
        } catch (Exception e){
            return new HashMap<>();
        }

    }

    private static Map<String, String> buildHeadersMap(WebRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = (Enumeration) request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
}
