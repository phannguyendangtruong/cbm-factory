package com.factory.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Log4j2
@Aspect
@Component
public class LoggingAspect {

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @Pointcut("execution(* com.amit.sample.controllers..*(..)))")
    protected void restControllers() {
    }

    @Around("(restControllers())")
    public Object logAspectController(ProceedingJoinPoint pjp) throws Throwable {
        Object output = null;
        String ip = getIPRequest(this.httpServletRequest);
        String headers = header(httpServletRequest);
        log.debug("[REQ] ip : {}; Request uri: {} ;method: {} ;params: {} ;header: {} ",
                ip,
                this.httpServletRequest.getRequestURI(),
                this.httpServletRequest.getMethod(),
                getParams(this.httpServletRequest.getParameterMap()),
                headers);
        output = pjp.proceed();
        return output;
    }

    private String header(HttpServletRequest httpServletRequest) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> x = httpServletRequest.getHeaderNames();
        while (x.hasMoreElements()) {
            String key = x.nextElement();
            sb.append(key + "=" + httpServletRequest.getHeader(key) + ",");
        }
        return sb.toString();
    }


    private String getIPRequest(HttpServletRequest servletRequest) {
        if (servletRequest == null)
            return null;
        String remoteIPAddress = null;
        remoteIPAddress = servletRequest.getHeader("X-FORWARDED-FOR");
        if (remoteIPAddress == null || "".equals(remoteIPAddress)) {
            remoteIPAddress = servletRequest.getRemoteAddr();
        }

        return remoteIPAddress;
    }

    private String getParams(Map<String, String[]> parameterMap) {
        if (parameterMap == null || parameterMap.isEmpty())
            return "No Params";
        StringBuilder sb = new StringBuilder();
        for (String key : parameterMap.keySet()) {
            sb.append(key + "=" + parameterMap.get(key)[0] + ",");
        }
        return sb.toString();
    }

}
