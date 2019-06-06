package com.zhilingsd.base.common.vo;

import com.zhilingsd.base.common.emuns.workmanage.ExportTypeEnum;
import com.zhilingsd.base.common.utils.ExportVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.poi.xwpf.usermodel.XWPFRun;

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
public class ReportExportVo extends ExportVo implements Serializable {
    @Override
    public void replaceContent(String text, XWPFRun bufferrun) {
        for (String word : this.getExportValue().keySet()) {
            if (word.equals(text) || text.contains(word)) {
                text = text.replace(word, this.getExportValue().get(word));
                bufferrun.setText(text, 0);
                break;
            }
        }
    }

    private static final long serialVersionUID = 1L;


    private Integer type;

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
            "[当前年]",
            "[当前月]",
            "[当前日]",
            "[催收员姓]",
            "[催收员座机]",
            "[当前日期]"
    };

    private String[] visitWords = {
            "[案件编号]",
            "[外访日期]",
            "[外访时间]",
            "[结束时间]",
            "[姓名]",
            "[委托日期]",
            "[委案金额]",
            "[目前余额]",
            "[卡号]",
            "[身份证]",
            "[手机]",
            "[外访情况]"
    };

    public ReportExportVo() {
        exportValue = new HashMap<>();
        for (String word : words) {
            exportValue.put(word, "");
        }
    }
    /**
     * @description 案件编号
     **/
    private String billCode;

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
    private String commitYear;
    /**
     * 委托月
     */
    private String commitMonth;
    /**
     * 委托日
     */
    private String commitDay;
    /**
     * 当前年
     */
    private String year;
    /**
     * 当前月
     */
    private String month;
    /**
     * 当前日
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

    public void setCommitYear(String commitYear) {
        if (commitYear != null) {
            this.commitYear = commitYear;
            this.exportValue.put("[委托年]", commitYear);
        }
    }

    public void setCommitMonth(String commitMonth) {
        if (commitMonth != null) {
            this.commitMonth = commitMonth;
            this.exportValue.put("[委托月]", commitMonth);
        }
    }

    public void setCommitDay(String commitDay) {
        if (commitDay != null) {
            this.commitDay = commitDay;
            this.exportValue.put("[委托日]", commitDay);
        }
    }

    public void setYear(String year) {
        if (year != null) {
            this.year = year;
            this.exportValue.put("[当前年]", year);
        }
    }

    public void setMonth(String month) {
        if (month != null) {
            this.month = month;
            this.exportValue.put("[当前月]", month);
        }
    }

    public void setDay(String day) {
        if (day != null) {
            this.day = day;
            this.exportValue.put("[当前日]", day);
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
     * @description 外访对象电话
     **/
    private String visitPhone;
    /**
     * @description 委托年月日
     **/
    private String commitDate;
    /**
     * @description 欠款金额
     **/
    private String debtMoney;
    /**
     * @description 外访情况
     **/
    private String visitDesc;
    public ReportExportVo(Integer type) {
        exportValue = new HashMap<>();
        if (type == ExportTypeEnum.EXPORT_TYPE_2.getCode()){
            for (String word : words) {
                exportValue.put(word, "");
            }
        }else if (type == ExportTypeEnum.EXPORT_TYPE_1.getCode()){
            for (String word : visitWords) {
                exportValue.put(word, "");
            }
        }
    }

    public void setBillCode(String billCode) {
        if (idCard != null) {
            this.billCode = billCode;
            this.exportValue.put("案件编号",billCode);
        }
    }

    public void setBeginDate(String beginDate) {
        if (beginDate != null){
            this.beginDate = beginDate;
            this.exportValue.put("外访日期",beginDate);
        }
    }

    public void setBeginTime(String beginTime) {
        if (beginTime != null){
            this.beginTime = beginTime;
            this.exportValue.put("外访时间",beginTime);
        }
    }

    public void setEndTime(String endTime) {
        if (endTime != null){
            this.endTime = endTime;
            this.exportValue.put("结束时间",endTime);
        }
    }

    public void setVisitPhone(String visitPhone) {
        if (visitPhone != null) {
            this.visitPhone = visitPhone;
            this.exportValue.put("电话",visitPhone);
        }
    }

    public void setCommitDate(String commitDate) {
        if (commitDate != null){
            this.commitDate = commitDate;
            this.exportValue.put("委托日期",commitDate);
        }
    }

    public void setDebtMoney(String debtMoney) {
        if (debtMoney != null){
            this.debtMoney = debtMoney;
            this.exportValue.put("目前余额",debtMoney);
        }
    }

    public void setVisitDesc(String visitDesc) {
        if (visitDesc != null){
            this.visitDesc = visitDesc;
            this.exportValue.put("外访情况",visitDesc);
        }
    }

}