package com.mmall.dao;

import com.mmall.model.Device_Group;

public interface Device_GroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device_Group record);

    int insertSelective(Device_Group record);

    Device_Group selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device_Group record);

    int updateByPrimaryKey(Device_Group record);
}