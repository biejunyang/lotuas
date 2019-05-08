package com.lotuas.common.scan;

public interface ResourceScanRule<T> {
    boolean validate(T resource);
}
