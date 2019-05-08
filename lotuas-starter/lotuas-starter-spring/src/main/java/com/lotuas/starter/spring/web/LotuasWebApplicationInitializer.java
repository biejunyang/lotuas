package com.lotuas.starter.spring.web;

import com.lotuas.common.reflect.util.ReflectUtil;
import com.lotuas.starter.spring.web.config.AppConfig;
import com.lotuas.starter.spring.web.config.WebConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class LotuasWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private final static Log log = LogFactory.getLog(LotuasWebApplicationInitializer.class);


    private static Map<String, String> sysProperties= new HashMap<>();

    private static String[] BASE_PACKAGES;


    @Override
    public void onStartup(ServletContext webContainer) throws ServletException {
        try {
            List<String> customWebApplicationInitializer=loadCustomerWebApplicationInitializer();
            if(CollectionUtils.isEmpty(customWebApplicationInitializer)){
                super.onStartup(webContainer);
            }else{
                for(String initializerName: customWebApplicationInitializer){
                    WebApplicationInitializer initializer = (WebApplicationInitializer) Class.forName(initializerName).newInstance();
                    initializer.onStartup(webContainer);
                }
            }
            SystemContext.setServletContext(webContainer);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("web application initializer start up failed!!!!");
        }
    }


    @Override
    protected Class<?>[] getRootConfigClasses() {
        ComponentScan anno=AppConfig.class.getAnnotation(ComponentScan.class);
        ReflectUtil.setAnnotaionProperty(anno, "basePackages", BASE_PACKAGES);
        return new Class[]{AppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        ComponentScan anno=WebConfig.class.getAnnotation(ComponentScan.class);
        ReflectUtil.setAnnotaionProperty(anno, "basePackages", BASE_PACKAGES);
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected String getServletName() {
        return "spring-dispatch";
    }


    /**
     * 添加过滤器
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        return super.getServletFilters();
    }


    /**
     * dispatch-servlet spring上下文初始化加载器
     * @return
     */
    @Override
    protected ApplicationContextInitializer<?>[] getServletApplicationContextInitializers() {
        return super.getServletApplicationContextInitializers();
    }

    /**
     * 是否支持请求异步处理，默认true
     * @return
     */
    @Override
    protected boolean isAsyncSupported() {
        return super.isAsyncSupported();
    }


    /**
     * web root spring上下文初始化加载器
     * @return
     */
    @Override
    protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
        return super.getRootApplicationContextInitializers();
    }


    /**
     * 从系统根路径下加载自定义的WebApplicationInitializer初始化器
     * @return
     */
    private List<String> loadCustomerWebApplicationInitializer() throws IOException, ClassNotFoundException {

        ResourcePatternResolver resourceResolver=new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceResolver);
        //当前项目的根路径
        Resource[] rootResources=resourceResolver.getResources("*");
        if(rootResources==null && rootResources.length <= 0){
            return Collections.EMPTY_LIST;
        }
        List<String> customerWebApplicationInitializer=new ArrayList<>();
        for(Resource res: rootResources){
            if(ResourceUtils.isFileURL(res.getURL())){
                File file=res.getFile();
                if(file.isDirectory()){
                    String packageSearchPath=res.getURL().toString()+"**/*.class";
                    Resource[] resources = resourceResolver.getResources(packageSearchPath);
                    for (Resource resource : resources) {
                        ClassMetadata classMetadata=metadataReaderFactory.getMetadataReader(resource).getClassMetadata();
                        Class<?> clazz=Class.forName(classMetadata.getClassName());
                        Class<?>[] interfaces=ClassUtils.getAllInterfacesForClass(clazz);
                        if(Arrays.asList(interfaces).contains(WebApplicationInitializer.class)){
                            customerWebApplicationInitializer.add(classMetadata.getClassName());
                        }
                    }

                }else if(file.getName().endsWith(".class")){
                    ClassMetadata classMetadata=metadataReaderFactory.getMetadataReader(res).getClassMetadata();
                    Class<?> clazz=Class.forName(classMetadata.getClassName());
                    Class<?>[] interfaces=ClassUtils.getAllInterfacesForClass(clazz);
                    if(Arrays.asList(interfaces).contains(WebApplicationInitializer.class)){
                        customerWebApplicationInitializer.add(classMetadata.getClassName());
                    }
                }
            }
        }
        return customerWebApplicationInitializer;
    }

    static {
        try{
            //系统配置文件读取
            String scanPackage=null;
            Properties sysProps=new Properties();
            InputStream sysIn=Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            if(sysIn!=null){
                sysProps.load(sysIn);
                for(Map.Entry<Object, Object> en: sysProps.entrySet()){
                    sysProperties.put(String.valueOf(en.getKey()), String.valueOf(en.getValue()));
                    if(en.getKey().toString().equals("lotuas.componentScan.basePackage")){
                        scanPackage=en.getValue().toString();
                    }
                }
            }

            //获取Spring ComponetScan扫描包路径
            if(StringUtils.isEmpty(scanPackage)){
                ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
                Resource[] rootResources = resourceResolver.getResources("*");
                List<String> basePackages=new ArrayList<>();
                for (Resource res : rootResources) {
                    if (ResourceUtils.isFileURL(res.getURL()) && res.getFile().isDirectory()) {
                        String url = res.getURL().toString();
                        url=url.endsWith("/") ? url.substring(0,url.length()-1) : url;
                        String basePackage = url.substring(url.lastIndexOf("/") + 1);
                        basePackages.add(basePackage);
                    }
                }
                if(!CollectionUtils.isEmpty(basePackages)){
                    BASE_PACKAGES=basePackages.toArray(new String[]{});
                }
            }else{
                BASE_PACKAGES=scanPackage.split(",");
            }
            Class.forName("com.lotuas.starter.spring.web.SystemContext");
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Load LotuasWebApplicationInitializer failed!!!");
        }

    }

}
