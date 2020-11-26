package com.zhilingsd.base.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作人信息，API层设置
 * @author 灭霸
 * @date 2019-5-12 20:30:01
 * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppAgentInfo {
    /**
     * 相当于token
     */
    private String session;
    /**
     * 当前登录用户Id
     */
    private Long operatorId;
    /**
     * 当前登录用户所在的催收机构Id
     */
    private Long collectionCompanyId;

    /**
     * 当前登录用户所在的催收小组Id
     */
    private long collectionGroupId;
    /**
     * 行内inner/委外out
     */
    private String assetCompanyType;

}
