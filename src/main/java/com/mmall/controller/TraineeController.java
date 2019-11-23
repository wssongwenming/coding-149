package com.mmall.controller;

import com.mmall.beans.PageQuery;
import com.mmall.beans.PageResult;
import com.mmall.common.JsonData;
import com.mmall.model.Trainee;
import com.mmall.param.TraineeParam;
import com.mmall.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;

@Controller
@RequestMapping("/sys/trainee")
@Slf4j
public class TraineeController {


    @Resource
    private TraineeService traineeService;

    @RequestMapping("trainee.page")
    public ModelAndView page() {
        return new ModelAndView("trainee");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveTrainee(TraineeParam param) throws ParseException {
        traineeService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateTrainee(TraineeParam param) throws ParseException {
        traineeService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(PageQuery pageQuery) {
        PageResult<Trainee> result = traineeService.getPage(pageQuery);
        return JsonData.success(result);
    }

    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id) {
        traineeService.delete(id);
        System.out.print("aaaaaa="+JsonData.success().toMap());
        return JsonData.success();
    }


}
