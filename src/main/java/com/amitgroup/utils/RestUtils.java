package com.amitgroup.utils;

import com.amitgroup.models.ShareConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RestUtils {
    public static String getTokenFromHeader(HttpServletRequest request) {
        String token =  request.getHeader(ShareConstants.Header.TOKEN_HEADER);

        if (StringUtils.isEmpty(token)){
            return null;
        }

        token = token.replaceFirst("(?i)" + ShareConstants.Header.TOKEN_PREFIX, "");
        return  token.replace(" " , "");
    }

    public static String getIPRequest(HttpServletRequest servletRequest) {
        if (servletRequest == null){
            return null;
        }

        String remote_iPAddress = servletRequest.getHeader("X-FORWARDED-FOR");
        if (remote_iPAddress == null || "".equals(remote_iPAddress)) {
            remote_iPAddress = servletRequest.getRemoteAddr();
        }

        return remote_iPAddress;
    }

    public static String getRootIPRequest(HttpServletRequest servletRequest) {
        String remote_iPAddress = getIPRequest(servletRequest);

        if (remote_iPAddress.contains(",")){
            remote_iPAddress = remote_iPAddress.split(",")[0];
        }

        return remote_iPAddress;
    }

    public static String getDeviceId(HttpServletRequest servletRequest) {
        if (servletRequest == null)
            return null;
        return servletRequest.getHeader(ShareConstants.Header.DEVICE_Id);
    }


}
