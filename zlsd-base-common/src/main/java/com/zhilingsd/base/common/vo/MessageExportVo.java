package com.zhilingsd.base.common.vo;

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
public class MessageExportVo extends ExportVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> exportValue;

    private Integer type;

    private String[] words = {
            "[邮编]",
            "[发函地址]",
            "[收件人号码]",
            "[姓名]"
    };
    /**
     * @description 案件编号
     **/
    private String postCode;
    /**
     * 发函地址
     */
    private String justiceAddress;
    /**
     * 收件人号码
     */
    private String reciveNum;
    /**
     * 姓名
     */
    private String name;
    public MessageExportVo() {
        exportValue = new HashMap<>();
        for (String word : words) {
            exportValue.put(word, "");
        }
    }

    public void setPostCode(String postCode) {
        if (postCode != null){
            this.postCode = postCode;
            this.exportValue.put("[邮编]",postCode);
        }
    }

    public void setJusticeAddress(String justiceAddress) {
        if (justiceAddress != null){
            this.justiceAddress = justiceAddress;
            this.exportValue.put("[发函地址]",justiceAddress);
        }
    }

    public void setReciveNum(String reciveNum) {
        if (reciveNum != null) {
            this.reciveNum = reciveNum;
            this.exportValue.put("[收件人号码]",reciveNum);
        }
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
            this.exportValue.put("[姓名]",name);
        }
    }

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
}