package com.zhilingsd.base.common.emuns;

/**
 * Description: 自定义错误异常信息
 *
 * @Author zengkai
 * @Date 2019/4/10 12:06
 */
public enum ReturnCode {
    /**
     * 系统繁忙，请稍后再试
     */
    ERROR_01(-1, "系统繁忙，请稍后再试"),
    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),
    /**
     * 操作失败
     */
    UN_SUCCESS(1, "操作失败"),

    /**
     * 新增成功
     */
    SUCCESS_INSERT(0, "新增成功"),

    /**
     * 新增失败
     */
    FAIL_INSERT(3, "新增失败"),

    /**
     * 修改成功
     */
    SUCCESS_UPDATE(0, "修改成功"),

    /**
     * 修改失败
     */
    FAIL_UPDATE(5, "修改失败"),

    /**
     * 删除成功
     */
    SUCCESS_DELETE(0, "删除成功"),

    /**
     * 删除失败
     */
    FAIL_DELETE(7, "删除失败"),
    /**
     * 认证失败
     */
    ERROR_101(101, "认证失败"),

    /**
     * 缺少参数或参数不正确
     */
    ERROR_102(102, "缺少参数或参数不正确"),

    /**
     * 正在处理中
     */
    ERROR_103(103, "正在处理中"),
    /**
     * 请勿重复操作
     */
    ERROR_104(104, "请勿重复操作"),
    /**
     * JSON格式错误
     */
    ERROR_105(105, "JSON格式错误"),
    /**
     * 时间日期格式不正确
     */
    ERROR_106(106, "时间日期格式不正确"),
    /**
     * 数据库操作失败
     */
    ERROR_107(107, "数据库操作失败"),
    /**
     * 文件格式不支持
     */
    ERROR_108(108, "文件格式不支持"),
    /**
     * 处理数据条数不能超过1000条
     */
    ERROR_109(109, "处理数据条数不能超过1000条"),
    /**
     * 操作类型不存在或未开通
     */
    ERROR_111(111, "操作类型不存在或未开通"),
    /**
     * 数据已存在
     */
    ERROR_113(113, "数据已存在"),
    /**
     * 数据不存在
     */
    ERROR_114(114, "数据不存在"),
    /**
     * 当前设备时间未校准，校准后才可正常使用
     */
    ERROR_115(115, "当前设备时间未校准，校准后才可正常使用"),
    /**
     * 请选择正确的上级机构
     */
    ERROR_116(116, "请选择正确的上级机构"),
    /**
     * 参数格式错误
     */
    ERROR_117(117, "参数格式错误"),
    /**
     * 存在不合规数据
     */
    ERROR_118(118, "存在不合规数据"),
    /**
     * 上传文件过大
     */
    ERROR_119(119, "上传文件过大"),
    /**
     * 上传文件时发生异常
     */
    ERROR_120(120, "上传文件时发生异常"),
    /**
     * 参数格式不正确
     */
    ERROR_121(121, "参数格式不正确"),
    /**
     * 参数类型未对应
     */
    ERROR_122(122, "参数类型未对应"),
    /**
     * excel文件校验异常
     */
    ERROR_123(123, "excel文件校验异常"),
    /**
     * 用户未绑定分机号
     */
    ERROR_124(124, "用户未绑定分机号"),
    /**
     * 手机号加密失败
     */
    ERROR_125(125, "手机号加密失败"),
    /**
     * 存在重复绑定的分机号
     */
    ERROR_126(126, "存在重复绑定的分机号"),
    /**
     * 保存模板文件失败
     */
    ERROR_127(127, "保存模板文件失败"),
    /**
     * excel单元格格式必须统一为文本格式
     */
    ERROR_128(128, "excel单元格格式必须统一为文本格式"),
    /**
     * 缺少上传的文件或文件是空的
     */
    ERROR_129(129, "缺少上传的文件或文件是空的"),
    /**
     * 文件不存在
     */
    ERROR_130(130, "文件不存在"),
    /**
     * 该用户已经绑定分机号
     */
    ERROR_131(131, "该用户已经绑定分机号"),
    /**
     * 参数已存在
     */
    ERROR_132(132, "参数已存在"),
    /**
     * 存在未分配的分机号
     */
    ERROR_133(133, "存在未分配的分机号"),

    /**
     * HTTP请求异常
     */
    ERROR_400(400, "HTTP请求异常"),
    /**
     * 系统繁忙，请稍后再试
     */
    ERROR_500(500, "系统繁忙，请稍后再试"),

    /**
     * 接口调用异常返回
     */
    ERROR_4001(4001, "接口调用异常返回"),

    /**
     * 结束时间要大于开始时间
     */
    ERROR_4002(4002, "结束时间要大于开始时间"),

    /**
     * 权限验证错误，请确认操作权限
     */
    ERROR_4003(4003, "权限验证错误，请确认操作权限"),

    /**
     * 导出失败
     */
    ERROR_4004(4004, "导出失败"),

    /**
     * 类型错误
     */
    ERROR_4005(4005, "类型错误"),

    /**
     * ID为空
     */
    ERROR_4006(4006, "ID为空"),

    /**
     * 文件读写异常
     */
    ERROR_4007(4007, "文件读写异常"),

    //-------------------案件服务业务异常 4000X-------------------
    /**
     * 批次无法删除，已分案
     */
    ERROR_40001(40001, "批次无法删除，已分案"),
