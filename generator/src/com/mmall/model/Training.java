package com.mmall.model;

import java.util.Date;

public class Training {
    private Integer id;

    private String title;

    private String orgDept;

    private Integer traineeNumber;

    private Date dot;

    private String addr;

    private String memo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getOrgDept() {
        return orgDept;
    }

    public void setOrgDept(String orgDept) {
        this.orgDept = orgDept == null ? null : orgDept.trim();
    }

    public Integer getTraineeNumber() {
        return traineeNumber;
    }

    public void setTraineeNumber(Integer traineeNumber) {
        this.traineeNumber = traineeNumber;
    }

    public Date getDot() {
        return dot;
    }

    public void setDot(Date dot) {
        this.dot = dot;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}