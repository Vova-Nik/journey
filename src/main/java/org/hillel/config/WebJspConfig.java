package org.hillel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("org.hillel.controller.jsp")
public class WebJspConfig {
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver internalResourceViewResolver =  new InternalResourceViewResolver();
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setPrefix("WEB-INF/view/jsp/");
        return internalResourceViewResolver;
    }
}
