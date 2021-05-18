package org.hillel.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalTime;
import java.util.Objects;

//https://www.baeldung.com/spring-requestmapping

@Controller
public class WelcomeController {

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("thetime", LocalTime.now().toString());
        return "welcome";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void getImageAsByteArray(HttpServletResponse response) throws IOException {
        InputStream in = servletContext.getResourceAsStream("/WEB-INF/img/1.jpg");
        if (Objects.isNull(in)) {
            response.setContentType(MediaType.TEXT_HTML_VALUE);
            String resp = "<html><h1 style=\"color: darkred;\">Internal server error<h1><h2>Unable to find file 1.jpg</h2></html>";
            in = IOUtils.toInputStream(resp);
            IOUtils.copy(in, response.getOutputStream());
            return;
        }
        BufferedInputStream inputStream = new BufferedInputStream(in);
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        //        response.setContentType("text/plain");
        //        response.setHeader("Content-disposition", "attachment; 1.jpg");

        IOUtils.copy(inputStream, outputStream);
        IOUtils.close(inputStream);
        IOUtils.close(outputStream);
    }
}
