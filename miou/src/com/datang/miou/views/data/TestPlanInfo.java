package com.datang.miou.views.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dingzhongchang on 2015/3/6.
 */
public class TestPlanInfo {
    static DateFormat Format = new SimpleDateFormat("dd HH:mm");
    boolean isChecked;
    //名称
    String name;
    //创建时间
    String createTime;
    //创建者ID
    String creatorId=" ";
    //修改时间
    String modifiedTime=" ";
    //累计执行次数
    String exedNum=" ";

    public TestPlanInfo(String name, String creatorId) {
        this.name = name;
        this.createTime = Format.format(new Date());
        this.creatorId = creatorId;
    }

    @Override
    public String toString() {
        return name;
    }
}
