package com.mmall.dao;

import com.mmall.model.Target;

public interface TargetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Target record);

    int insertSelective(Target record);

    Target selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Target record);

    int updateByPrimaryKey(Target record);
}