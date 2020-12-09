package com.zhilingsd.base.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 机器人系统操作人信息，API层设置
 *
 * @author 余柏良
 * @date 2020-2-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserInfo {
    /**
     * 相当于token
     */
    private String session;
    /**
     * 当前登录用户Id
     */
    private Long operatorId;
    /**
     * 当前登录用户账号
     */
    private String account;
    /**
     * 商户ID
     */
    private Long merchantId;
    /**
     * 平台
     */
    private String platform;

}
