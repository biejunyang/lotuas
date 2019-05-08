package com.lotuas.common.scan;


import com.lotuas.common.scan.exception.ResourceConvertException;
import org.springframework.core.io.Resource;

public interface ResourceConverter<T> {
    T convert(Resource resource) throws ResourceConvertException;
}
