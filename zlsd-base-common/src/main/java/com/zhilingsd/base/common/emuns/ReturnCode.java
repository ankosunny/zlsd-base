package com.zhilingsd.base.common.emuns;

/**
 * Description: 自定义错误异常信息
 *
 * @Author zengkai
 * @Date 2019/4/10 12:06
 */
public enum ReturnCode {
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

    /*-----------案件中台服务业务异常开始 4000X---*/
    ERROR_40001(40001, "批次无法删除，已分案"),
    ERROR_40009(40009, "缺少主键信息"),
    ERROR_40010(40010, "案件ID不能为空"),
    ERROR_40011(40011, "案件编号不能为空"),
    ERROR_40012(40012, "批次号不能为空"),
    ERROR_40013(40013, "专案号不能为空"),
    ERROR_40014(40014, "删除案件删除方式不正确"),
    ERROR_40015(40015, "查询案件金额查询方式不正确"),
    ERROR_40016(40016, "案件分组信息不能为空"),
    ERROR_40017(40017, "案件催收记录信息不能为空"),
    ERROR_40018(40018, "案件所传催收员或案件信息为空"),
    ERROR_40019(40019, "导入案件,缺少案件信息"),
    ERROR_40020(40020, "案件状态修改,传参不能为空"),
    ERROR_40021(40021, "获取案件分案表信息,传参为空"),
    ERROR_40022(40022, "该机构下没有未查询到专案"),
    ERROR_40023(40023, "修改催收流程操作类型不存在"),
    ERROR_40024(40024, "修改催收流程案件编号不存在"),
    ERROR_40025(40025, "自定义修改催收流程集合信息不存在"),
    ERROR_40026(40026, "记催记修改承诺还款信息失败"),
    ERROR_40027(40027, "记催记修改案件归类状态信息失败"),
    ERROR_40028(40028, "记催记案件归类状态信息不存在"),
    ERROR_40029(40029, "记催记案件编号或主要信息不存在"),
    ERROR_40030(40030, "导入案件,缺少案件编号信息"),
    ERROR_40031(40031, "删除案件中对应除案件联系人ID失败"),
    ERROR_40032(40032, "案件导入或更新，未传入操作类型"),
    ERROR_40033(40033, "案件不为未分配新案件，无法进行物理删除操作"),
    ERROR_40034(40034, "所选择的案件包含非停催状态，无法取消停催操作"),
    ERROR_40035(40035, "组合字段查询案件信息为空"),
    ERROR_40036(40036,"导入工单中，投诉工单存在投诉人信息未填写"),
    ERROR_40037(40037,"组合查询条件不存在"),
    ERROR_40038(40038,"导入工单中，工单类型不存在"),
    ERROR_40039(40039,"导入工单中，工单类型填写有误"),
    ERROR_40040(40040,"该贷款机构无催收方式，请设置催收方式"),
    ERROR_40041(40041,"该贷款机构无催收结果，请设置催收结果"),
    /*-----------案件中台服务业务异常结束 4000X---*/

    /*-------模板服务异常开始 4100X-------------------------*/
    ERROR_41001(41001, "没有找到该模板类型，请重新选择"),
    ERROR_41002(41002, "模板不存在内容，请重新导入模板"),
    ERROR_41003(41003, "模板字段不能重复，请修改模板字段"),
    ERROR_41004(41004, "模板文件格式不正确，请重新选择"),
    ERROR_41005(41005, "模板不存在"),
    ERROR_41006(41006, "模板上传至黑洞失败"),
    ERROR_41007(41007, "数据为空"),
    ERROR_41008(41008, "配置模板映射关系中标准字段不能为空"),
    // ERROR_41009(41009, "未配置案件的关联字段查询，匹配不上案件信息，请重新配置映射关系"),
    ERROR_41101(41101, "常量信息ID不能为空"),
    ERROR_41102(41102, "常量信息类型或值不能为空"),
    ERROR_41103(41103, "常量信息类型不能为空"),
    ERROR_41104(41104, "字典信息类型不能为空"),
    ERROR_41032(41032, "请上传word文件"),
    ERROR_41033(41033, "模板名称不能相同"),
    ERROR_41034(41034, "该贷款机构没有此模板，请新建"),
    ERROR_41035(41035, "暂无信封模板，请内置信封模板"),
    ERROR_41036(41036, "模板id不能为空"),
    ERROR_41037(41037, "映射人关系映射数据不能空"),
    /*-------模板服务异常结束 4100X-------------------------*/

