package com.zhilingsd.base.common.bean;

import lombok.*;

/**
 * 机器人系统操作人信息，API层设置
 * @author 余柏良
 * @date 2020-2-22
 * */
@Getter
@Setter
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
     * 商户ID
     */
    private Long merchantId;
    /**
     * 平台
     */
    private String platform;

}
