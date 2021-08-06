package com.zhilingsd.base.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.zhilingsd.base.common.bean.AppAgentInfo;
import com.zhilingsd.base.common.emuns.ReturnCode;
import com.zhilingsd.base.common.exception.ServiceException;
import com.zhilingsd.base.common.result.SingleResult;
import com.zhilingsd.base.common.utils.AppUtil;
import com.zhilingsd.base.common.utils.IPUtils;
import com.zhilingsd.base.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * 接口公共服务处理
 *
 * @author linmenghuai
 * @date 2019-4-30 16:23:32
 */
@Order(1)
@Aspect
@Component
@Slf4j
public class CommonFacadeAspect {

    //    @Autowired
//    protected Validator validator;
    private static final String PRE_TAG = "\n" + "*** ";

    private static final String UNPRINT_CLASS = "com.zhilingsd.blackhole.dto.request.UploadWithBase64Request";
    private static final String UNPRINT_RESP_CLASS = "com.zhilingsd.blackhole.dto.response.DownloadWithBase64Resp";

    @Around(value = "@annotation(com.zhilingsd.base.common.annotation.CommonFacade)")
    public Object before(ProceedingJoinPoint jp) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append(PRE_TAG + "URL: ").append(request.getRequestURL().toString());
        sb.append(PRE_TAG + "HTTP_METHOD: ").append(request.getMethod());
        sb.append(PRE_TAG + "IP: ").append(IPUtils.getRemortIP(request));
        sb.append(PRE_TAG).append(jp.getSignature().toShortString());

        if (isPrintArgs(jp.getArgs())) {
            String jsonString = JsonUtils.toJsonString(jp.getArgs());
            if (jsonString.length() < 1000) {
                sb.append(PRE_TAG + "in: ").append(jsonString);
            }
        } else {
            sb.append(PRE_TAG + "in: do not print");
        }
        log.info(sb.toString());
        // 创建AppAgentInfo对象，如果有字段为空则抛出异常
        String session = Optional.ofNullable(request.getHeader("session")).orElseThrow(() -> new ServiceException(ReturnCode.BUSINESS_ERROR.getCode(), "请求头session不能为空"));
        String operatorId = Optional.ofNullable(request.getHeader("operatorId")).orElseThrow(() -> new ServiceException(ReturnCode.SYSTEM_ERROR.getCode(), "请求头operatorId不能为空"));
        AppAgentInfo agentInfo = new AppAgentInfo(Long.parseLong(operatorId), session);
        AppUtil.setAppAgentInfo(agentInfo);
        log.info(PRE_TAG + "operatorInfo：" + JSONObject.toJSONString(agentInfo));

        Object obj;
        try {
            obj = jp.proceed();
        } finally {
            AppUtil.clean();
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(PRE_TAG).append(jp.getSignature().toShortString());

        if (isPrintResp(obj)) {
            String toJsonString = JsonUtils.toJsonString(obj);
            if (toJsonString.length() < 1000) {
                stringBuilder.append(PRE_TAG + "out: ").append(toJsonString);
            }
        } else {
            stringBuilder.append(PRE_TAG + "out: do not print");
        }
        stringBuilder.append(PRE_TAG + "usedTime: ").append(System.currentTimeMillis() - startTime).append("ms");
        log.info(stringBuilder.toString());
        return obj;
    }

    private Boolean isPrintArgs(Object[] objects) {
        try {
            return Arrays.stream(objects).noneMatch(object -> StringUtils.equals(object.getClass().getName(), UNPRINT_CLASS));
        } catch (Exception ex) {
            log.warn("判断是否输出接口入参出错，ex:{}", ex.toString());
            return true;
        }
    }

    private Boolean isPrintResp(Object object) {
        try {
            if (!(object instanceof ResponseEntity)) {
                return true;
            }

            ResponseEntity responseEntity = (ResponseEntity) object;

            if (!(responseEntity.getBody() instanceof SingleResult)) {
                return true;
            }

            SingleResult singleResult = (SingleResult) responseEntity.getBody();

            return !StringUtils.equals(singleResult.getData().getClass().getName(), UNPRINT_RESP_CLASS);
        } catch (Exception ex) {
            log.warn("判断是否输出接口出参错误，ex:{}", ex.toString());
            return true;
        }
    }
}
