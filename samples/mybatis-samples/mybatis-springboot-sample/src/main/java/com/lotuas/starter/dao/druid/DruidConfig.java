package com.lotuas.starter.dao.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@ConfigurationProperties(prefix = "druid")
public class DruidConfig {


    private final static String DRUDI_CONFIG_PREFIX="spring.datasource";


    /**
     *druid数据源配置
     * @return
     */
    // 加载配置文件属性，注入到Bean的属性中
    @ConfigurationProperties(prefix = DRUDI_CONFIG_PREFIX)
    @Bean(initMethod = "init", destroyMethod = "close")
    @Primary
    public DruidDataSource druidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        return dataSource;
    }


    /**
     * druid监控后台请求配置
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean servletRegistrationBean=new ServletRegistrationBean(new StatViewServlet(),
                "/druid/*");
//        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
//        servletRegistrationBean.addInitParameter("deny", "127.9.991.11");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 配置WebStataFilter：统计相关的web应用信息，在监控后台界面显示
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico," +
                "/druid/*");
        return filterRegistrationBean;
    }

}
