package com.mmall.service;

import com.google.common.base.Preconditions;
import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.dao.TraineeMapper;
import com.mmall.dao.TrainingMapper;
import com.mmall.exception.ParamException;
import com.mmall.model.SysUser;
import com.mmall.model.Trainee;
import com.mmall.model.Training;
import com.mmall.param.TraineeParam;
import com.mmall.param.TrainingParam;
import com.mmall.util.BeanValidator;
import com.mmall.util.MD5Util;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@Service
public class TraineeService {
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Resource
    private TraineeMapper traineeMapper;

    @Resource
    private SysLogService sysLogService;

    @Resource
    SqlSessionTemplate sqlSessionTemplate;

    public void save(TraineeParam param) throws ParseException {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getPhone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        Trainee trainee = Trainee.builder().name(param.getName()).trainingId(param.getTrainingId()).password(MD5Util.encrypt(param.getPassword()))
        .phone(param.getPhone()).status(param.getStatus()).memo(param.getMemo()).photo(param.getPhoto()).workunit(param.getWorkunit()).build();
        traineeMapper.insertSelective(trainee);
    }

    public void update(TraineeParam param) throws ParseException {
        BeanValidator.check(param);
        Trainee before = traineeMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的参训人员不存在");

        Trainee after = Trainee.builder().id(param.getId()).name(param.getName()).trainingId(param.getTrainingId())
                .phone(param.getPhone()).password(param.getPassword()).photo(param.getPhoto()).status(param.getStatus()).memo(param.getMemo()).workunit(param.getWorkunit()).build();
        traineeMapper.updateByPrimaryKeySelective(after);

    }

    public boolean checkTelephoneExist(String phone, Integer id) {
        return traineeMapper.countByPhone(phone, id) > 0;
    }

/*
    public PageResult<Trainee> getPage(PageQuery page) {
        BeanValidator.check(page);
        int count = traineeMapper.countAll();
        if (count > 0) {
            List<Trainee> list = traineeMapper.getPage(page);
            return PageResult.<Trainee>builder().total(count).data(list).build();
        }
        return PageResult.<Trainee>builder().build();
    }*/

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


    public PageResult<Trainee> getPageByTrainingId(int trainingId, PageQuery page){
        BeanValidator.check(page);
        int count = traineeMapper.countByTrainingId(trainingId);
        if (count > 0) {
            List<Trainee> list = traineeMapper.getPageByTrainingId(trainingId, page);
            return PageResult.<Trainee>builder().total(count).data(list).build();
        }
        return PageResult.<Trainee>builder().build();

    }
    public String writeExelData(List<Object> list){
        Trainee trainee;
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);//跟上述sql区别
        int size=list.size();
         for (int i = 0; i<size; i++) {
            trainee = new Trainee();
            trainee.setName((String)((HashMap)list.get(i)).get("name"));
            trainee.setMemo((String)((HashMap)list.get(i)).get("memo"));
            trainee.setPassword((String)((HashMap)list.get(i)).get("password"));
            trainee.setPhone((String)((HashMap)list.get(i)).get("phone"));
            trainee.setStatus(Integer.parseInt(((HashMap)list.get(i)).get("status").toString()));
            trainee.setPhoto((String)((HashMap)list.get(i)).get("photo"));
            trainee.setTrainingId((Integer)((HashMap)list.get(i)).get("trainingid"));
            trainee.setWorkunit((String)((HashMap)list.get(i)).get("workunit"));
            traineeMapper.insertSelective(trainee);
        }
        sqlSession.commit();

         return "ok";
    }
}
