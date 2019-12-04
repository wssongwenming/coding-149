package com.mmall.service;


import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.dao.CameraMapper;
import com.mmall.dao.DisplayMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.Camera;
import com.mmall.model.Display;
import com.mmall.param.CameraParam;
import com.mmall.param.DisplayParam;
import com.mmall.util.BeanValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service
public class DisplayService {
    @Resource
    private DisplayMapper displayMapper;
    public void save(DisplayParam param) throws ParseException {

        BeanValidator.check(param);
        if (checkExistByIp(param.getIp(),param.getId())) {
            throw new ParamException("设备IP地址已经存在");
        }
        if (checkExistByMac(param.getMac(),param.getId())) {
            throw new ParamException("设备MAC地址已经存在");
        }
        if (checkExistByNumber(param.getNumber(),param.getId())) {
            throw new ParamException("设备IP地址已经存在");
        }
        Display display = Display.builder().name(param.getName()).mac(param.getMac()).ip(param.getIp())
                .status(param.getStatus()).number(param.getNumber()).memo(param.getMemo()).build();
        displayMapper.insertSelective(display);
    }
    public PageResult<Display> getPage(PageQuery page) {
        BeanValidator.check(page);
        int count = displayMapper.count();
        if (count > 0) {
            List<Display> list = displayMapper.getPage(page);
            return PageResult.<Display>builder().total(count).data(list).build();
        }
        return PageResult.<Display>builder().build();
    }
    public void delete(int displayId) {
        Display display = displayMapper.selectByPrimaryKey(displayId);
        Preconditions.checkNotNull(display, "待删除的训练计划不存在，无法删除");

        //TODO 删除时判断有没有依赖数据,比如打靶成绩
        displayMapper.deleteByPrimaryKey(displayId);
    }
    public void update(DisplayParam param) throws ParseException {
        BeanValidator.check(param);
        if (checkExistByIp(param.getIp(),param.getId())) {
            throw new ParamException("设备IP地址已经存在");
        }
        if (checkExistByMac(param.getMac(),param.getId())) {
            throw new ParamException("设备MAC地址已经存在");
        }
        if (checkExistByNumber(param.getNumber(),param.getId())) {
            throw new ParamException("设备IP地址已经存在");
        }
        Display before = displayMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的设备不存在");

        Display after = Display.builder().id(param.getId()).name(param.getName()).ip(param.getIp())
                .mac(param.getMac()).status(param.getStatus()).number(param.getNumber()).memo(param.getMemo()).build();
        displayMapper.updateByPrimaryKeySelective(after);

    }

    private boolean checkExistByIp(String ip,Integer id) {
        return displayMapper.countByIp(ip,id) > 0;
    }
    private boolean checkExistByMac(String mac,Integer id) {
        return displayMapper.countByMac(mac,id) > 0;
    }
    private boolean checkExistByNumber(Integer number,Integer id) {
        return displayMapper.countByNumber(number,id) > 0;
    }
}
