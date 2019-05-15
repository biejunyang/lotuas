package com.lotuas.samples.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;

@Mapper
public interface BaseDao<T> {

    @Select("")
    public T selectById(Serializable id, Class<?> clazz);

}
