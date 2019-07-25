package com.zhilingsd.base.common.vo;

import com.zhilingsd.base.common.utils.ExportVo;
import com.zhilingsd.base.common.utils.ImportUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: zhangbo
 * @DateTime: 2019/5/17 9:39
 */
@Getter
@Builder
@AllArgsConstructor
public class JusticeExportVo extends ExportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long templateId;

    private Long justiceApplyId;

    public void setJusticeApplyId(Long justiceApplyId) {
        this.justiceApplyId = justiceApplyId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    private Map<Long,Long> justiceAndResourceId;

    private String[] words = {
            "[案件编号]",
            "[姓名]",
            "[身份证]",
            "[脱敏身份证]",
            "[手机]",
            "[委案金额]",
            "[最新欠款额]",
            "[年龄]",
            "[性别]",
            "[帐号]",
            "[卡号]",
            "[脱敏卡号]",
            "[客户号]",
            "[逾期天数]",
            "[逾期次数]",
            "[借款天数]",
            "[账龄]",
            "[手别]",
            "[币种]",
            "[账单日]",
            "[账单期别]",
            "[原币欠款]",
            "[产品代码]",
            "[合同编号]",
            "[账户类型]",
            "[停息日]",
            "[透支起始日]",
            "[到期还款日]",
            "[分期付款授权累计]",
            "[其他]",
            "[委案时间]",
            "[委案年]",
            "[委案月]",
            "[委案日]",
            "[催收员]",
            "[收件人]",
            "[收件地址]",
            "[收件人电话]",

            "[开户行]",
            "[开卡日期]",
            "[出生日期]",
            "[二级机构全称]",
            "[当天时间]",
            "[当天年]",
            "[当天月]",
            "[当天日]"
    };

    @Override
    public void replaceContent(String text, XWPFRun bufferrun) {
        if(!this.getExportValue().containsKey(text)){
            bufferrun.setText("", 0);
        }else {
            if(Objects.nonNull(this.getExportValue().get(text))){
                String tureValue = text.replace(text, this.getExportValue().get(text));
                bufferrun.setText(tureValue,0);
            }else {
                bufferrun.setText("",0);
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
     * 身份证
     **/
    private String idNumber;
    /**
     * 脱敏身份证
     **/
    private String idNumberSafe;
    /**
     * 手机
     **/
    private String borrowerPhone;
    /**
     * 委案金额
     **/
    private String commitMoney;
    /**
     * 最新欠款额
     **/
    private String latestDebtMoney;
    /**
     * 年龄
     **/
    private String borrowerAge;
    /**
     * 性别
     **/
    private String borrowerSex;
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
     * 逾期天数
     **/
    private String overdueDay;
    /**
     * 逾期次数
     **/
    private String overdueTimes;
    /**
     * 借款天数
     **/
    private String loanDays;
    /**
     * 账龄
     **/
    private String bucket;
    /**
     * 手别
     **/
    private String billBatchTimes;
    /**
     * 币种
     **/
    private String currency;
    /**
     * 账单日
     **/
    private String billDays;
    /**
     * 账单期别
     **/
    private String billCycle;
    /**
     * 原币欠款
     **/
    private String defaultDebt;
    /**
     * 产品代码
     **/
    private String prodId;
    /**
     * 合同编号
     **/
    private String contractNo;
    /**
     * 账户类型
     **/
    private String accountType;
    /**
     * 停息日
     **/
    private String stopInterestDay;
    /**
     * 透支起始日
     **/
    private String overStartDate;
    /**
     * 到期还款日
     **/
    private String repaymentDate;
    /**
     * 分期付款授权累计
     **/
    private String stagesCount;
    /**
     * 其他
     **/
    private String other;
    /**
     * 委案时间
     **/
    private String commitDate;
    /**
     * 委案年
     **/
    private String commitYear;
    /**
     * 委案月
     **/
    private String commitMonth;
    /**
     * 委案日
     **/
    private String commitDay;
    /**
     * 催收员
     **/
    private String agentName;
    /**
     * 收件人
     **/
    private String justiceName;
    /**
     * 收件地址
     **/
    private String justiceAddress;
    /**
     * 收件人电话
     **/
    private String justicePhone;
    /**
     * 开户行
     **/
    private String openBrankCode;
    /**
     * 开卡日期
     **/
    private String buildDate;
    /**
     * 出生日期
     **/
    private String birthDate;
    /**
     * 二级机构全称
     **/
    private String lendingFullName;
    /**
     * 当天时间
     **/
    private String currentDate;
    /**
     * 当天年
     **/
    private String currentYear;
    /**
     * 当天月
     **/
    private String currentMonth;
    /**
     * 当天日
     **/
    private String currentDay;
    public JusticeExportVo() {
        exportValue = new HashMap<>();
        for (String word : words) {
            exportValue.put(word, "");
        }
    }

    public void setBillCode(String billCode) {
        if (StringUtils.isNotBlank(billCode)) {
            this.billCode = billCode;
            this.exportValue.put("[案件编号]", billCode);
        }
    }

    public void setName(String name) {
        if (StringUtils.isNotBlank(name)) {
            this.name = name;
            this.exportValue.put("[姓名]", name);
        }
    }

    public void setIdNumber(String idNumber) {
        if (StringUtils.isNotBlank(idNumber)) {
            this.idNumber = idNumber;
            this.exportValue.put("[身份证]", idNumber);
        }
    }

    public void setIdNumberSafe(String idNumberSafe) {
        if (StringUtils.isNotBlank(idNumberSafe)) {
            String s = ImportUtils.encrypIdNum(idNumberSafe, 12, 15);
            this.idNumberSafe = s;
            this.exportValue.put("[脱敏身份证]", s);
        }
    }

    public void setBorrowerPhone(String borrowerPhone) {
        if (StringUtils.isNotBlank(borrowerPhone)) {
            this.borrowerPhone = borrowerPhone;
            this.exportValue.put("[手机]", borrowerPhone);
        }
    }

    public void setCommitMoney(String commitMoney) {
        if (StringUtils.isNotBlank(commitMoney)) {
            this.commitMoney = commitMoney;
            this.exportValue.put("[委案金额]", commitMoney);
        }
    }

    public void setLatestDebtMoney(String latestDebtMoney) {
        if (StringUtils.isNotBlank(latestDebtMoney)) {
            this.latestDebtMoney = latestDebtMoney;
            this.exportValue.put("[最新欠款额]", latestDebtMoney);
        }
    }

    public void setBorrowerAge(String borrowerAge) {
        if (StringUtils.isNotBlank(borrowerAge)) {
            this.borrowerAge = borrowerAge;
            this.exportValue.put("[年龄]", borrowerAge);
        }
    }

    public void setBorrowerSex(String borrowerSex) {
        if (StringUtils.isNotBlank(borrowerSex)) {
            this.borrowerSex = borrowerSex;
            this.exportValue.put("[性别]", borrowerSex);
        }
    }

    public void setAccountNum(String accountNum) {
        if (StringUtils.isNotBlank(accountNum)) {
            this.accountNum = accountNum;
            this.exportValue.put("[帐号]", accountNum);
        }
    }

    public void setCardNum(String cardNum) {
        if (StringUtils.isNotBlank(cardNum)) {
            this.cardNum = cardNum;
            this.exportValue.put("[卡号]", cardNum);
        }
    }

    public void setCardNumSafe(String cardNumSafe) {
        if (StringUtils.isNotBlank(cardNumSafe)) {
            String s = ImportUtils.encrypIdNum(cardNum, 9, 12);
            this.cardNumSafe = s;
            this.exportValue.put("[脱敏卡号]", s);
        }
    }

    public void setClientNum(String clientNum) {
        if (StringUtils.isNotBlank(clientNum)) {
            this.clientNum = clientNum;
            this.exportValue.put("[客户号]", clientNum);
        }
    }

    public void setOverdueDay(String overdueDay) {
        if (StringUtils.isNotBlank(overdueDay)) {
            this.overdueDay = overdueDay;
            this.exportValue.put("[逾期天数]", overdueDay);
        }
    }

    public void setOverdueTimes(String overdueTimes) {
        if (StringUtils.isNotBlank(overdueTimes)) {
            this.overdueTimes = overdueTimes;
            this.exportValue.put("[逾期次数]", overdueTimes);
        }
    }

    public void setLoanDays(String loanDays) {
        if (StringUtils.isNotBlank(loanDays)) {
            this.loanDays = loanDays;
            this.exportValue.put("[借款天数]", loanDays);
        }
    }

    public void setBucket(String bucket) {
        if (StringUtils.isNotBlank(bucket)) {
            this.bucket = bucket;
            this.exportValue.put("[账龄]", bucket);
        }
    }

    public void setBillBatchTimes(String billBatchTimes) {
        if (StringUtils.isNotBlank(billBatchTimes)) {
            this.billBatchTimes = billBatchTimes;
            this.exportValue.put("[手别]", billBatchTimes);
        }
    }

    public void setCurrency(String currency) {
        if (StringUtils.isNotBlank(currency)) {
            this.exportValue.put("[币种]", currency);
            this.currency = currency;
        }
    }

    public void setBillDays(String billDays) {
        if (StringUtils.isNotBlank(billDays)) {
            this.billDays = billDays;
            this.exportValue.put("[账单日]", billDays);
        }
    }

    public void setBillCycle(String billCycle) {
        if (StringUtils.isNotBlank(billCycle)) {
            this.billCycle = billCycle;
            this.exportValue.put("[账单期别]", billCycle);
        }
    }

    public void setDefaultDebt(String defaultDebt) {
        if (StringUtils.isNotBlank(defaultDebt)) {
            this.defaultDebt = defaultDebt;
            this.exportValue.put("[原币欠款]", defaultDebt);
        }
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
        if (StringUtils.isNotBlank(prodId)) {
            this.exportValue.put("[产品代码]", prodId);
        }
    }

    public void setContractNo(String contractNo) {
        if (StringUtils.isNotBlank(contractNo)) {
            this.contractNo = contractNo;
            this.exportValue.put("[合同编号]", contractNo);
        }
    }

    public void setAccountType(String accountType) {
        if (StringUtils.isNotBlank(accountType)) {
            this.accountType = accountType;
            this.exportValue.put("[账户类型]", accountType);
        }
    }

    public void setStopInterestDay(String stopInterestDay) {
        if (StringUtils.isNotBlank(stopInterestDay)) {
            this.stopInterestDay = stopInterestDay;
            this.exportValue.put("[停息日]", stopInterestDay);
        }
    }

    public void setOverStartDate(String overStartDate) {
        if (StringUtils.isNotBlank(overStartDate)) {
            this.overStartDate = overStartDate;
            this.exportValue.put("[透支起始日]", overStartDate);
        }
    }

    public void setRepaymentDate(String repaymentDate) {
        if (StringUtils.isNotBlank(repaymentDate)) {
            this.repaymentDate = repaymentDate;
            this.exportValue.put("[到期还款日]", repaymentDate);
        }
    }

    public void setStagesCount(String stagesCount) {
        if (StringUtils.isNotBlank(stagesCount)) {
            this.stagesCount = stagesCount;
            this.exportValue.put("[分期付款授权累计]", stagesCount);
        }
    }

    public void setOther(String other) {
        if (StringUtils.isNotBlank(other)) {
            this.other = other;
            this.exportValue.put("[其他]", other);
        }
    }

    public void setCommitDate(String commitDate) {
        if (StringUtils.isNotBlank(commitDate)) {
            this.commitDate = commitDate;
            this.exportValue.put("[委案时间]", commitDate);
        }
    }

    public void setCommitYear(String commitYear) {
        if (StringUtils.isNotBlank(commitYear)) {
            this.commitYear = commitYear;
            this.exportValue.put("[委案年]", commitYear);
        }
    }

    public void setCommitMonth(String commitMonth) {
        if (StringUtils.isNotBlank(commitMonth)) {
            this.commitMonth = commitMonth;
            this.exportValue.put("[委案月]", commitMonth);
        }
    }

    public void setCommitDay(String commitDay) {
        this.commitDay = commitDay;
        if (StringUtils.isNotBlank(commitDay)) {
            this.exportValue.put("[委案日]", commitDay);
        }
    }

    public void setAgentName(String agentName) {
        if (StringUtils.isNotBlank(agentName)) {
            this.agentName = agentName;
            this.exportValue.put("[催收员]", agentName);
        }
    }

    public void setJusticeName(String justiceName) {
        if (StringUtils.isNotBlank(justiceName)) {
            this.justiceName = justiceName;
            this.exportValue.put("[收件人]", justiceName);
        }
    }

    public void setJusticeAddress(String justiceAddress) {
        if (StringUtils.isNotBlank(justiceAddress)) {
            this.justiceAddress = justiceAddress;
            this.exportValue.put("[收件地址]", justiceAddress);
        }
    }

    public void setJusticePhone(String justicePhone) {
        if (StringUtils.isNotBlank(justicePhone)) {
            this.justicePhone = justicePhone;
            this.exportValue.put("[收件人电话]", justicePhone);
        }
    }

    public void setOpenBrankCode(String openBrankCode) {
        if (StringUtils.isNotBlank(openBrankCode)) {
            this.openBrankCode = openBrankCode;
            this.exportValue.put("[开户行]", openBrankCode);
        }
    }

    public void setBuildDate(String buildDate) {
        if (StringUtils.isNotBlank(buildDate)) {
            this.buildDate = buildDate;
            this.exportValue.put("[开卡日期]", buildDate);
        }
    }

    public void setBirthDate(String birthDate) {
        if (StringUtils.isNotBlank(birthDate)) {
            this.birthDate = birthDate;
            this.exportValue.put("[出生日期]", birthDate);
        }
    }

    public void setLendingFullName(String lendingFullName) {
        if (StringUtils.isNotBlank(lendingFullName)) {
            this.lendingFullName = lendingFullName;
            this.exportValue.put("[二级机构全称]", lendingFullName);
        }
    }

    public void setCurrentDate(String currentDate) {
        if (StringUtils.isNotBlank(currentDate)) {
            this.currentDate = currentDate;
            this.exportValue.put("[当天时间]", currentDate);
        }
    }

    public void setCurrentYear(String currentYear) {
        if (StringUtils.isNotBlank(currentYear)) {
            this.currentYear = currentYear;
            this.exportValue.put("[当天年]", currentYear);
        }
    }

    public void setCurrentMonth(String currentMonth) {
        if (StringUtils.isNotBlank(currentMonth)) {
            this.currentMonth = currentMonth;
            this.exportValue.put("[当天月]", currentMonth);
        }
    }

    public void setCurrentDay(String currentDay) {
        if (StringUtils.isNotBlank(currentDay)) {
            this.currentDay = currentDay;
            this.exportValue.put("[当天日]", currentDay);
        }
    }

}