//    案件中心相关处理
    ERROR_40009(40009, "缺少主键信息"),
    ERROR_40010(40010,"案件ID不能为空"),
    ERROR_40011(40011,"案件编号不能为空"),
    ERROR_40012(40012,"批次号不能为空"),
    ERROR_40013(40013,"专案号不能为空"),
    ERROR_40014(40014,"删除案件删除方式不正确"),
    ERROR_40015(40015,"查询案件金额查询方式不正确"),
    ERROR_40016(40016,"案件分组信息不能为空"),
    ERROR_40017(40017,"案件催收记录信息不能为空"),
    ERROR_40018(40018,"案件所传催收员或案件信息为空"),
    ERROR_40019(40019,"导入案件,缺少案件信息"),
    ERROR_40020(40020,"案件状态修改,传参不能为空"),
    ERROR_40021(40021,"获取案件分案表信息,传参为空"),
    ERROR_40022(40022,"该机构下没有未查询到专案"),


    //------------------- 模板服务异常 4100X-------------------
    ERROR_41001(41001, "没有找到该模板类型，请重新选择"),
    ERROR_41002(41002, "模板不存在内容，请重新导入模板"),
    ERROR_41003(41003, "模板字段不能重复，请修改模板字段"),
    ERROR_41004(41004, "模板文件格式不正确，请重新选择"),
    ERROR_41005(41005, "模板不存在"),

    ERROR_41101(41101, "常量信息ID不能为空"),
    ERROR_41102(41102, "常量信息类型或值不能为空"),
    ERROR_41103(41103, "常量信息类型不能为空"),
    ERROR_41104(41104, "字典信息类型不能为空"),
    ERROR_41032(41032, "请上传word文件"),
    //------------------- 作业管理服务异常 5100X-------------------
    ERROR_51001(51001, "导出外访报告导出类型或ID组或模板为空"),
    ERROR_51002(51002, "导出外访附件导出类型或ID组为空"),
    ERROR_51003(51003, "打印函件导出类型或ID组为空"),
    ERROR_51004(51004, "导出类型选择错误"),
    //------------------- 登录服务异常 6100X-------------------
    ERROR_61028(61028, "密码不能为空"),
    ;

    private int code;
    private String msg;

    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static  String getValueByCode(int code){
        for (ReturnCode osEnum: ReturnCode.values()) {
            if(osEnum.getCode()==code){
                return  osEnum.getMsg();
            }
        }
        return  "";
    }
}
