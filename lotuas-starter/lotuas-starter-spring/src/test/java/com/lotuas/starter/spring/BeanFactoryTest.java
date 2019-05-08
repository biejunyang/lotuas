package com.lotuas.starter.spring;

import com.lotuas.common.scan.ClassResourceScaner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BeanFactoryTest {

    public static void main(String[] args) throws IOException {

        ClassResourceScaner classResourceSacner=new ClassResourceScaner();
        List<Class> results=classResourceSacner.doScan("com/lotuas/**");
        for(Class clz: results){
            System.out.println(clz.getName());
        }

        System.out.println("=========================");
        results=classResourceSacner.doScan(ClassResourceScaner.DEFAULT_RESOURCE_PATTER_PREFIX+"com/lotuas/**");
        for(Class clz: results){
            System.out.println(clz.getName());
        }

        System.out.println("=========================");
        results=classResourceSacner.findPackageClasses(Arrays.asList("com.lotuas", "com.lotuas.common"));
        for(Class clz: results){
            System.out.println(clz.getName());
        }
        System.out.println("=========================");


//        System.out.println(System.getProperty("java.class.path"));
//        ResourcePatternResolver resourceResolver=new PathMatchingResourcePatternResolver();
//        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceResolver);
//        List<String> customerWebApplicationInitializer=new ArrayList<>();
//
//
//        //当前项目的根路径
//        Resource[] rootResources=resourceResolver.getResources("*");
//        for(Resource res: rootResources){
//            if(ResourceUtils.isFileURL(res.getURL())){
//                File file=res.getFile();
//                if(file.isDirectory()){
//                    String packageSearchPath=res.getURL().toString()+"**/*.class";
//                    Resource[] resources = resourceResolver.getResources(packageSearchPath);
//                    for (Resource resource : resources) {
//                        ClassMetadata classMetadata=metadataReaderFactory.getMetadataReader(resource).getClassMetadata();
//                        Class<?> clazz=Class.forName(classMetadata.getClassName());
//                        Class<?>[] interfaces=ClassUtils.getAllInterfacesForClass(clazz);
//                        if(Arrays.asList(interfaces).contains(WebApplicationInitializer.class)){
//                            customerWebApplicationInitializer.add(classMetadata.getClassName());
//                        }
//                    }
//
//                }else if(file.getName().endsWith(".class")){
//                    ClassMetadata classMetadata=metadataReaderFactory.getMetadataReader(res).getClassMetadata();
//                    Class<?> clazz=Class.forName(classMetadata.getClassName());
//                    Class<?>[] interfaces=ClassUtils.getAllInterfacesForClass(clazz);
//                    if(Arrays.asList(interfaces).contains(WebApplicationInitializer.class)){
//                        customerWebApplicationInitializer.add(classMetadata.getClassName());
//                    }
//                }
//            }
//        }
//        System.out.println(customerWebApplicationInitializer);

    }


}
