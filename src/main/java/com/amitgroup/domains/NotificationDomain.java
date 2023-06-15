package com.amitgroup.domains;


import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.amitgroup.models.ShareConstants;
import com.amitgroup.services.VelocityService;
import com.amitgroup.services.email.MailService;
import com.amitgroup.utils.HTMLUtils;
import com.amitgroup.utils.RequestUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class NotificationDomain {

    @Value("${com.amit.notification.email.receiver:amitgroup.bug@yopmail.com}")
    private String errorReceiver;

    @Value("${com.amit.notification.email.title:ERROR}")
    private String errorTitle;

    @Autowired
    protected MailService mailService;

    @Autowired
    protected VelocityService velocityService;

    @Autowired
    Tracer tracer;

    public void sendErrorEmail(WebRequest request, Object data, String message, StringBuilder stackTraceSBD) {
        Span span = tracer.currentSpan();
        String traceId = "";
        String description = "";
        try {
            description = RequestUtils.getRequestDescription(request, data);
        } catch (Exception e1){
            e1.printStackTrace();
            description = request.getDescription(true);
        }

        if (span != null){
            traceId = span.context().spanId();
        }

        Map<String, Object> model = new HashMap<>();
        model.put("trace", traceId);
        model.put("description", description);
        model.put("message", message);
        model.put("stackTrace", HTMLUtils.normalizeText(stackTraceSBD.toString()));
        String body = velocityService.mergeTemplate(ShareConstants.Template.ERROR_EMAIL, model);

        if (!body.equals(ShareConstants.Template.ERROR_EMAIL)) {
            try {
                mailService.sendSimpleMessage(errorReceiver, errorTitle, body, true);
            } catch (Exception e) {
                log.warn("send email to {} failed", errorReceiver, e);
            }
        }
    }

}
