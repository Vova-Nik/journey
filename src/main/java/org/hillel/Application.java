package org.hillel;

import org.hillel.config.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class Application implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext rootConfig = new AnnotationConfigWebApplicationContext();
        rootConfig.register(RootConfig.class);
        rootConfig.register(SecurityConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootConfig));

        AnnotationConfigWebApplicationContext jspAppContext = new AnnotationConfigWebApplicationContext();
        jspAppContext.register(WebJspConfig.class);
        ServletRegistration.Dynamic jspServlet = servletContext.addServlet("jspServlet", new DispatcherServlet(jspAppContext));
        jspServlet.addMapping("/welcome","/rest");

        AnnotationConfigWebApplicationContext tlAppContext = new AnnotationConfigWebApplicationContext();
        tlAppContext.register(WebTLConfig.class);
        ServletRegistration.Dynamic tlServlet = servletContext.addServlet("tlServlet", new DispatcherServlet(tlAppContext));
        tlServlet.addMapping("/tl", "/tl/*");

        AnnotationConfigWebApplicationContext restAppContext = new AnnotationConfigWebApplicationContext();
        restAppContext.register(RestConfig.class);
        ServletRegistration.Dynamic restServlet = servletContext.addServlet("apiServlet", new DispatcherServlet(restAppContext));
        restServlet.addMapping("/api", "/api/*");

        CharacterEncodingFilter cef = new CharacterEncodingFilter();
        cef.setEncoding("UTF-8");
        cef.setForceEncoding(true);
        FilterRegistration.Dynamic charsetFilter = servletContext.addFilter("charsetFilter", cef);
        charsetFilter.addMappingForUrlPatterns(null, true, "/*");

//        DelegatingFilterProxy filter = new DelegatingFilterProxy("springSecurityFilterChain");
//        servletContext.addFilter("springSecurityFilterChain", filter)
//                .addMappingForUrlPatterns(null, true, "/*");
    }

}
