package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.dao.TraineeMapper;
import com.mmall.dao.TrainingMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.Trainee;
import com.mmall.model.Training;
import com.mmall.param.TraineeParam;
import com.mmall.param.TrainingParam;
import com.mmall.util.BeanValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class TraineeService {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private TraineeMapper traineeMapper;

    @Resource
    private SysLogService sysLogService;

    public void save(TraineeParam param) throws ParseException {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getPhone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        Trainee trainee = Trainee.builder().name(param.getName()).trainingId(param.getTrainingId()).password(param.getPassword())
        .phone(param.getPhone()).memo(param.getMemo()).photo(param.getPhoto()).workunit(param.getWorkunit()).build();
        traineeMapper.insertSelective(trainee);
    }

    public void update(TraineeParam param) throws ParseException {
        BeanValidator.check(param);
        Trainee before = traineeMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的参训人员不存在");

        Trainee after = Trainee.builder().id(param.getId()).name(param.getName()).trainingId(param.getTrainingId())
                .phone(param.getPhone()).password(param.getPassword()).photo(param.getPhoto()).memo(param.getMemo()).workunit(param.getWorkunit()).build();
        traineeMapper.updateByPrimaryKeySelective(after);

    }

    public boolean checkTelephoneExist(String phone, Integer id) {
        return traineeMapper.countByPhone(phone, id) > 0;
    }


    public PageResult<Trainee> getPage(PageQuery page) {
        BeanValidator.check(page);
        int count = traineeMapper.countAll();
        if (count > 0) {
            List<Trainee> list = traineeMapper.getPage(page);
            return PageResult.<Trainee>builder().total(count).data(list).build();
        }
        return PageResult.<Trainee>builder().build();
    }

    public void delete(int traineeId) {
        Trainee trainee = traineeMapper.selectByPrimaryKey(traineeId);
        Preconditions.checkNotNull(trainee, "待删除的训练计划不存在，无法删除");

       //TODO 删除时判断有没有依赖数据,比如打靶成绩
        /*if (sysDeptMapper.countByParentId(dept.getId()) > 0) {
            throw new ParamException("当前部门下面有子部门，无法删除");
        }
        if(sysUserMapper.countByDeptId(dept.getId()) > 0) {
            throw new ParamException("当前部门下面有用户，无法删除");
        }*/
        traineeMapper.deleteByPrimaryKey(traineeId);
    }
}
