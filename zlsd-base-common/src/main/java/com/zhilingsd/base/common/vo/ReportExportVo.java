package com.zhilingsd.base.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangbo
 * @DateTime: 2019/5/17 9:39
 */
@Getter
@Builder
@AllArgsConstructor
public class ReportExportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> exportValue;

    private String[] words = {
            "[姓名]",
            "[卡号]",
            "[委案金额]",
            "[催收员]",
            "[地区]",
            "[收件地址]",
            "[身份证]",
            "[委托年]",
            "[委托月]",
            "[委托日]",
            "[催收员姓]",
            "[催收员座机]",
            "[当前日期]"
    };

    public ReportExportVo() {
        exportValue = new HashMap<>();
        for (String word : words) {
            exportValue.put(word, "");
        }
    }

    /**
     * 姓名
     */
    private String name;
    /**
     * 卡号
     */
    private String cardNum;
    /**
     * 委案金额
     */
    private String commitMoney;
    /**
     * 催收员
     */
    private String staffName;
    /**
     * 地址
     */
    private String address;
    /**
     * 地区
     */
    private String area;
    /**
     * 身份证
     */
    private String idCard;
    /**
     * 委托年
     */
    private String year;
    /**
     * 委托月
     */
    private String month;
    /**
     * 委托日
     */
    private String day;
    /**
     * 催收员座机
     */
    private String staffTelPhone;
    /**
     * 当前日期
     */
    private String date;
    /**
     * [催收员姓]
     */
    private String lastName;


    public void setName(String name) {
        if (name != null) {
            this.name = name;
            this.exportValue.put("[姓名]", name);
        }
    }

    public void setCardNum(String cardNum) {
        if (cardNum != null) {
            this.cardNum = cardNum;
            this.exportValue.put("[卡号]", cardNum);
        }
    }

    public void setCommitMoney(String commitMoney) {
        if (commitMoney != null) {
            this.commitMoney = commitMoney;
            this.exportValue.put("[委案金额]", commitMoney);
        }
    }

    public void setStaffName(String staffName) {
        if (staffName != null) {
            this.staffName = staffName;
            this.exportValue.put("[催收员]", staffName);
        }
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
            this.exportValue.put("[收件地址]", address);
        }
    }

    public void setArea(String area) {
        if (area != null) {
            this.area = area;
            this.exportValue.put("[地区]", area);
        }
    }

    public void setIdCard(String idCard) {
        if (idCard != null) {
            this.idCard = idCard;
            this.exportValue.put("[身份证]", idCard);
        }
    }

    public void setYear(String year) {
        if (year != null) {
            this.year = year;
            this.exportValue.put("[委托年]", year);
        }
    }

    public void setMonth(String month) {
        if (month != null) {
            this.month = month;
            this.exportValue.put("[委托月]", month);
        }
    }

    public void setDay(String day) {
        if (day != null) {
            this.day = day;
            this.exportValue.put("[委托日]", day);
        }
    }

    public void setStaffTelPhone(String staffTelPhone) {
        if (staffTelPhone != null) {
            this.staffTelPhone = staffTelPhone;
            this.exportValue.put("[催收员座机]", staffTelPhone);
        }
    }

    public void setDate(String date) {
        if (date != null) {
            this.date = date;
            this.exportValue.put("[当前日期]", date);
        }
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
            this.exportValue.put("[催收员姓]", lastName);
        }
    }
}