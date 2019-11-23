package com.mmall.dao;

import com.mmall.model.Trainee;

public interface TraineeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trainee record);

    int insertSelective(Trainee record);

    Trainee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trainee record);

    int updateByPrimaryKey(Trainee record);
}