    /*-------------- 作业管理服务异常开始 5100X-----------*/
    ERROR_51001(51001, "导出外访报告导出类型或ID组或模板为空"),
    ERROR_51002(51002, "导出外访附件导出类型或ID组为空"),
    ERROR_51003(51003, "打印函件导出类型或ID组为空"),
    ERROR_51004(51004, "导出类型选择错误"),
    ERROR_51005(51005, "通过申请人ID获取小组ID异常，无数据"),
    ERROR_51006(51006, "两个外访人员不能为空"),
    ERROR_51007(51007, "获取用户信息集合失败，无用户数据"),
    ERROR_51008(51008, "获取贷款机构信息失败"),
    ERROR_51009(51009, "获取用户信息无数据"),
    ERROR_51010(51010, "获取催收记录无数据或异常"),
    ERROR_51011(51011, "获取联系人信息失败"),
    ERROR_51012(51012, "没有附件可提供下载"),
    ERROR_51013(51013, "文件压缩处理失败"),
    ERROR_51014(51014, "导出文件处理失败"),
    ERROR_51015(51015, "保存催记信息失败"),
    ERROR_51016(51016, "获取信封信息失败"),
    ERROR_51017(51017, "请填入催记内容/注记内容"),
    ERROR_51018(51018, "函件已打印才能标记完成"),
    ERROR_51019(51019, "取消申请入参不完整"),
    ERROR_51020(51020, "请选择同一分公司的案件进行分配"),
    ERROR_51021(51021, "催记内容包含敏感词"),
    ERROR_51022(51022, "只能编辑当月的目标值"),
    ERROR_51023(51023, "案件列表查询无返回数据"),
    ERROR_51024(51024, "案件ID不能为空"),
    ERROR_51025(51025, "函件数据缺少贷款机构不能为空"),
    ERROR_51026(51026, "没有数据"),
    ERROR_51027(51027, "只能设置当月及以后月份的回款目标值"),
    ERROR_51028(51028, "导出函件模板为空，请上传函件模板"),
    ERROR_51029(51029, "该用户没有审批权限"),
    ERROR_51030(51030, "该分公司下还没有外访员"),
    ERROR_51031(51031, "没有函件需要标记完成"),
    ERROR_51032(51032, "两个外访人员不能相同"),
    ERROR_51033(51033, "案件没有分配催收员，或获取催收员失败"),
    ERROR_51034(51034, "该案件没有分配催收员，请先分配催收员"),
    /*-------------- 作业管理服务异常结束 5100X-----------*/

    //------------------- 登录服务异常 6100X-------------------
    ERROR_61028(61028, "密码不能为空"),

    //--------------------业务服务异常 7100x-------------------
    ERROR_71001(71001, "该案件编号不存在"),
    ERROR_71002(71002, "文件有必填字段列未添加"),
    ERROR_71003(71003, "无匹配案件信息"),
    ERROR_71004(71004, "文件数不正确"),
    ERROR_71005(71005, "存在导入的还款币种不能和汇率设置的币种完全匹配"),
    ERROR_71006(71006, "已存在对应的汇总账单"),
    ERROR_71007(71007, "添加失败,数据不一致"),
    ERROR_71008(71008, "主键ID不存在"),
    ERROR_71009(71009, "存在相同委案机构人员操作还款耗时任务，为保证数据一致，请稍后再试"),
    ERROR_71010(71010, "存在还款操作没有处理完成，为保证数据一致，请稍后再试"),
    ERROR_71011(71011, "存在必填字段未匹配到模板字段"),
    ERROR_71012(71012, "存在必填字段没有值"),

    /*---------- agent 服务异常开始 8000x------- */
    ERROR_80001(80001, "角色已绑定用户，不能删除"),
    ERROR_80002(80002, "删除角色异常"),
    ERROR_80003(80003, "查询角色记录数异常"),
    ERROR_80004(80004, "查询角色列表异常"),
    ERROR_80005(80005, "插入角色权限关联表失败"),
    ERROR_80006(80006, "根据权限Id查询权限异常"),
    ERROR_80007(80007, "插入角色异常"),
    ERROR_80008(80008, "修改角色异常"),
    ERROR_80009(80009, "催收员不存在"),
    ERROR_80010(80010, "服务机构不允许为空"),
    /*---------- agent 服务异常结束 8000x------- */


    /*---------- callcenter 服务异常结束 900XX------- */
    ERROR_90001(90001, "excel校验异常"),
    ERROR_90002(90002, "号码识别异常，请检查号码是否有误"),
    ERROR_90003(90003, "获取不到该省会城市，请联系管理员修复基础数据"),
    ERROR_90004(90004, "当前无符合要求的匹配号码，请稍后再试"),
    ERROR_90005(90005, "存在接入号有坐席使用中，不可删除，可回收坐席后再删除"),
    ;

    private int code;
    private String msg;

    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getValueByCode(int code) {
        for (ReturnCode osEnum : ReturnCode.values()) {
            if (osEnum.getCode() == code) {
                return osEnum.getMsg();
            }
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
