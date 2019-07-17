package com.zhilingsd.base.common.vo;

import com.zhilingsd.base.common.utils.ExportVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
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
public class VisitExportVo extends ExportVo implements Serializable {

    private static final long serialVersionUID = 1L;
    private Map<String, String> exportValue;

    private String[] words = {
            "案件编号",
            "姓名",
            "借款人身份证号",
            "脱敏身份证",
            "手机",
            "委托金额",
            "帐号",
            "卡号",
            "脱敏卡号",
            "客户号",
            "委案时间",
            "退案时间",
            "家庭电话",
            "外访日期",
            "外访时间",
            "结束日期",
            "结束时间",
            "外访人1",
            "外访人2",
            "外访地址",
            "外访催记日期",
            "外访催记时间",
            "外访催记类型",
            "外访催记关系",
            "外访催记电话",
            "外访催记结果",
            "外访催记催收方式",
            "外访催记"
    };

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

    /**
     * 案件编号
     **/
    private String billCode;
    /**
     * 姓名
     **/
    private String name;
    /**
     * 借款人身份证号
     **/
    private String idNumber;
    /**
     * 脱敏身份证
     **/
    private String idNumberSafe;
    /**
     * 手机
     **/
    private String phone;
    /**
     * 委托金额
     **/
    private String commitMoney;
    /**
     * 帐号
     **/
    private String accountNum;
    /**
     * 卡号
     **/
    private String cardNum;
    /**
     * 脱敏卡号
     **/
    private String cardNumSafe;
    /**
     * 客户号
     **/
    private String clientNum;
    /**
     * 委案时间
     **/
    private String commitDate;
    /**
     * 退案时间
     **/
    private String limitDate;
    /**
     * 家庭电话
     **/
    private String homeTel;
    /**
     * 外访日期
     **/
    private String visitDate;
    /**
     * 外访时间
     **/
    private String visitTime;
    /**
     * 结束日期
     **/
    private String visitEndDate;
    /**
     * 结束时间
     **/
    private String visitEndTime;
    /**
     * 外访人1
     **/
    private String visitOne;
    /**
     * 外访人2
     **/
    private String visitTwo;

    private Long staffAId;

    private Long staffBId;

    /**
     * 外访地址
     **/
    private String visitAdress;
    /**
     * 外访催记日期
     **/
    private String visitMarkDate;
    /**
     * 外访催记时间
     **/
    private String visitMarkTime;

    /**
     * 外访催记类型
     **/
    private Integer markType;
    /**
     * 外访催记类型
     **/
    private String visitMarkType;
    /**
     * 外访催记关系
     **/
    private String visitMarkRelation;
    /**
     * 外访催记电话
     **/
    private String visitMarkPhone;
    /**
     * 外访催记结果
     **/
    private String visitMarkResult;
    /**
     * 外访催记催收方式
     **/
    private String visitMarkMethod;
    /**
     * 外访催记内容
     **/
    private String visitMarkContent;

    public VisitExportVo() {
        exportValue = new HashMap<>();
        for (String word : words) {
            exportValue.put(word, "");
        }
    }

    public void setBillCode(String billCode) {
        if (StringUtils.isNotBlank(billCode)) {
            this.exportValue.put("案件编号", billCode);
        }
        this.billCode = billCode;
    }

    public void setName(String name) {
        if (StringUtils.isNotBlank(name)) {
            this.name = name;
            this.exportValue.put("姓名", name);
        }
    }

    public void setIdNumber(String idNumber) {
        if (StringUtils.isNotBlank(idNumber)) {
            this.idNumber = idNumber;
            this.exportValue.put("借款人身份证号", idNumber);
        }
    }
    public void setIdNumberSafe(String idNumberSafe) {
        if (StringUtils.isNotBlank(idNumberSafe)) {
            this.idNumberSafe = idNumberSafe;
            this.exportValue.put("脱敏身份证", idNumberSafe);
        }
    }

    public void setPhone(String phone) {
        if (StringUtils.isNotBlank(phone)) {
            this.phone = phone;
            this.exportValue.put("手机", phone);
        }
    }

    public void setCommitMoney(String commitMoney) {
        if (StringUtils.isNotBlank(commitMoney)) {
            this.commitMoney = commitMoney;
            this.exportValue.put("委托金额", commitMoney);
        }
    }

    public void setAccountNum(String accountNum) {
        if (StringUtils.isNotBlank(accountNum)) {
            this.accountNum = accountNum;
            this.exportValue.put("账号", accountNum);
        }
    }

