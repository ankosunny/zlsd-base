package com.zhilingsd.base.common.emuns;

import com.zhilingsd.base.common.bean.IReturnCode;

/**
 * Description: 自定义错误异常信息
 *
 * @Author yuboliang
 * @Date 2019-08-27
 */
public enum ReturnCode implements IReturnCode {
    /*-------------各子系统通用返回码开始------*/
    ERROR_01(-1, "系统繁忙，请稍后再试"),
    BUSINESS_ERROR(-2, "业务异常"),
    EXECUTION_EXCEPTION(-3, "系统执行异常"),
    NULL_ARGUMENT(-4, "参数为空"),
    RETURN_IS_NULL(-5, "返回结果为空"),
    NETWORK_ERROR(-6, "网络异常"),
    SYSTEM_ERROR(-7, "系统错误"),
    ADD_MAX_SIZE(-8, "批量添加数据过大"),
    UPDATE_MAX_SIZE(-9, "批量更新数据过大"),
    MISSING_PARAMETER(-10, "缺少参数异常"),
    QUERY_MAX_SIZE(-11, "批量查询数据过大"),
    DATA_NOT_EXIST(-12, "数据不存在异常"),
    DATA_EXIST(-13, "数据存在异常"),
    SUCCESS(200000, "操作成功"),
    UN_SUCCESS(1, "操作失败"),
    SUCCESS_INSERT(200000, "新增成功"),
    FAIL_INSERT(3, "新增失败"),
    SUCCESS_UPDATE(200000, "修改成功"),
    FAIL_UPDATE(5, "修改失败"),
    SUCCESS_DELETE(200000, "删除成功"),
    FAIL_DELETE(7, "删除失败"),
    ERROR_101(101, "认证失败"),
    ERROR_102(102, "缺少参数或参数不正确"),
    ERROR_103(103, "正在处理中"),
    ERROR_104(104, "请勿重复操作"),
    ERROR_105(105, "JSON格式错误"),
    ERROR_106(106, "时间日期格式不正确"),
    ERROR_107(107, "数据库操作失败"),
    ERROR_108(108, "文件格式不支持"),
    ERROR_109(109, "处理数据条数不能超过1000条"),
    ERROR_111(111, "操作类型不存在或未开通"),
    ERROR_113(113, "数据已存在"),
    ERROR_114(114, "数据不存在"),
    ERROR_115(115, "当前设备时间未校准，校准后才可正常使用"),
    ERROR_116(116, "请选择正确的上级机构"),
    ERROR_117(117, "参数格式错误"),
    ERROR_118(118, "存在不合规数据"),
    ERROR_119(119, "上传文件过大"),
    ERROR_120(120, "上传文件时发生异常"),
    ERROR_121(121, "参数格式不正确"),
    ERROR_122(122, "参数类型未对应"),
    ERROR_123(123, "excel文件校验异常"),
    ERROR_124(124, "用户未绑定分机号"),
    ERROR_125(125, "手机号加密失败"),
    ERROR_126(126, "存在重复绑定的分机号"),
    ERROR_127(127, "保存模板文件失败"),
    ERROR_128(128, "excel单元格格式必须统一为文本格式"),
    ERROR_129(129, "缺少上传的文件或文件是空的"),
    ERROR_130(130, "文件不存在"),
    ERROR_131(131, "该用户已经绑定分机号"),
    ERROR_132(132, "参数已存在"),
    ERROR_133(133, "存在未分配的分机号"),
    ERROR_134(134, "数据库插入失败"),
    ERROR_135(135, "数据库更新失败"),
    ERROR_136(136, "数据库查询异常"),
    ERROR_137(137, " 文件不能为空"),
    ERROR_138(138, "数据库插入主键不存在"),
    ERROR_400(401, "HTTP请求异常"),
    ERROR_405(405, "请求方式不支持"),
    ERROR_500(501, "系统繁忙，请稍后再试"),
    ERROR_4001(4001, "接口调用异常返回"),
    ERROR_4002(4002, "结束时间要大于开始时间"),
    ERROR_4003(4003, "权限验证错误，请确认操作权限"),
    ERROR_4004(4004, "导出失败"),
    ERROR_4005(4005, "类型错误"),
    ERROR_4006(4006, "ID为空"),
    ERROR_4007(4007, "文件读写异常"),
    ERROR_4008(4008, "数据为空"),
    /*-------------各子系统通用返回码结束------*/

    /*-------------- template 服务异常结束 4100X-------------------------*/
    /*-------------- workmanage 服务异常结束 5100X-----------*/
    //-------------- business 服务异常7100x-------------------
    /*-------------- agent 服务异常8000x------- */
    /*-------------- callcenter 服务异常 900XX------- */
    ;

    private int code;
    private String msg;

    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
