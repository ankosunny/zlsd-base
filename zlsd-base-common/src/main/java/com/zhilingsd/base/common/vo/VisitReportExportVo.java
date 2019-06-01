package com.zhilingsd.base.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangbo
 * @DateTime: 2019/5/17 9:39
 */
@Getter
@Builder
@AllArgsConstructor
public class VisitReportExportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> exportValue;

    private Integer type;

    private String[] words = {
            "[案件编号]",
            "[外访日期]",
            "[外访时间]",
            "[结束时间]",
            "[姓名]",
            "[委托日期]",
            "[委托金额]",
            "[目前余额]",
            "[卡号]",
            "[身份证号码]",
            "[手机]",
            "[外访情况]"
    };
    /**
     * @description 案件编号
     **/
    private String billCode;
    /**
     * @description 外访日期
     **/
    private String beginDate;
    /**
     * @description 外访时间
     **/
    private String beginTime;
    /**
     * @description 结束时间
     **/
    private String endTime;
    /**
     * @description 姓名
     **/
    private String name;
    /**
     * @description 外访对象电话
     **/
    private String visitPhone;
    /**
     * @description 委托年月日
     **/
    private String commitDate;
    /**
     * @description 卡号
     **/
    private String cardNum;
    /**
     * @description 委案金额
     **/
    private String commitMoney;
    /**
     * @description 欠款金额
     **/
    private String debtMoney;
    /**
     * @description 身份证
     **/
    private String idCard;
    /**
     * @description 外访情况
     **/
    private String visitDesc;
    public VisitReportExportVo() {
        exportValue = new HashMap<>();
        for (String word : words) {
            exportValue.put(word, "");
        }
    }

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
            this.exportValue.put("[委托金额]", commitMoney);
        }
    }

    public void setIdCard(String idCard) {
        if (idCard != null) {
            this.idCard = idCard;
            this.exportValue.put("[身份证]", idCard);
        }
    }

    public void setBillCode(String billCode) {
        if (idCard != null) {
            this.billCode = billCode;
            this.exportValue.put("[案件编号]",billCode);
        }
    }

    public void setBeginDate(String beginDate) {
        if (beginDate != null){
            this.beginDate = beginDate;
            this.exportValue.put("[外访日期]",beginDate);
        }
    }

    public void setBeginTime(String beginTime) {
        if (beginTime != null){
            this.beginTime = beginTime;
            this.exportValue.put("[外访时间]",beginTime);
        }
    }

    public void setEndTime(String endTime) {
        if (endTime != null){
            this.endTime = endTime;
            this.exportValue.put("[结束时间]",endTime);
        }
    }

    public void setVisitPhone(String visitPhone) {
        if (visitPhone != null) {
            this.visitPhone = visitPhone;
            this.exportValue.put("[电话]",visitPhone);
        }
    }

    public void setCommitDate(String commitDate) {
        if (commitDate != null){
            this.commitDate = commitDate;
            this.exportValue.put("[委托日期]",commitDate);
        }
    }

    public void setDebtMoney(String debtMoney) {
        if (debtMoney != null){
            this.debtMoney = debtMoney;
            this.exportValue.put("[目前余额]",debtMoney);
        }
    }

    public void setVisitDesc(String visitDesc) {
        if (visitDesc != null){
            this.visitDesc = visitDesc;
            this.exportValue.put("[外访情况]",visitDesc);
        }
    }
}