package com.mmall.dao;

import com.mmall.model.Display;

public interface DisplayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Display record);

    int insertSelective(Display record);

    Display selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Display record);

    int updateByPrimaryKey(Display record);
}