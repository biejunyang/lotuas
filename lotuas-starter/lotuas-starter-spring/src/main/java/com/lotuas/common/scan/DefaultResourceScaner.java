package com.lotuas.common.scan;

import com.lotuas.common.scan.exception.ResourceConvertException;
import org.springframework.core.io.Resource;

public class DefaultResourceScaner extends AbstractResourceScaner<Resource> {

    @Override
    public Resource convert(Resource resource) throws ResourceConvertException {
        return resource;
    }
}
