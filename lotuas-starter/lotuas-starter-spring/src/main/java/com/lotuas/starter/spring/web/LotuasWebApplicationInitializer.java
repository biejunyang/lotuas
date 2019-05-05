package com.lotuas.starter.spring.web;

import com.lotuas.starter.spring.web.config.AppConfig;
import com.lotuas.starter.spring.web.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class LotuasWebApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext webContainer) throws ServletException {
        try {
            Class.forName("com.lotuas.starter.spring.web.SystemContext");

            //create spring mvc application context
            AnnotationConfigWebApplicationContext mvcCtx=new AnnotationConfigWebApplicationContext();
            //load spring mvc configuration
            mvcCtx.register(WebConfig.class);


            //register dispatch servlet
            ServletRegistration.Dynamic dispatchServlet=
                    webContainer.addServlet("springMVC", new DispatcherServlet(mvcCtx));
            dispatchServlet.setLoadOnStartup(1);
            dispatchServlet.setAsyncSupported(true);
            dispatchServlet.addMapping("/");


            AnnotationConfigWebApplicationContext appCtx=new AnnotationConfigWebApplicationContext();
            appCtx.register(AppConfig.class);//load spring application configuration
            webContainer.addListener(new ContextLoaderListener(appCtx));



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
