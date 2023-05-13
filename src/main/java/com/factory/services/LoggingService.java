package com.factory.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Log4j2
public class LoggingService {

    @Value("${com.amit.log.uri.ignore:}")
    private String[] ignores;

    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        if (httpServletRequest.getRequestURI().contains("medias")) {
            return;
        }
        StringBuilder data = new StringBuilder();
        data.append("[BODY REQUEST]: ").append(body);

        if (ignores.length > 0){
            for (String uri : ignores){
                if (uri.equalsIgnoreCase(httpServletRequest.getRequestURI())){
                    data.append("[BODY REQUEST]: This uri in log.uri.ignore, it will be skip the log");
                    log.debug(data.toString());
                    return;
                }
            }
        }
        log.debug(data.toString());
    }

    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        if (httpServletRequest.getRequestURI().contains("medias")) {
            return;
        }
        StringBuilder data = new StringBuilder();
        data.append("[BODY RESPONSE]: ").append(body);

        if (ignores.length > 0){
            for (String uri : ignores){
                if (uri.equalsIgnoreCase(httpServletRequest.getRequestURI())){
                    data.append("[BODY RESPONSE]: This uri in log.uri.ignore, it will be skip the log");
                    log.debug(data.toString());
                    return;
                }
            }
        }
        log.debug(data.toString());
    }
}