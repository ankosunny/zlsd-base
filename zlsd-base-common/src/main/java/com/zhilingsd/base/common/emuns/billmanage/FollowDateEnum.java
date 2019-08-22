package com.zhilingsd.base.common.emuns.billmanage;


import com.zhilingsd.base.common.bean.Tuple;
import com.zhilingsd.base.common.utils.DateUtil;

import java.util.Date;

/**
 * @author: wangjiaquan
 * @description:未跟进时间枚举
 * @create: 2019/6/17 14:45
 */

public enum FollowDateEnum {

    THREE_DAY(1,3,5,"三天未跟进"),
    FIVE_DAY(2,5,7,"五天未跟进"),
    SEVEN_DAY(3,7,null,"七天未跟进"),
    ;

    private int code;
    //开始
    private Integer start;
    //结束
    private Integer end ;
    private String value;


    FollowDateEnum(int code, Integer start, Integer end, String value) {
        this.code = code;
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    /**
     * 根据code值获取开始和结束时间
     * @param code
     */
    public static Tuple<Integer,Integer> getBeweenDay(int code){
        for (FollowDateEnum followDate: FollowDateEnum.values()) {
            if(followDate.getCode() == code ){
                return new Tuple<>(followDate.start,followDate.end);
            }
        }
        return null;
    }

}
