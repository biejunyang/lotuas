package com.lotuas.common.scan;

import org.junit.Test;
import org.springframework.util.ClassUtils;
import org.springframework.web.WebApplicationInitializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ClassResourceScanerTest {

    private ClassResourceScaner scaner=new ClassResourceScaner();


    @Test
    public void test1() throws IOException {
        List<Class> results=scaner.doScan("com/lotuas/**");
        for(Class clz: results){
            System.out.println(clz.getName());
        }


    }


    @Test
    public void test2() throws IOException {

        List<Class> results=scaner.doScan(ClassResourceScaner.DEFAULT_RESOURCE_PATTER_PREFIX+"com/lotuas/**");
        for(Class clz: results){
            System.out.println(clz.getName());
        }

    }


    @Test
    public void test3() throws IOException {

        List<Class> results=scaner.findPackageClasses(Arrays.asList("com.lotuas", "com.lotuas.common"));
        for(Class clz: results){
            System.out.println(clz.getName());
        }

    }

    @Test
    public void test4() throws IOException {
        List<Class> results=scaner.findPackageClasses(Arrays.asList("com.lotuas", "com.lotuas.common"), new ResourceScanRule<Class>() {
            @Override
            public boolean validate(Class resource) {
                Class<?>[] interfaces= ClassUtils.getAllInterfacesForClass(resource);
                if(Arrays.asList(interfaces).contains(WebApplicationInitializer.class)){
                    return true;
                }
                return false;
            }
        });


        for(Class clz: results){
            System.out.println(clz.getName());
        }

    }
}
