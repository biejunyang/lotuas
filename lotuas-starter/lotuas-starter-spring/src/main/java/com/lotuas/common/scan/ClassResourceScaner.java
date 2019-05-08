package com.lotuas.common.scan;

import com.lotuas.common.scan.exception.ResourceConvertException;
import com.lotuas.common.scan.exception.ResourceScanException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.*;


/**
 * class资源扫描器
 */
public class ClassResourceScaner extends AbstractResourceScaner<Class> {

    private final static Log log = LogFactory.getLog(ClassResourceScaner.class);
    /**
     * 扫描路径必须以.class结束
     */
    public static String RESOURCE_PATTERN_SUFFIX=".class";

    /**
     * 从类路径下获取的默认前缀
     */
    public static String DEFAULT_RESOURCE_PATTER_PREFIX="classpath*:";


    /**
     * pring中用来读取resource为class的工具
     */
    private MetadataReaderFactory metadataReaderFactory;



    public ClassResourceScaner(){
        this.metadataReaderFactory= new CachingMetadataReaderFactory(super.pathMatchingResourcePatternResolver);
    }

    @Override
    public List<Class> doScan(String scanPath, ResourceScanRule<Class> rule) throws IOException {
        if(!scanPath.endsWith(".class")){
            scanPath=scanPath.endsWith("/") ? scanPath : scanPath+"/";
            scanPath+="*"+RESOURCE_PATTERN_SUFFIX;
        }
        return super.doScan(scanPath, rule);
    }

    @Override
    public Class convert(Resource resource) throws ResourceConvertException {
        try {
            if(resource==null) {
                throw new ResourceConvertException("Convert resource can not be Null!!!");
            }
            if(!resource.getURL().toString().endsWith(".class")) {
                throw new ResourceConvertException("The convert resource is not .class File, Can not to be converted to Class Type");
            }
            ClassMetadata classMetadata= metadataReaderFactory.getMetadataReader(resource).getClassMetadata();
            if(classMetadata!=null && !StringUtils.isEmpty(classMetadata.getClassName())){
                Class<?> clazz=Class.forName(classMetadata.getClassName());
                return clazz;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceConvertException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ResourceConvertException();
        }
    }


    /**
     * 获取某个包所有下级包中的类
     * @param basePackage
     * @return
     */
    public List<Class> findPackageClasses(String basePackage){
        if(StringUtils.isEmpty(basePackage)) {
            return null;
        }
        return findPackageClasses(Arrays.asList(basePackage), null);
    }

    /**
     * 获取某个包所有下级包中的类
     * @param basePackage
     * @return
     */
    public List<Class> findPackageClasses(String basePackage, ResourceScanRule<Class> rule){
        if(StringUtils.isEmpty(basePackage)) {
            return null;
        }
        return findPackageClasses(Arrays.asList(basePackage), rule);
    }




    /**
     * 获取多个包所有下级包中的类
     * @param basePackages
     * @return
     */
    public List<Class> findPackageClasses(List<String> basePackages) {
        return findPackageClasses(basePackages, null);
    }

    /**
     * 获取多个包所有下级包中的类
     * @param basePackages
     * @param rule
     * @return
     */
    public List<Class> findPackageClasses(List<String> basePackages, ResourceScanRule<Class> rule) {
        if(CollectionUtils.isEmpty(basePackages)){
            return null;
        }
        Set<String> resultBasePackages=checkPackage(basePackages);

        List<Class> clazzs=new ArrayList<>();
        try {
            for(String basePackage: resultBasePackages){
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) +
                        "/**/*" + RESOURCE_PATTERN_SUFFIX;
                    clazzs.addAll(doScan(packageSearchPath, rule));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResourceScanException("find Classs Resource Exception!!!");
        }
        return clazzs;
    }



    /**
     * 排重、检测package父子关系，避免多次扫描
     *
     * @param basePackages
     * @return 返回检查后有效的路径集合
     */
    private static Set<String> checkPackage(List<String> basePackages) {
//        if (StringUtils.isEmpty(scanPackages)) {
//            return Collections.EMPTY_SET;
//        }
        Set<String> packages = new HashSet<String>(basePackages);
        //排重路径
        for (String pInArr : packages.toArray(new String[packages.size()])) {
            if (StringUtils.isEmpty(pInArr) || pInArr.equals(".") || pInArr.startsWith(".")) {
                continue;
            }
            if (pInArr.endsWith(".")) {
                pInArr = pInArr.substring(0, pInArr.length() - 1);
            }
            Iterator<String> packageIte = packages.iterator();
            boolean needAdd = true;
            while (packageIte.hasNext()) {
                String pack = packageIte.next();
                if (pInArr.startsWith(pack + ".")) {
                    //如果待加入的路径是已经加入的pack的子集，不加入
                    needAdd = false;
                } else if (pack.startsWith(pInArr + ".")) {
                    //如果待加入的路径是已经加入的pack的父集，删除已加入的pack
                    packageIte.remove();
                }
            }
            if (needAdd) {
                packages.add(pInArr);
            }
        }
        return packages;
    }
}
