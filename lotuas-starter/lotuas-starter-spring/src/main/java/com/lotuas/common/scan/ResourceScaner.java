package com.lotuas.common.scan;

import java.io.IOException;
import java.util.List;

/**
 * 资源扫描器
 * @param <T>
 */
public interface ResourceScaner<T> {

    List<T> doScan(String scanPath) throws IOException;

}
