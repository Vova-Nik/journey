package org.hillel;

import org.hillel.config.WebJspConfig;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;


public class Application extends AbstractDispatcherServletInitializer {
    AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext;

    @Override
    protected WebApplicationContext createServletApplicationContext() {
        annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(WebJspConfig.class);
        return annotationConfigWebApplicationContext;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/welcome", "/download"};
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }


}
