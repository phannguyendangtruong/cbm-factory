package com.amitgroup.services;

import lombok.extern.log4j.Log4j2;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Map;

/**
 * Velocity service.
 */
@Log4j2
public class VelocityService {
    private final VelocityEngine engine;

    public VelocityService(VelocityEngine engine) {
        this.engine = engine;
    }

    public String mergeTemplate(String templatePath, Map<String, Object> model) {

        try {
            VelocityContext context = new VelocityContext(model);
            StringWriter writer = new StringWriter();
            engine.mergeTemplate(templatePath, "UTF-8", context, writer);
            String result = writer.toString();
            writer.close();
            return result;
        } catch (Exception e) {
            log.error("load template fail ", e);
        }

        return templatePath;
    }

}