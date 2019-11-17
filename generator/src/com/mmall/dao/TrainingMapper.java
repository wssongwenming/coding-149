package com.mmall.dao;

import com.mmall.model.Training;

public interface TrainingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Training record);

    int insertSelective(Training record);

    Training selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Training record);

    int updateByPrimaryKey(Training record);
}