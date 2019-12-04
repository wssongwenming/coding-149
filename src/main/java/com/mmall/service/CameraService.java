package com.mmall.service;


import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.dao.CameraMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.Camera;
import com.mmall.model.SysUser;
import com.mmall.model.Trainee;
import com.mmall.model.Training;
import com.mmall.param.CameraParam;
import com.mmall.param.TraineeParam;
import com.mmall.util.BeanValidator;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service
public class CameraService {
    @Resource
    private CameraMapper cameraMapper;
    public void save(CameraParam param) throws ParseException {

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
        Camera camera = Camera.builder().name(param.getName()).mac(param.getMac()).ip(param.getIp())
                .status(param.getStatus()).number(param.getNumber()).memo(param.getMemo()).build();
        cameraMapper.insertSelective(camera);
    }
    public PageResult<Camera> getPage(PageQuery page) {
        BeanValidator.check(page);
        int count = cameraMapper.count();
        if (count > 0) {
            List<Camera> list = cameraMapper.getPage(page);
            return PageResult.<Camera>builder().total(count).data(list).build();
        }
        return PageResult.<Camera>builder().build();
    }
    public void delete(int cameraId) {
        Camera camera = cameraMapper.selectByPrimaryKey(cameraId);
        Preconditions.checkNotNull(camera, "待删除的训练计划不存在，无法删除");

        //TODO 删除时判断有没有依赖数据,比如打靶成绩
        cameraMapper.deleteByPrimaryKey(cameraId);
    }
    public void update(CameraParam param) throws ParseException {
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
        Camera before = cameraMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的设备不存在");

        Camera after = Camera.builder().id(param.getId()).name(param.getName()).ip(param.getIp())
                .mac(param.getMac()).status(param.getStatus()).number(param.getNumber()).memo(param.getMemo()).build();
        cameraMapper.updateByPrimaryKeySelective(after);

    }

    private boolean checkExistByIp(String ip,Integer id) {
        return cameraMapper.countByIp(ip,id) > 0;
    }
    private boolean checkExistByMac(String mac,Integer id) {
        return cameraMapper.countByMac(mac,id) > 0;
    }
    private boolean checkExistByNumber(Integer number,Integer id) {
        return cameraMapper.countByNumber(number,id) > 0;
    }
}
