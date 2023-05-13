package com.factory.aop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.factory.models.BasePaginationResponse;
import com.factory.models.BaseResponse;
import com.factory.models.ShareConstants;
import com.factory.services.LoggingService;

@ControllerAdvice
public class CustomResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    @Autowired
    LoggingService loggingService;

    @Autowired
    Tracer tracer;

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (o instanceof BaseResponse){
            if (ShareConstants.ApplicationSetting.IS_ENABLE_RESPONSE_TRACE_ID){
                BaseResponse _o = (BaseResponse) o;
                Span span = tracer.currentSpan();
                if (span != null){
                    _o.setTraceId(span.context().traceId());
                }
                o = _o;
            }
        }


        if (o instanceof BasePaginationResponse){
            if (ShareConstants.ApplicationSetting.IS_ENABLE_RESPONSE_TRACE_ID){
                BasePaginationResponse _o = (BasePaginationResponse) o;
                Span span = tracer.currentSpan();
                if (span != null){
                    _o.setTraceId(span.context().traceId());
                }
                o = _o;
            }
        }

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {
            loggingService.logResponse(
                    ((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                    ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }

        return o;
    }
}