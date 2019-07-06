package com.zhilingsd.base.common.emuns;

/**
 * 基准数据类型定义
 * @program: 智灵时代广州研发中心
 * @author: ant man(tuzhen)
 * @create: 2019/6/3 9:13
 **/
public enum ConsoleBaseInfoTypeEnum {

//    基准数据类型 1-关系对象 2-联系类型 3-案件归类 4-手别描述 5-催收方式 6-催收结果

    RelationalObject(1,"关系对象"),
   	RelationshipType (2,"联系类型"),
    CaseClassification(3,"案件归类"),
    HandDescription(4,"手别描述"),
    CollectionMethod(5,"催收方式"),
    CollectionResult(6,"催收结果");


    /** 状态码 **/
    private int code;
    /** 状态描述 **/
    private String description;

    ConsoleBaseInfoTypeEnum(int code, String description){
        this.code =code;
        this.description =description;
    }

    public ConsoleBaseInfoTypeEnum findByCode(int code){
        for (ConsoleBaseInfoTypeEnum typeEnum : ConsoleBaseInfoTypeEnum.values()) {
            if (typeEnum.getCode() == code) {
                return typeEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