    public void setCardNum(String cardNum) {
        if (StringUtils.isNotBlank(cardNum)) {
            this.cardNum = cardNum;
            this.exportValue.put("卡号", cardNum);
        }
    }
    public void setCardNumSafe(String cardNumSafe) {
        if (StringUtils.isNotBlank(cardNumSafe)) {
            this.cardNumSafe = cardNumSafe;
            this.exportValue.put("脱敏卡号", cardNumSafe);
        }
    }


    public void setClientNum(String clientNum) {
        if (StringUtils.isNotBlank(clientNum)) {
            this.clientNum = clientNum;
            this.exportValue.put("客户号", clientNum);
        }
    }

    public void setCommitDate(String commitDate) {
        if (StringUtils.isNotBlank(commitDate)) {
            this.commitDate = commitDate;
            this.exportValue.put("委案时间", commitDate);
        }
    }

    public void setLimitDate(String limitDate) {
        if (StringUtils.isNotBlank(limitDate)) {
            this.limitDate = limitDate;
            this.exportValue.put("退案时间", limitDate);
        }
    }
    public void setHomeTel(String homeTel) {
        if (StringUtils.isNotBlank(homeTel)) {
            this.homeTel = homeTel;
            this.exportValue.put("家庭电话", homeTel);
        }
    }

    public void setVisitDate(String visitDate) {
        if (StringUtils.isNotBlank(visitDate)) {
            this.visitDate = visitDate;
            this.exportValue.put("外访日期", visitDate);
        }
    }

    public void setVisitTime(String visitTime) {
        if (StringUtils.isNotBlank(visitTime)) {
            this.visitTime = visitTime;
            this.exportValue.put("外访时间", visitTime);
        }
    }

    public void setVisitEndDate(String visitEndDate) {
        if (StringUtils.isNotBlank(visitEndDate)) {
            this.visitEndDate = visitEndDate;
            this.exportValue.put("外访结束日期", visitEndDate);
        }
    }

    public void setVisitEndTime(String visitEndTime) {
        if (StringUtils.isNotBlank(visitEndTime)) {
            this.visitEndTime = visitEndTime;
            this.exportValue.put("外访结束时间", visitEndTime);
        }
    }

    public void setVisitOne(String visitOne) {
        if (StringUtils.isNotBlank(visitOne)) {
            this.visitOne = visitOne;
            this.exportValue.put("外访人1", visitOne);
        }
    }

    public void setVisitTwo(String visitTwo) {
        if (StringUtils.isNotBlank(visitTwo)) {
            this.visitTwo = visitTwo;
            this.exportValue.put("外访人2", visitTwo);
        }
    }

    public void setVisitAdress(String visitAdress) {
        if (StringUtils.isNotBlank(visitAdress)) {
            this.visitAdress = visitAdress;
            this.exportValue.put("外放地址", visitAdress);
        }
    }

    public void setVisitMarkDate(String visitMarkDate) {
        if (StringUtils.isNotBlank(visitMarkDate)) {
            this.visitMarkDate = visitMarkDate;
            this.exportValue.put("外访催记日期", visitMarkDate);
        }
    }

    public void setVisitMarkTime(String visitMarkTime) {
        if (StringUtils.isNotBlank(visitMarkTime)) {
            this.visitMarkTime = visitMarkTime;
            this.exportValue.put("外访催记时间", visitMarkTime);
        }
    }

    public void setVisitMarkType(String visitMarkType) {
        if (StringUtils.isNotBlank(visitMarkType)) {
            this.visitMarkType = visitMarkType;
            this.exportValue.put("外访催记类型", visitMarkType);
        }
    }

    public void setVisitMarkRelation(String visitMarkRelation) {
        if (StringUtils.isNotBlank(visitMarkRelation)) {
            this.visitMarkRelation = visitMarkRelation;
            this.exportValue.put("外访催记关系", visitMarkRelation);
        }
    }

    public void setVisitMarkPhone(String visitMarkPhone) {
        if (StringUtils.isNotBlank(visitMarkPhone)) {
            this.visitMarkPhone = visitMarkPhone;
            this.exportValue.put("外访催记电话", visitMarkPhone);
        }
    }

    public void setVisitMarkResult(String visitMarkResult) {
        if (StringUtils.isNotBlank(visitMarkResult)) {
            this.visitMarkResult = visitMarkResult;
            this.exportValue.put("外访催记结果", visitMarkResult);
        }
    }

    public void setVisitMarkMethod(String visitMarkMethod) {
        if (StringUtils.isNotBlank(visitMarkMethod)) {
            this.visitMarkMethod = visitMarkMethod;
            this.exportValue.put("外访催记催收方式", visitMarkMethod);
        }
    }

    public void setVisitMarkContent(String visitMarkContent) {
        if (StringUtils.isNotBlank(visitMarkContent)) {
            this.visitMarkContent = visitMarkContent;
            this.exportValue.put("外访催记", visitMarkContent);
        }
    }




}