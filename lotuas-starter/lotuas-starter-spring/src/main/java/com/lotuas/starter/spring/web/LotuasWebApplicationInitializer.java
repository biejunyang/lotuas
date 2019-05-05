package com.lotuas.starter.spring.web;

import com.lotuas.starter.spring.web.config.WebConfig;
import org.springframework.util.ClassUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.security.auth.login.AppConfigurationEntry;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class LotuasWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        try {


            Class.forName("com.lotuas.starter.spring.web.SystemContext");

            // Load Spring web application configuration
            AnnotationConfigWebApplicationContext webAppCtx = new AnnotationConfigWebApplicationContext();
            webAppCtx.register(WebConfig.class);
            webAppCtx.refresh();

            // Create and register the DispatcherServlet
            ServletRegistration.Dynamic dispatchServlet = servletContext.addServlet("dispatchServlet", new DispatcherServlet(webAppCtx));
            dispatchServlet.setLoadOnStartup(1);
            dispatchServlet.setAsyncSupported(true);
            dispatchServlet.addMapping("/");


            AnnotationConfigWebApplicationContext rootAppCtx = new AnnotationConfigWebApplicationContext();
            rootAppCtx.register(AppConfigurationEntry.class);
            rootAppCtx.refresh();
            servletContext.addListener(new ContextLoaderListener(rootAppCtx));



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
