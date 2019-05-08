package com.lotuas.common.scan;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractResourceScaner<T> implements ResourceScaner<T>, ResourceConverter<T>{


    /**
     * PathMatchingResourcePatternResolver是一个支持Ant模式通配符的Resource查找器，可以用来查找类路径下或者文件系统中的资源。
     * 可以查找出符合路径模式下的一个或者多个资源，包括类路径下jar文件中的资源。查找路径支持三种格式：
     *      单个路径:com/lotuas
     *      ant格式路径：*匹配0个或多个字符，?匹配单个字符，**匹配0个或多个路径
     *      classpath*开头的路径，包含类路径下的jar文件中的资源，没有classpath则表示当前项目下的类路径下
     *      ResouceUrl格式：如file//:E:/gitwo/*
     */
    protected PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver=new PathMatchingResourcePatternResolver();


    /**
     * 扫描资源
     * @param scanPath：扫描路径
     * @return
     * @throws IOException
     */
    @Override
    public List<T> doScan(String scanPath) throws IOException {
        return this.doScan(scanPath, null);
    }

    public List<T> doScan(String scanPath, ResourceScanRule<T> rule) throws IOException {
        Resource[] resources=pathMatchingResourcePatternResolver.getResources(scanPath);
        List<T> results=new ArrayList<>();
        if(resources!=null && resources.length>0){
            for(Resource resource: resources){
                T result=convert(resource);
                if(result!=null){
                    if(rule!=null){
                        if(rule.validate(result)){
                            results.add(result);
                        }
                    }else{
                        results.add(result);
                    }
                }
            }
        }
        return results;
    }



}
