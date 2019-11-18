package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class TrainingParam {
    private Integer id;

    @NotBlank(message = "训练计划名称不可以为空")
    @Length(min = 1, max = 50, message = "用户名长度需要在50个字以内")
    private String title;
    private String orgDept;
    private String trainigId;
    private Integer traineeNumber;
    private String dot;
    private String addr;
    private String memo;

}
