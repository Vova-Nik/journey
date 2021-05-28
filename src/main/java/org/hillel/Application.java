package org.hillel;

import org.hillel.config.RootConfig;
import org.hillel.config.WebJspConfig;
import org.hillel.config.WebTLConfig;
import org.hillel.filter.CharsetEncodingFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.nio.charset.StandardCharsets;

public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootConfig = new AnnotationConfigWebApplicationContext();
        rootConfig.register(RootConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootConfig));

        AnnotationConfigWebApplicationContext jspAppContext = new AnnotationConfigWebApplicationContext();
        jspAppContext.register(WebJspConfig.class);
        ServletRegistration.Dynamic jspServlet = servletContext.addServlet("jspServlet", new DispatcherServlet(jspAppContext));
        jspServlet.addMapping("/welcome");

        //        ServletRegistration.Dynamic downloadServlet =  servletContext.addServlet("downloadServlet", new DispatcherServlet(jspAppContext));
        //        downloadServlet.addMapping("/download");

        AnnotationConfigWebApplicationContext tlAppContext = new AnnotationConfigWebApplicationContext();
        tlAppContext.register(WebTLConfig.class);
        ServletRegistration.Dynamic tlServlet = servletContext.addServlet("tlServlet", new DispatcherServlet(tlAppContext));
        tlServlet.addMapping("/tl", "/tl/*");

        CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding("UTF-8");
        cef.setForceEncoding(true);
        FilterRegistration.Dynamic charsetFilter = servletContext.addFilter("charsetFilter",cef);
        charsetFilter.addMappingForUrlPatterns(null,true,"/*");

    }

}
